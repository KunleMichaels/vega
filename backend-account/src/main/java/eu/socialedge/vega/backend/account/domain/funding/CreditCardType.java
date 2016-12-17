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
package eu.socialedge.vega.backend.account.domain.funding;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@Embeddable
public enum CreditCardType {
    /**
     * Option specifying that American Express cards are allowed.
     */
    AMEX(1 << 0),

    /**
     * Option specifying that Visa cards are allowed.
     */
    VISA(1 << 1),

    /**
     * Option specifying that Mastercard cards are allowed.
     */
    MASTERCARD(1 << 2),

    /**
     * Option specifying that Discover cards are allowed.
     */
    DISCOVER(1 << 3),

    /**
     * Option specifying that Diners cards are allowed.
     */
    DINERS(1 << 4),

    /**
     * Option specifying that VPay (Visa) cards are allowed.
     */
    VPAY(1 << 5);

    private long code;

    CreditCardType(long code) {
        this.code = code;
    }
}
