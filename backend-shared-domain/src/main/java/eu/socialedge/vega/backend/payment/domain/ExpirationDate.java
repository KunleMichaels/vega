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

import eu.socialedge.ddd.domain.ValueObject;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.val;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import static java.util.Objects.isNull;

@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Embeddable @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class ExpirationDate extends ValueObject {

    private static final LocalTime LOCAL_EXPIRATION_TIME = LocalTime.MIDNIGHT;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE;

    private static final Integer DEFAULT_EXPIRATION_DATE = 1;
    private static final String LEADING_ZEROS_STRING_FORMAT = "%02d";

    public static final ExpirationDate MAX = new ExpirationDate(LocalDate.MAX);
    public static final ExpirationDate MIN = new ExpirationDate(LocalDate.MIN);

    @Column(nullable = false)
    private final ZonedDateTime zonedDateTime;

    public ExpirationDate(LocalDate localDate) {
        this(localDate, null);
    }

    public ExpirationDate(LocalDate localDate, ZoneOffset zoneOffset) {
        this.zonedDateTime = ZonedDateTime.of(localDate, LOCAL_EXPIRATION_TIME,
            isNull(zoneOffset) ? systemZoneOffset() : zoneOffset);
    }

    public ExpirationDate(Integer day, Integer month, Integer year) {
        this(day, month, year, null);
    }

    public ExpirationDate(Integer day, Integer month, Integer year, ZoneOffset zoneOffset) {
        val currentDate = LocalDate.now();
        val currentYear = currentDate.getYear();

        LocalDate localDate;
        if (year > 0 && year < 100) {
            val century = currentYear / 100;
            val normalizedYear = century * 100 + year;

            localDate = LocalDate.of(normalizedYear, month,
                    isNull(day) ? DEFAULT_EXPIRATION_DATE : day);
        } else {
            localDate = LocalDate.of(year, month,
                    isNull(day) ? DEFAULT_EXPIRATION_DATE : day);
        }

        this.zonedDateTime = ZonedDateTime.of(localDate, LOCAL_EXPIRATION_TIME,
            isNull(zoneOffset) ? systemZoneOffset() : zoneOffset);
    }

    public ExpirationDate(Integer month, Integer year) {
        this(null, month, year);
    }

    public ZoneOffset zoneOffset() {
        return zonedDateTime.getOffset();
    }

    public String zoneOffsetAsString() {
        return zonedDateTime.getOffset().toString();
    }

    public Integer day() {
        return zonedDateTime.getDayOfMonth();
    }

    public String dayAsString() {
        return dayAsString(false);
    }

    public String dayAsString(boolean keepLeadingZero) {
        val day = zonedDateTime.getDayOfMonth();

        if (!keepLeadingZero)
            return String.valueOf(day);

        return String.format(LEADING_ZEROS_STRING_FORMAT, day);
    }

    public Integer month() {
        return zonedDateTime.getMonthValue();
    }

    public String monthAsString() {
        return monthAsString(false);
    }

    public String monthAsString(boolean keepLeadingZero) {
        val month = zonedDateTime.getMonthValue();

        if (!keepLeadingZero)
            return String.valueOf(month);

        return String.format(LEADING_ZEROS_STRING_FORMAT, month);
    }

    public Integer year() {
        return zonedDateTime.getYear();
    }

    public String yearAsString() {
        return yearAsString(false);
    }

    public String yearAsString(boolean keepLeadingZero) {
        val year = zonedDateTime.getYear();

        if (!keepLeadingZero)
            return String.valueOf(year);

        return String.format(LEADING_ZEROS_STRING_FORMAT, year);
    }

    public boolean occurred() {
        return zonedDateTime.isBefore(ZonedDateTime.now());
    }

    public boolean isBefore(ExpirationDate otherExpDate) {
        return zonedDateTime.isBefore(otherExpDate.toZonedDateTime());
    }

    public boolean isAfter(ExpirationDate otherExpDate) {
        return zonedDateTime.isAfter(otherExpDate.toZonedDateTime());
    }

    public ZonedDateTime toZonedDateTime() {
        return zonedDateTime;
    }

    public LocalDate toLocalDate() {
        return zonedDateTime.toLocalDate();
    }

    public Instant toInstant() {
        return zonedDateTime.toInstant();
    }

    @Override
    public String toString() {
        return zonedDateTime.format(FORMATTER);
    }

    private static ZoneOffset systemZoneOffset() {
        return ZoneId.systemDefault().getRules().getOffset(LocalDateTime.MAX);
    }

    public static ExpirationDate parse(String zonedDate) {
        val date = LocalDate.parse(zonedDate, FORMATTER);
        val zoneOffsetSec = FORMATTER.parse(zonedDate).get(ChronoField.OFFSET_SECONDS);
        val zoneOffset = ZoneOffset.ofTotalSeconds(zoneOffsetSec);

        return new ExpirationDate(date, zoneOffset);
    }

    public static ExpirationDate now() {
        return new ExpirationDate(LocalDate.now());
    }
}
