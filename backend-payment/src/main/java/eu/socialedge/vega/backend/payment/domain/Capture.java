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

import javax.money.MonetaryAmount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
public class Capture extends Transaction {

    private final MonetaryAmount reminder;

    private final boolean isFinal;

    public Capture(String id, Token token, MonetaryAmount reminder, String description,
                   boolean isFinal) {
        super(id, token, description);
        this.reminder = notNull(reminder);
        this.isFinal = isFinal;
    }

    public Capture(String id, Token token, MonetaryAmount reminder, String description) {
        this(id, token, reminder, description, true);
    }

    public Capture(String id, Token token, MonetaryAmount reminder, boolean isFinal) {
        this(id, token, reminder, null, isFinal);
    }

    public Capture(String id, Token token, MonetaryAmount reminder) {
        this(id, token, reminder, null, true);
    }
}
