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

import lombok.val;
import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;

public class DeductionTest {

    @Test
    public void shouldThrowExceptionOnCreatingDeductionWithInvalidMultiplier() {
        assertThatThrownBy(() -> new Deduction("negative", -1))
            .isInstanceOf(IllegalArgumentException.class);

        new Deduction("but zero is ok", 0);
    }

    @Test
    public void shouldApplyDeductionCorrectly() throws Exception {
        val basePrice = Money.of(20, "UAH");

        val nopDeduction = new Deduction("x1", 1);
        assertEquals(basePrice, nopDeduction.apply(basePrice));

        val x2Deduction = new Deduction("x2", 2);
        assertEquals(basePrice.multiply(2), x2Deduction.apply(basePrice));

        val x1_5Deduction = new Deduction("x1.5", 1.5);
        assertEquals(basePrice.multiply(1.5), x1_5Deduction.apply(basePrice));
    }

}
