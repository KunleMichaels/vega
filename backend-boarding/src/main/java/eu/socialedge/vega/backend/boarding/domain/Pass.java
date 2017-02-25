/**
 * SocialEdge Vega - An Electronic Transit Fare Payment System
 * Copyright (c) 2016 SocialEdge
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package eu.socialedge.vega.backend.boarding.domain;

import eu.socialedge.ddd.domain.ValueObject;
import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.geo.domain.Zone;
import eu.socialedge.vega.backend.payment.domain.ExpirationDate;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity // Treat it as an Entity from the persistence perspective to avoid JSR338 Chp 2.6 constraint
@Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Pass extends ValueObject {

    @Id @Embedded
    @Column(nullable = false)
    private final FareId fareId;

    @Id
    @Column(nullable = false)
    private final Instant activation;

    @Id
    @Column(nullable = false)
    private final ExpirationDate expirationDate;

    @ElementCollection
    @Column(name = "vehicle_types")
    @CollectionTable(name = "pass_vehicle_type", joinColumns = {
            @JoinColumn(name = "fare_id"),
            @JoinColumn(name = "activation"),
            @JoinColumn(name = "expirationDate")})
    @Enumerated(EnumType.STRING)
    private final Set<VehicleType> vehicleTypes;

    @Embedded
    private final Zone zone;

    @ElementCollection
    @CollectionTable(name = "pass_operator", joinColumns = {
            @JoinColumn(name = "fare_id"),
            @JoinColumn(name = "activation"),
            @JoinColumn(name = "expirationDate")})
    private final Set<OperatorId> operatorIds;

    public Pass(FareId fareId,
                Instant activation, ExpirationDate expirationDate,
                Set<VehicleType> vehicleTypes, Zone zone,
                Set<OperatorId> operatorIds) {
        this.fareId = notNull(fareId);
        this.vehicleTypes = new HashSet<>(notEmpty(vehicleTypes));
        this.zone = notNull(zone);
        this.operatorIds = new HashSet<>(notEmpty(operatorIds));
        this.activation = notNull(activation);

        Validate.isTrue(expirationDate.toInstant().isBefore(activation),
                "expirationDate must be before activation date");
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.occurred();
    }

    public boolean complies(VehicleType vehicleType) {
        return vehicleTypes.contains(vehicleType);
    }

    public boolean complies(Location location) {
        return zone.contains(location);
    }

    public boolean complies(OperatorId operatorId) {
        return operatorIds.contains(operatorId);
    }

    public Set<VehicleType> vehicleTypes() {
        return Collections.unmodifiableSet(vehicleTypes);
    }

    public Set<OperatorId> operatorIds() {
        return Collections.unmodifiableSet(operatorIds);
    }
}
