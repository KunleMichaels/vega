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

import eu.socialedge.vega.backend.ddd.ValueObject;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.val;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class ExpirationDate extends ValueObject {

    private static final Integer DEFAULT_EXPIRATION_DATE = 1;
    private static final String LEADING_ZEROS_STRING_FORMAT = "%02d";

    public static final ExpirationDate MAX = new ExpirationDate(LocalDate.MAX);
    public static final ExpirationDate MIN = new ExpirationDate(LocalDate.MIN);

    @Column(nullable = false)
    private final LocalDate localDate;

    public ExpirationDate(LocalDate localDate) {
        this.localDate = notNull(localDate);
    }

    public ExpirationDate(Integer day, Integer month, Integer year) {
        val currentDate = LocalDate.now();
        val currentYear = currentDate.getYear();
        if (year > 0 && year < 100) {
            val century = currentYear / 100;
            val normalizedYear = century * 100 + year;

            this.localDate = LocalDate.of(normalizedYear, month,
                    isNull(day) ? DEFAULT_EXPIRATION_DATE : day);
        } else {
            this.localDate = LocalDate.of(year, month,
                    isNull(day) ? DEFAULT_EXPIRATION_DATE : day);;
        }
    }

    public ExpirationDate(Integer month, Integer year) {
        this(null, month, year);
    }

    public Integer day() {
        return localDate.getDayOfMonth();
    }

    public String dayAsString() {
        return dayAsString(false);
    }

    public String dayAsString(boolean keepLeadingZero) {
        val day = localDate.getDayOfMonth();

        if (!keepLeadingZero)
            return String.valueOf(day);

        return String.format(LEADING_ZEROS_STRING_FORMAT, day);
    }

    public Integer month() {
        return localDate.getMonthValue();
    }

    public String monthAsString() {
        return monthAsString(false);
    }

    public String monthAsString(boolean keepLeadingZero) {
        val month = localDate.getMonthValue();

        if (!keepLeadingZero)
            return String.valueOf(month);

        return String.format(LEADING_ZEROS_STRING_FORMAT, month);
    }

    public Integer year() {
        return localDate.getYear();
    }

    public String yearAsString() {
        return yearAsString(false);
    }

    public String yearAsString(boolean keepLeadingZero) {
        val year = localDate.getYear();

        if (!keepLeadingZero)
            return String.valueOf(year);

        return String.format(LEADING_ZEROS_STRING_FORMAT, year);
    }

    public boolean isExpired() {
        return localDate.isBefore(LocalDate.now());
    }
}
