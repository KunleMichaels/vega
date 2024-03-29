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

import eu.socialedge.ddd.domain.DeactivatableAggregateRoot;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "passenger_token", joinColumns = @JoinColumn(name = "passenger_id"))
    private final Set<TagId> tagIds;

    @ElementCollection
    @CollectionTable(name = "passenger_pmethods", joinColumns = @JoinColumn(name = "passenger_id"))
    private final Set<Token> paymentTokens;

    public Passenger(PassengerId id, String name, String email, String password,
                     Set<TagId> tagIds, Set<Token> paymentTokens) {
        super(id);

        this.name = notBlank(name);
        this.password = notBlank(password);
        this.tagIds = isNull(tagIds) ? new HashSet<>() : new HashSet<>(tagIds);

        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(email + " is not a valid email address");

        this.email = email;

        if (isNull(paymentTokens)) {
            this.paymentTokens = new HashSet<>();
            this.isActive = false;
        } else {
            if (countPrimaryPaymentTokens(paymentTokens) != 1)
                throw new IllegalArgumentException("One (only) payment token must be primary");

            this.paymentTokens = new HashSet<>(paymentTokens);
        }
    }

    public Passenger(String name, String email, String password,
                     Set<TagId> tagIds, Set<Token> paymentTokens) {
        this(new PassengerId(), name, email, password, tagIds, paymentTokens);
    }

    public Passenger(PassengerId id, String name, String email, String password) {
        this(id, name, email, password, null, null);
    }

    public Passenger(String name, String email, String password) {
        this(new PassengerId(), name, email, password);
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

    public boolean addTagId(TagId tagId) {
        return tagIds.add(notNull(tagId));
    }

    public boolean removeTagId(TagId tagId) {
        return tagIds.remove(tagId);
    }

    public void removeTagIds() {
        tagIds.clear();
    }

    public Set<TagId> tagIds() {
        return Collections.unmodifiableSet(tagIds);
    }

    public boolean addPaymentToken(Token token) {
        Validate.notNull(token);

        if (token.isPrimary() && containsPrimaryPaymentToken(this.paymentTokens))
            throw new IllegalArgumentException("You cant add more than one primary token");

        return paymentTokens.add(token);
    }

    public boolean removePaymentToken(Token token) {
        return paymentTokens.remove(token);
    }

    public void removePaymentTokens() {
        paymentTokens.clear();
    }

    public Set<Token> paymentTokens() {
        return Collections.unmodifiableSet(paymentTokens);
    }

    public Set<Token> validPaymentTokens() {
        return paymentTokens.stream()
                .filter(t -> !t.isExpired())
                .collect(Collectors.toSet());
    }

    public Token primaryPaymentToken() {
        return paymentTokens.stream()
                .filter(t -> !t.isExpired())
                .filter(Token::isPrimary).findAny().get();
    }

    private static int countPrimaryPaymentTokens(Set<Token> tokens) {
        return Math.toIntExact(tokens.stream().filter(Token::isPrimary).count());
    }

    private static boolean containsPrimaryPaymentToken(Set<Token> tokens) {
        return countPrimaryPaymentTokens(tokens) > 0;
    }
}

