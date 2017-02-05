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
package eu.socialedge.vega.backend.infrastructure.persistence.convert;

import eu.socialedge.vega.backend.fare.domain.Deduction;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static org.junit.Assert.assertEquals;

public class DeductionSerializerTest {

    static class TestDeductionStrategy implements Deduction {

        @Override
        public MonetaryAmount calculatePrice(MonetaryAmount basePrice) {
            return null;
        }
    }

    private static final Deduction deductionStrategy = new TestDeductionStrategy();

    private static final DeductionSerializer converter = new DeductionSerializer();

    @Test
    public void shouldSerializeDeductionStrategyCorrectly() {
        String serializedDeduction = converter.convertToDatabaseColumn(deductionStrategy);

        assertEquals(deductionStrategy.getClass().getName(), serializedDeduction);
    }

    @Test
    public void shouldRecreateSerializedDeductionStrategyCorrectly() {
        Deduction deserealizedDeduction
                = converter.convertToEntityAttribute(deductionStrategy.getClass().getName());

        assertEquals(deductionStrategy.getClass(), deserealizedDeduction.getClass());
    }
}
