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
package eu.socialedge.vega.backend.account.domain.event;

import eu.socialedge.ddd.event.DomainEventException;
import eu.socialedge.ddd.event.DomainEventHandler;
import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.vega.backend.account.domain.PassengerRepository;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.event.BookFarePaymentRequestEvent;
import eu.socialedge.vega.backend.payment.domain.Token;
import eu.socialedge.vega.backend.payment.domain.event.PaymentTokenChargeRequestEvent;
import lombok.val;

import javax.money.MonetaryAmount;

import static org.apache.commons.lang3.Validate.notNull;

public class PassengerChargeRequestEventHandler implements DomainEventHandler<PassengerFareChargeRequestEvent> {

    private static final String CHARGE_DESC_FORMAT = "Pass purchase (Fare #%d)";

    private final PassengerRepository passengerRepository;

    private final DomainEventPublisher eventPublisher;

    public PassengerChargeRequestEventHandler(PassengerRepository passengerRepository, DomainEventPublisher eventPublisher) {
        this.passengerRepository = notNull(passengerRepository);
        this.eventPublisher = notNull(eventPublisher);
    }

    @Override
    public void handleEvent(PassengerFareChargeRequestEvent event) {
        val tagId = event.tagId();
        val passengerId = event.passengerId();
        val fareId = event.fareId();
        val price = event.monetaryAmount();

        val passenger = passengerRepository.get(passengerId).orElseThrow(()
                -> new DomainEventException("No passenger found by id " + passengerId));
        val paymentToken = passenger.primaryPaymentToken();

        val paymentDescription = createPaymentDescription(fareId);

        publishTokenChargeRequest(paymentToken, price, paymentDescription);
        publishBookFarePaymentRequest(fareId, tagId);
    }

    private void publishTokenChargeRequest(Token token, MonetaryAmount amount, String desc) {
        val tokenChargeRequestEvent = PaymentTokenChargeRequestEvent.builder()
                .token(token)
                .amount(amount)
                .description(desc)
                .build();

        eventPublisher.publish(tokenChargeRequestEvent);
    }

    private void publishBookFarePaymentRequest(FareId fareId, TagId tagId) {
        val bookFarePaymentRequestEvent = BookFarePaymentRequestEvent.builder()
                .fareId(fareId)
                .tagId(tagId)
                .build();

        eventPublisher.publish(bookFarePaymentRequestEvent);
    }

    private static String createPaymentDescription(FareId fareId) {
        return String.format(CHARGE_DESC_FORMAT, fareId.value());
    }
}
