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
package eu.socialedge.vega.backend.payment.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpirationDateTest {
    @Test
    public void shouldIdentifyItsExpirationCorrectly() throws Exception {
        ExpirationDate expiredExpDate = new ExpirationDate(7, 1995);
        assertTrue(expiredExpDate.isExpired());

        ExpirationDate nonExpiredExpDate = new ExpirationDate(7, 2024);
        assertFalse(nonExpiredExpDate.isExpired());
    }

    @Test
    public void expDateConstructedWithoutDayShouldPointToFirstDatOfMonth() {
        ExpirationDate expDate = new ExpirationDate(7, 1995);
        assertEquals(Integer.valueOf(1), expDate.day());
    }

    @Test
    public void expDateConstructedWith2digYearShouldPointToThisDecade() {
        ExpirationDate expDate = new ExpirationDate(1, 2, 24);
        assertEquals(Integer.valueOf(2024), expDate.year());
    }

    @Test
    public void shouldReturnValidInternalValuesAsStringValues() {
        ExpirationDate expirationDate = new ExpirationDate(5, 5, 1995);
        assertEquals("5", expirationDate.dayAsString());
        assertEquals("5", expirationDate.monthAsString());
        assertEquals("1995", expirationDate.yearAsString());
    }

    @Test
    public void shouldReturnValidInternalValues() {
        ExpirationDate expirationDate = new ExpirationDate(5, 5, 1995);
        assertEquals(Integer.valueOf(5), expirationDate.day());
        assertEquals(Integer.valueOf(5), expirationDate.month());
        assertEquals(Integer.valueOf(1995), expirationDate.year());
    }

    @Test
    public void shouldReturnValidLeadingZerosValues() {
        ExpirationDate expDateReqLz = new ExpirationDate(1, 2, 1995);
        assertEquals("01", expDateReqLz.dayAsString(true));
        assertEquals("02", expDateReqLz.monthAsString(true));

        ExpirationDate expDateNotReqLz = new ExpirationDate(10, 12, 1995);
        assertEquals("10", expDateNotReqLz.dayAsString(true));
        assertEquals("12", expDateNotReqLz.monthAsString(true));
    }
}