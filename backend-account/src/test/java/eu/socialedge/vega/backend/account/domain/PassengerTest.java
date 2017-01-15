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

import eu.socialedge.vega.backend.payment.domain.Token;
import eu.socialedge.vega.backend.transit.domain.TagId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PassengerTest {

    @SuppressWarnings("unchecked")
    private static HashSet<TagId> notEmptyTagIdSet =
            (HashSet<TagId>) mock(HashSet.class);

    @SuppressWarnings("unchecked")
    private static HashSet<Token> notEmptyPaymentTokenSet =
            (HashSet<Token>) mock(HashSet.class);

    static {
        when(notEmptyTagIdSet.isEmpty()).thenReturn(false);
        when(notEmptyPaymentTokenSet.isEmpty()).thenReturn(false);
    }

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
    public void shouldThrowExceptionForInvalidEmailAddress() {
        INVALID_EMAIL_ADDRESSES.forEach(email -> {
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(1L), "test", email, "12", notEmptyTagIdSet, notEmptyPaymentTokenSet))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
            assertThatThrownBy(() ->
                    new Passenger(new PassengerId(1L), "test", email, "12", notEmptyTagIdSet, notEmptyPaymentTokenSet)
                                .email(email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("email address");
        });
    }
    @Test
    public void shouldNotThrowExceptionForValidEmailAddress() {
        VALID_EMAIL_ADDRESSES.forEach(email -> {
            new Passenger(new PassengerId(1L), "test", email, "12", notEmptyTagIdSet, notEmptyPaymentTokenSet)
                        .email(email);
        });
    }
}