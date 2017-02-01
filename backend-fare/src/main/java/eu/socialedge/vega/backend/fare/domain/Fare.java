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

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.geo.domain.Zone;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.DeductionAttributeConverter;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.MonetaryAmountAttributeConverter;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.PeriodAttributeConverter;

import org.apache.commons.lang3.Validate;

import java.time.Period;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Fare extends AggregateRoot<FareId> {

    @Setter
    @Column(nullable = false)
    @Convert(converter = MonetaryAmountAttributeConverter.class)
    private @NonNull MonetaryAmount price;

    @Setter
    @Column(nullable = false)
    @Convert(converter = PeriodAttributeConverter.class)
    private Period validity;

    @ElementCollection
    @Column(name = "deduction")
    @CollectionTable(name = "fare_deduction", joinColumns = @JoinColumn(name = "fare_id"))
    @Convert(converter = DeductionAttributeConverter.class)
    private final Set<Deduction> deductions;

    @ElementCollection
    @Column(name = "vehicle_types")
    @CollectionTable(name = "fare_vehicle_type", joinColumns = @JoinColumn(name = "fare_id"))
    @Enumerated(EnumType.STRING)
    private final Set<VehicleType> vehicleTypes;

    @Setter
    @Embedded
    private Zone zone;

    @ElementCollection
    @CollectionTable(name = "fare_operator", joinColumns = @JoinColumn(name = "fare_id"))
    private final Set<OperatorId> operatorIds;

    public Fare(FareId fareId, MonetaryAmount price, Set<Deduction> deductions,
                Period validity, Set<VehicleType> vehicleTypes, Zone zone,
                Set<OperatorId> operatorIds) {
        super(fareId);

        Validate.isTrue(!validity.isNegative() && !validity.isZero(),
                "Validity period must be longer than 0");

        this.price = notNull(price);
        this.deductions = new HashSet<>(notNull(deductions));
        this.validity = notNull(validity);
        this.vehicleTypes = new HashSet<>(notEmpty(vehicleTypes));
        this.zone = notNull(zone);
        this.operatorIds = new HashSet<>(notEmpty(operatorIds));
    }

    public Fare(FareId fareId, MonetaryAmount price, Period validity,
                Set<VehicleType> vehicleTypes, Zone zone, Set<OperatorId> operatorIds) {
        this(fareId, price, new HashSet<>(), validity, vehicleTypes,
                zone, operatorIds);
    }

    public boolean addVehicleType(VehicleType vehicleType) {
        return vehicleTypes.add(notNull(vehicleType));
    }

    public boolean removeVehicleType(VehicleType vehicleType) {
        return vehicleTypes.remove(vehicleType);
    }

    public Set<VehicleType> vehicleTypes() {
        return Collections.unmodifiableSet(vehicleTypes);
    }

    public boolean addOperatorId(OperatorId operatorId) {
        return operatorIds.add(notNull(operatorId));
    }

    public boolean removeOperatorId(OperatorId operatorId) {
        return operatorIds.remove(operatorId);
    }

    public Set<OperatorId> operatorIds() {
        return Collections.unmodifiableSet(operatorIds);
    }
}
