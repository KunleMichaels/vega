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
package eu.socialedge.vega.backend.account.domain;

import eu.socialedge.vega.backend.account.domain.funding.PaymentMethod;
import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.shared.PassengerId;
import eu.socialedge.vega.backend.shared.TokenId;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Passenger extends AggregateRoot<PassengerId> {

    private String name;

    private String email;

    private String password;

    private final Set<TokenId> tokenIds;

    private final Set<PaymentMethod> paymentMethods;

    public Passenger(PassengerId id, String name, String email, String password,
                     Set<TokenId> tokenIds, Set<PaymentMethod> paymentMethods) {
        super(id);

        this.name = notBlank(name);
        this.password = notBlank(password);
        this.tokenIds = notEmpty(tokenIds);
        this.paymentMethods = notEmpty(paymentMethods);

        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(email + " is not a valid email address");

        this.email = email;
    }

    public void name(String name) {
        this.name = notBlank(name);
    }

    public void email(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(email + " is not a valid email address");

        this.email = email;
    }

    public void password(String password) {
        this.password = notBlank(password);
    }
}

