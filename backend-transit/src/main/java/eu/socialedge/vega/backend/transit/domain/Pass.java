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
package eu.socialedge.vega.backend.transit.domain;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.ddd.ValueObject;
import eu.socialedge.vega.backend.transit.domain.location.ZoneId;

import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity // Treat it as an Entity from the persistence perspective to avoid JSR338 Chp 2.6 constraint
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Pass extends ValueObject {

    @Getter
    @Id @Embedded
    @Column(nullable = false)
    private final PassengerId passengerId;

    @Getter
    @Id @Embedded
    @Column(nullable = false)
    private final FareId fareId;

    @Getter
    @Id
    @Column(nullable = false)
    private final LocalDateTime activation;

    @Getter
    @Id
    @Column(nullable = false)
    private final LocalDateTime expiration;

    @ElementCollection
    @CollectionTable(name = "pass_vehicle_type", joinColumns = @JoinColumn(name = "pass_id"))
    private final Set<VehicleType> vehicleTypes;

    @ElementCollection
    @CollectionTable(name = "pass_zone", joinColumns = @JoinColumn(name = "pass_id"))
    private final Set<ZoneId> zoneIds;

    @ElementCollection
    @CollectionTable(name = "pass_operator", joinColumns = @JoinColumn(name = "pass_id"))
    private final Set<OperatorId> operatorIds;

    public Pass(PassengerId passengerId, FareId fareId,
                LocalDateTime activation, LocalDateTime expiration,
                Set<VehicleType> vehicleTypes, Set<ZoneId> zoneIds,
                Set<OperatorId> operatorIds) {
        this.passengerId = notNull(passengerId);
        this.fareId = notNull(fareId);
        this.vehicleTypes = notEmpty(vehicleTypes);
        this.zoneIds = notEmpty(zoneIds);
        this.operatorIds = notEmpty(operatorIds);

        Validate.isTrue(notNull(expiration).isBefore(notNull(activation)),
                "Expiration must be before activation date");
        this.activation = activation;
        this.expiration = expiration;
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiration);
    }

    public boolean complies(VehicleType vehicleType) {
        return vehicleTypes.contains(vehicleType);
    }

    public boolean complies(ZoneId zoneId) {
        return zoneIds.contains(zoneId);
    }

    public boolean complies(OperatorId operatorId) {
        return operatorIds.contains(operatorId);
    }
}
