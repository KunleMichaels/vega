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
package eu.socialedge.vega.backend.payment.infrastructure;

import eu.socialedge.vega.backend.payment.domain.*;
import eu.socialedge.vega.backend.payment.domain.funding.PaymentMethod;
import lombok.val;

import javax.money.MonetaryAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class FakePaymentGateway implements PaymentGateway {

    private final Map<PaymentMethod, Token> tokens = new HashMap<>();

    private final Map<Authorization, MonetaryAmount> auths = new HashMap<>();

    @Override
    public <P extends PaymentMethod> Token tokenize(P paymentMethod, String description,
                                                    boolean isPrimary) {

        validateAlreadyTokenized(paymentMethod);

        val token = new Token(randomId(), ExpirationDate.MAX, description, isPrimary);

        tokens.put(paymentMethod, token);
        return token;
    }

    @Override
    public Charge charge(Token token, MonetaryAmount amount, String description) {
        validateToken(token);
        return new Charge(randomId(), token, amount, description);
    }

    @Override
    public Authorization auth(Token token, MonetaryAmount amount, String description) {
        validateToken(token);

        val auth = new Authorization(randomId(), token, amount, description);
        auths.put(auth, amount);

        return auth;
    }

    @Override
    public Capture capture(Authorization auth, MonetaryAmount amount, String statementDescription) {
        validateAuth(auth);

        val blockedAmount = auths.get(auth);
        val blockedRemainder = blockedAmount.subtract(amount);
        val isFinalCapture = blockedRemainder.isZero();

        if (blockedRemainder.isNegative())
            throw new PaymentGatewayException("Cant capture more money than was auth-ed");

        if (isFinalCapture) {
            auths.remove(auth);
        } else {
            auths.put(auth, blockedRemainder);
        }

        val desc = defaultIfBlank(statementDescription, auth.description());
        return new Capture(randomId(), auth.token(), blockedRemainder, desc, isFinalCapture);
    }

    @Override
    public Capture capture(Authorization auth, String statementDescription) {
        validateAuth(auth);

        val blockedAmount = auths.get(auth);
        val desc = defaultIfBlank(statementDescription, auth.description());

        return capture(auth, blockedAmount, desc);
    }

    private void validateAlreadyTokenized(PaymentMethod paymentMethod) {
        if (tokens.containsKey(paymentMethod))
            throw new PaymentGatewayException("Payment method is already tokenized");
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }

    private void validateAuth(Authorization authorization) {
        if (!auths.containsKey(authorization))
            throw new PaymentGatewayException("Unknown auth");
    }

    private void validateToken(Token token) {
        if (!tokens.containsValue(token))
            throw new PaymentGatewayException("Unknown token");

        if (token.isExpired()) {
            tokens.forEach((k, v) -> {
                if (v.equals(token)) tokens.remove(k);
            });

            throw new PaymentGatewayException("Token has been expired");
        }
    }
}
