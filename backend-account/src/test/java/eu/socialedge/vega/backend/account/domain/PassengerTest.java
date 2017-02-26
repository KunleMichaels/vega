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

import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.payment.domain.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PassengerTest {

    private static Set<TagId> notEmptyTagIdSet = new HashSet<TagId>() {{
        add(new TagId());
    }};

    private static Set<Token> validPaymentTokenSet = new HashSet<Token>() {{
        add(createPrimaryPaymentTokenMock());
        add(createNotPrimaryPaymentTokenMock());
    }};

    private static final List<String> INVALID_EMAIL_ADDRESSES =
            new ArrayList<String>() {{
                add("plainaddress");
                add("@example.com");
                add("Joe Smith <email@example.com>");
                add("email.example.com");
                add(".email@example.com");
                add("email@-example.com");
                add("email@example..com");
            }};

    private static final List<String> VALID_EMAIL_ADDRESSES =
            new ArrayList<String>() {{
                add("email@example.com");
                add("firstname.lastname@example.com");
                add("email@subdomain.example.com");
                add("firstname+lastname@example.com");
                add("email@example.co.jp");
                add("email@example.name");
            }};

    @Test
    public void shouldAddNonPrimaryTokensCorrectly() {
        Passenger p = new Passenger(new PassengerId(), "test", VALID_EMAIL_ADDRESSES.get(0),
                "12", notEmptyTagIdSet, validPaymentTokenSet);

        p.addPaymentToken(createNotPrimaryPaymentTokenMock());
    }

    @Test
    public void shouldThrowExceptionFor2OrMorePrimaryTokens() {
        HashSet<Token> invalidTokenSet = new HashSet<Token>() {{
            add(createPrimaryPaymentTokenMock());
            add(createPrimaryPaymentTokenMock());
        }};

        assertThatThrownBy(() ->
                new Passenger(new PassengerId(), "test", VALID_EMAIL_ADDRESSES.get(0),
                        "12", notEmptyTagIdSet, invalidTokenSet))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionForPrimaryTokensSetWithoutPrimaryOne() {
        HashSet<Token> invalidTokenSet = new HashSet<Token>() {{
            add(createNotPrimaryPaymentTokenMock());
            add(createNotPrimaryPaymentTokenMock());
        }};

        assertThatThrownBy(() ->
                new Passenger(new PassengerId(), "test", VALID_EMAIL_ADDRESSES.get(0),
                        "12", notEmptyTagIdSet, invalidTokenSet))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionOnAddingSecondPrimaryPaymentToken() {
        Set<Token> tokenSet = new HashSet<>(validPaymentTokenSet);

        Passenger p = new Passenger(new PassengerId(), "test", VALID_EMAIL_ADDRESSES.get(0), "12",
                notEmptyTagIdSet, tokenSet);

        assertThatThrownBy(() -> p.addPaymentToken(createPrimaryPaymentTokenMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotThrowExceptionOnAddingSecondPrimaryPaymentTokenAfterDeletingPreviousOne() {
        Token primaryTokenToRemove = createPrimaryPaymentTokenMock();
        HashSet<Token> validTokenSet = new HashSet<Token>() {{
            add(createNotPrimaryPaymentTokenMock());
            add(primaryTokenToRemove);
        }};

        Passenger p = new Passenger(new PassengerId(), "test", VALID_EMAIL_ADDRESSES.get(0), "12",
                notEmptyTagIdSet, validTokenSet);

        p.removePaymentToken(primaryTokenToRemove);
        p.addPaymentToken(createPrimaryPaymentTokenMock());
    }

    @Test
    public void shouldThrowExceptionForInvalidEmailAddress() {
        INVALID_EMAIL_ADDRESSES.forEach(email -> {
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(), "test", email, "12",
                            notEmptyTagIdSet, validPaymentTokenSet))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(), "test", email, "12",
                            notEmptyTagIdSet, validPaymentTokenSet)
                                .email(email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
        });
    }

    @Test
    public void shouldNotThrowExceptionForValidEmailAddressAndPaymentTokensSet() {
        VALID_EMAIL_ADDRESSES.forEach(email -> {
            new Passenger(new PassengerId(), "test", email, "12",
                    notEmptyTagIdSet, validPaymentTokenSet)
                        .email(email);
        });
    }

    private static Token createPrimaryPaymentTokenMock() {
        Token tokenMock = mock(Token.class);
        when(tokenMock.identifier()).thenReturn("primary");
        when(tokenMock.isPrimary()).thenReturn(true);
        return tokenMock;
    }

    private static Token createNotPrimaryPaymentTokenMock() {
        Token tokenMock = mock(Token.class);
        when(tokenMock.identifier()).thenReturn("notPrimary");
        when(tokenMock.isPrimary()).thenReturn(false);
        return tokenMock;
    }
}
