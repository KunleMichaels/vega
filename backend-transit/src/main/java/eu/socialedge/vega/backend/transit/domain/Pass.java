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

import eu.socialedge.vega.backend.ddd.ValueObject;
import eu.socialedge.vega.backend.transit.domain.location.Location;
import eu.socialedge.vega.backend.transit.domain.location.Zone;
import eu.socialedge.vega.backend.shared.FareId;
import eu.socialedge.vega.backend.shared.OperatorId;
import eu.socialedge.vega.backend.shared.PassengerId;
import eu.socialedge.vega.backend.shared.VehicleType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Pass extends ValueObject {

    @Getter
    private final PassengerId passengerId;

    @Getter
    private final FareId fareId;

    @Getter
    private final LocalDateTime activation;

    @Getter
    private final LocalDateTime expiration;

    private final Set<VehicleType> vehicleTypes;

    private final Set<Zone> zones;

    private final Set<OperatorId> operatorIds;

    public Pass(PassengerId passengerId, FareId fareId,
                LocalDateTime activation, LocalDateTime expiration,
                Set<VehicleType> vehicleTypes, Set<Zone> zones,
                Set<OperatorId> operatorIds) {
        this.passengerId = notNull(passengerId);
        this.fareId = notNull(fareId);
        this.vehicleTypes = notEmpty(vehicleTypes);
        this.zones = notEmpty(zones);
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

    public boolean complies(Zone zone) {
        return zones.contains(zone);
    }

    public boolean complies(Location location) {
        return zones.stream().anyMatch(zone -> zone.contains(location));
    }

    public boolean complies(OperatorId operatorId) {
        return operatorIds.contains(operatorId);
    }
}
