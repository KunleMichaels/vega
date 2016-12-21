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
import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.DeductionSerializer;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.MonetaryAmountSerializer;
import eu.socialedge.vega.backend.transit.domain.location.ZoneId;

import org.apache.commons.lang3.Validate;

import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Fare extends AggregateRoot<FareId> {

    @Column(nullable = false)
    @Convert(converter = MonetaryAmountSerializer.class)
    private @NonNull MonetaryAmount price;

    @Column(nullable = false)
    private Period validity;

    @ElementCollection
    @CollectionTable(name = "fare_deduction", joinColumns = @JoinColumn(name = "fare_id"))
    @Convert(converter = DeductionSerializer.class)
    private final Set<Deduction> deductions;

    @ElementCollection
    @CollectionTable(name = "fare_vehicle_type", joinColumns = @JoinColumn(name = "fare_id"))
    private final Set<VehicleType> vehicleTypes;

    @ElementCollection
    @CollectionTable(name = "fare_zone", joinColumns = @JoinColumn(name = "fare_id"))
    private final Set<ZoneId> zoneIds;

    @ElementCollection
    @CollectionTable(name = "fare_operator", joinColumns = @JoinColumn(name = "fare_id"))
    private final Set<OperatorId> operatorIds;

    public Fare(FareId fareId, MonetaryAmount price, Set<Deduction> deductions,
                Period validity, Set<VehicleType> vehicleTypes, Set<ZoneId> zoneIds,
                Set<OperatorId> operatorIds) {
        super(fareId);

        Validate.isTrue(!validity.isNegative() && !validity.isZero(),
                "Validity period must be longer than 0");

        this.price = notNull(price);
        this.deductions = notNull(deductions);
        this.validity = notNull(validity);
        this.vehicleTypes = notEmpty(vehicleTypes);
        this.zoneIds = notEmpty(zoneIds);
        this.operatorIds = notEmpty(operatorIds);
    }

    public Fare(FareId fareId, MonetaryAmount price, Period validity,
                Set<VehicleType> vehicleTypes, Set<ZoneId> zoneIds, Set<OperatorId> operatorIds) {
        this(fareId, price, new HashSet<>(), validity, vehicleTypes,
                zoneIds, operatorIds);
    }
}
