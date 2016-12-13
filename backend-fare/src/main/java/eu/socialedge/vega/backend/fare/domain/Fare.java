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
package eu.socialedge.vega.backend.fare.domain;

import eu.socialedge.vega.backend.fare.domain.location.Location;
import eu.socialedge.vega.backend.fare.domain.location.Zone;

import org.apache.commons.lang3.Validate;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
public class Fare {

    private final FareId fareId;

    private final MonetaryAmount price;
    private final Set<Deduction> deductions;

    private final Period validity;

    private final Set<VehicleType> vehicleTypes;

    private final Set<Zone> zones;

    private final Set<OperatorId> operatorIds;

    public Fare(FareId fareId, MonetaryAmount price, Set<Deduction> deductions, Period validity,
                Set<VehicleType> vehicleTypes, Set<Zone> zones, Set<OperatorId> operatorIds) {
        Validate.notNull(fareId);
        Validate.notNull(price);
        Validate.notNull(deductions);
        Validate.notNull(validity);
        Validate.isTrue(!validity.isNegative() && !validity.isZero(),
                "Validity period must be longer than 0");
        Validate.notNull(vehicleTypes);
        Validate.notNull(operatorIds);

        this.fareId = fareId;
        this.price = price;
        this.deductions = deductions;
        this.validity = validity;
        this.vehicleTypes = vehicleTypes;
        this.zones = zones;
        this.operatorIds = operatorIds;
    }

    public Fare(FareId fareId, MonetaryAmount price, Period validity, Set<OperatorId> operatorIds) {
        this(fareId, price, new HashSet<>(), validity,
                new HashSet<>(), new HashSet<>(), operatorIds);
    }

    public boolean handles(VehicleType vehicleType) {
        return vehicleTypes.contains(vehicleType);
    }

    public boolean handles(Zone zone) {
        return zones.contains(zone);
    }

    public boolean handles(Location location) {
        return zones.stream().anyMatch(zone -> zone.contains(location));
    }

    public boolean handles(OperatorId operatorId) {
        return operatorIds.contains(operatorId);
    }

    public boolean handles(Operator operator) {
        return handles(operator.id());
    }
}
