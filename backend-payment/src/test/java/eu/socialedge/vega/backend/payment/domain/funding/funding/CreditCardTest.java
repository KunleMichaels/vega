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
package eu.socialedge.vega.backend.payment.domain.funding.funding;

import eu.socialedge.vega.backend.payment.domain.funding.CreditCard;
import eu.socialedge.vega.backend.payment.domain.funding.CreditCardType;
import eu.socialedge.vega.backend.payment.domain.funding.ExpirationDate;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.socialedge.vega.backend.payment.domain.funding.CreditCardType.AMEX;
import static eu.socialedge.vega.backend.payment.domain.funding.CreditCardType.DINERS;
import static eu.socialedge.vega.backend.payment.domain.funding.CreditCardType.MASTERCARD;
import static eu.socialedge.vega.backend.payment.domain.funding.CreditCardType.VISA;
import static eu.socialedge.vega.backend.payment.domain.funding.CreditCardType.VPAY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreditCardTest {
    /*
     * https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm
     */
    private static final Map<CreditCardType, List<Long>> VALID_CARD_NUMS =
            new HashMap<CreditCardType, List<Long>>() {{
                put(VISA, Arrays.asList(4222222222222L, 4417123456789113L));
                put(AMEX, Arrays.asList(378282246310005L, 378734493671000L));
                put(MASTERCARD, Arrays.asList(5105105105105100L, 5555555555554444L));
                put(DINERS, Arrays.asList(38520000023237L, 30569309025904L));
                put(VPAY, Arrays.asList(4370000000000061L, 4370000000000012L));
            }};
    private static final Map<CreditCardType, Long> INVALID_CARD_NUMS =
            new HashMap<CreditCardType, Long>() {{
                put(VISA, 4417123456789112L);
                put(VISA, 4222222222229L);
                put(AMEX, 378282246310001L);
                put(MASTERCARD, 5105105105105105L);
                put(DINERS, 6011000990139421L);
                put(VPAY, 4370000000000069L);
            }};

    private static final List<Integer> VALID_CVV_CODES =
            new ArrayList<Integer>() {{
                add(100);
                add(999);
                add(1000);
                add(9999);
            }};

    private static final List<Integer> INVALID_CVV_CODES =
            new ArrayList<Integer>() {{
                add(-1);
                add(0);
                add(1);
                add(10);
                add(99);
                add(10000);
                add(10001);
                add(99999);
            }};

    @Test
    public void shouldNotThrowExceptionForValidCardNumbers() {
        VALID_CARD_NUMS.forEach((cardType, numberList) -> {
            numberList.forEach(number -> {
                new CreditCard(cardType, number, "test",
                        new ExpirationDate(LocalDate.now()), VALID_CVV_CODES.get(0));
            });
        });
    }

    @Test
    public void shouldThrowExceptionForInvalidCardNumbers() {
        INVALID_CARD_NUMS.forEach((cardType, number) -> {
            assertThatThrownBy(() ->
                    new CreditCard(cardType, number, "test",
                            new ExpirationDate(LocalDate.now()), VALID_CVV_CODES.get(0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("card number");
        });
    }

    @Test
    public void shouldNotThrowExceptionForValidCVCodes() {
        Long validVisaCardNum = VALID_CARD_NUMS.get(VISA).get(0);
        VALID_CVV_CODES.forEach(cvc -> {
            new CreditCard(VISA, validVisaCardNum, "test",
                    new ExpirationDate(LocalDate.now()), cvc);
        });
    }

    @Test
    public void shouldThrowExceptionForInvalidCVCodes() {
        Long validVisaCardNum = VALID_CARD_NUMS.get(VISA).get(0);
        INVALID_CVV_CODES.forEach(cvc -> {
            assertThatThrownBy(() ->
                    new CreditCard(VISA, validVisaCardNum, "test",
                            new ExpirationDate(LocalDate.now()), cvc))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cvc code");
        });
    }
}