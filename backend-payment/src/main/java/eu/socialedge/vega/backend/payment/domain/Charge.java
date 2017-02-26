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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.money.MonetaryAmount;

import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
public class Charge extends Transaction {

    private final MonetaryAmount amount;

    public Charge(String id, Token token, MonetaryAmount amount, String description) {
        super(id, token, description);
        this.amount = notNull(amount);
    }

    public Charge(String id, Token token, MonetaryAmount amount) {
        this(id, token, amount, null);
    }
}
