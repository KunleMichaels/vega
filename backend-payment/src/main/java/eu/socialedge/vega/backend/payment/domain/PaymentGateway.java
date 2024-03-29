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

import eu.socialedge.vega.backend.payment.domain.funding.PaymentMethod;

import javax.money.MonetaryAmount;

public interface PaymentGateway {

    <P extends PaymentMethod> Token tokenize(P paymentMethod, String tokenDescription,
                                             boolean isPrimaryToken);

    default <P extends PaymentMethod> Token tokenize(P paymentMethod) {
        return tokenize(paymentMethod, null, false);
    }

    Charge charge(Token token, MonetaryAmount amount, String description);

    Authorization auth(Token token, MonetaryAmount amount, String description);

    Capture capture(Authorization auth, MonetaryAmount amount, String statementDescription);

    default Capture capture(Authorization auth, MonetaryAmount amount) {
        return capture(auth, amount, null);
    }

    Capture capture(Authorization auth, String statementDescription);

    default Capture capture(Authorization auth) {
        return capture(auth, (String) null);
    }
}
