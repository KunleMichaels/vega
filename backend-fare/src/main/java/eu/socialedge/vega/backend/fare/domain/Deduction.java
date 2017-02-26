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

import eu.socialedge.ddd.domain.ValueObject;
import lombok.*;
import lombok.experimental.Accessors;

import javax.money.MonetaryAmount;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

import static org.apache.commons.lang3.Validate.notBlank;

@ToString
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Embeddable @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Deduction extends ValueObject {

    private final String description;

    private final BigDecimal multiplier;

    public Deduction(String description, BigDecimal multiplier) {
        if (multiplier.signum() < 0)
            throw new IllegalArgumentException("Multiplier can't be negative");

        this.description = notBlank(description);
        this.multiplier = multiplier;
    }

    public Deduction(String description, double multiplier) {
        this(description, new BigDecimal(multiplier));
    }

    public MonetaryAmount apply(MonetaryAmount basePrice) {
        return basePrice.multiply(multiplier);
    }
}
