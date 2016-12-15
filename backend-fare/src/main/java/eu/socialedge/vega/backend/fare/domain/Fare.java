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

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.fare.domain.location.Location;
import eu.socialedge.vega.backend.fare.domain.location.Zone;
import eu.socialedge.vega.backend.shared.FareId;
import eu.socialedge.vega.backend.shared.OperatorId;
import eu.socialedge.vega.backend.shared.VehicleType;

import org.apache.commons.lang3.Validate;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Fare extends AggregateRoot<FareId> {

    private final MonetaryAmount price;
    private final Set<Deduction> deductions;

    private final Period validity;

    private final Set<VehicleType> vehicleTypes;

    private final Set<Zone> zones;

    private final Set<OperatorId> operatorIds;

    private final Set<Pass> passes;

    public Fare(FareId fareId, MonetaryAmount price, Set<Deduction> deductions,
                Period validity, Set<VehicleType> vehicleTypes, Set<Zone> zones,
                Set<OperatorId> operatorIds, Set<Pass> passes) {
        super(fareId);

        Validate.isTrue(!validity.isNegative() && !validity.isZero(),
                "Validity period must be longer than 0");

        this.price = notNull(price);
        this.deductions = notNull(deductions);
        this.validity = notNull(validity);
        this.vehicleTypes = notEmpty(vehicleTypes);
        this.zones = notEmpty(zones);
        this.operatorIds = notEmpty(operatorIds);
        this.passes = notNull(passes);
    }

    public Fare(FareId fareId, MonetaryAmount price, Period validity,
                Set<VehicleType> vehicleTypes, Set<Zone> zones, Set<OperatorId> operatorIds) {
        this(fareId, price, new HashSet<>(), validity, vehicleTypes,
                zones, operatorIds, new HashSet<>());
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
