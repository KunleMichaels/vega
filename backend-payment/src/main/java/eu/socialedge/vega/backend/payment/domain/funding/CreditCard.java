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
package eu.socialedge.vega.backend.payment.domain.funding;

import eu.socialedge.vega.backend.payment.domain.ExpirationDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.CreditCardValidator;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
public class CreditCard extends PaymentMethod {

    private static final String DEFAULT_CARD_DESCR_FORMAT = "%s-%.4s*";

    private static final Pattern VALID_CVC_CODE_PATTERN = Pattern.compile("^[0-9]{3,4}$");

    private final CreditCardType cardType;

    private final Long number;

    private final String cardholder;

    private final ExpirationDate expirationDate;

    /**
     * CVC is  a security feature for "card not present" payment
     * card transactions instituted to reduce the incidence of
     * credit card fraud.
     *
     * <p>CVC is a <b>transient value</b> since according to PCI SSC,
     * CVC and other sensitive authentication data must not be stored
     * after authorization (even if encrypted).</p>
     */
    private final transient Integer cvc;

    public CreditCard(CreditCardType cardType, Long number, String cardholder,
                      ExpirationDate expirationDate, Integer cvc,
                      String description) {
        super(description);

        this.cardType = notNull(cardType);
        this.cardholder = notBlank(cardholder);
        this.expirationDate = notNull(expirationDate);
        this.cvc = cvc;

        if(!VALID_CVC_CODE_PATTERN.matcher(String.valueOf(notNull(cvc))).matches())
            throw new IllegalArgumentException("Invalid cvc code provided");

        CreditCardValidator cardValidator = new CreditCardValidator(cardType.code());
        if (!cardValidator.isValid(String.valueOf(number)))
            throw new IllegalArgumentException(number + " is not a valid " +
                    cardType.name() + " card number");

        this.number = number;
    }

    public CreditCard(CreditCardType cardType, Long number, String cardholder,
                      ExpirationDate expirationDate, Integer cvc) {
        this(cardType, number, cardholder, expirationDate, cvc,
                String.format(DEFAULT_CARD_DESCR_FORMAT, cardType.name(), number));
    }
}
