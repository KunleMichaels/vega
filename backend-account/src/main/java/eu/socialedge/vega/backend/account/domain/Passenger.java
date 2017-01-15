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

import eu.socialedge.vega.backend.ddd.DeactivatableAggregateRoot;
import eu.socialedge.vega.backend.payment.domain.Token;
import eu.socialedge.vega.backend.transit.domain.TagId;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.*;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Passenger extends DeactivatableAggregateRoot<PassengerId> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_shadow", nullable = false)
    private boolean isShadow;

    @ElementCollection
    @CollectionTable(name = "passenger_token", joinColumns = @JoinColumn(name = "passenger_id"))
    private final Set<TagId> tagIds;

    @ElementCollection
    @CollectionTable(name = "passenger_pmethods", joinColumns = @JoinColumn(name = "passenger_id"))
    private final Set<Token> paymentTokens;

    public Passenger(PassengerId id, String name, String email, String password,
                     Set<TagId> tagIds, Set<Token> paymentTokens, boolean isShadow) {
        super(id);

        this.name = notBlank(name);
        this.password = notBlank(password);
        this.tagIds = notEmpty(tagIds);
        this.paymentTokens = notEmpty(paymentTokens);
        this.isShadow = isShadow;

        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(email + " is not a valid email address");

        this.email = email;
    }

    public Passenger(PassengerId id, String name, String email, String password,
                     Set<TagId> tagIds, Set<Token> paymentTokens) {
        this(id, name, email, password, tagIds, paymentTokens, false);
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

