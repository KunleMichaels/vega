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

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.shared.transit.FareId;
import eu.socialedge.vega.backend.shared.account.OperatorId;
import eu.socialedge.vega.backend.shared.transit.VehicleType;
import eu.socialedge.vega.backend.shared.transit.location.Zone;

import org.apache.commons.lang3.Validate;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@ToString
@Getter @Setter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Fare extends AggregateRoot<FareId> {

    private @NonNull MonetaryAmount price;

    private @NonNull Period validity;

    private final Set<Deduction> deductions;

    private final Set<VehicleType> vehicleTypes;

    private final Set<Zone> zones;

    private final Set<OperatorId> operatorIds;

    public Fare(FareId fareId, MonetaryAmount price, Set<Deduction> deductions,
                Period validity, Set<VehicleType> vehicleTypes, Set<Zone> zones,
                Set<OperatorId> operatorIds) {
        super(fareId);

        Validate.isTrue(!validity.isNegative() && !validity.isZero(),
                "Validity period must be longer than 0");

        this.price = notNull(price);
        this.deductions = notNull(deductions);
        this.validity = notNull(validity);
        this.vehicleTypes = notEmpty(vehicleTypes);
        this.zones = notEmpty(zones);
        this.operatorIds = notEmpty(operatorIds);
    }

    public Fare(FareId fareId, MonetaryAmount price, Period validity,
                Set<VehicleType> vehicleTypes, Set<Zone> zones, Set<OperatorId> operatorIds) {
        this(fareId, price, new HashSet<>(), validity, vehicleTypes,
                zones, operatorIds);
    }
}
