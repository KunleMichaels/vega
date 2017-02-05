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
package eu.socialedge.vega.backend.payment.domain.event;

import eu.socialedge.ddd.event.DomainEventHandler;
import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.vega.backend.payment.domain.Charge;
import eu.socialedge.vega.backend.payment.domain.PaymentGateway;
import lombok.val;

import static org.apache.commons.lang3.Validate.notNull;

public class PaymentTokenChargeRequestEventHandler implements DomainEventHandler<PaymentTokenChargeRequestEvent> {

    private final PaymentGateway paymentGateway;

    private final DomainEventPublisher eventPublisher;

    public PaymentTokenChargeRequestEventHandler(PaymentGateway paymentGateway, DomainEventPublisher eventPublisher) {
        this.paymentGateway = notNull(paymentGateway);
        this.eventPublisher = notNull(eventPublisher);
    }

    @Override
    public void handleEvent(PaymentTokenChargeRequestEvent event) {
        val props = event.properties();
        val paymentToken = event.token();
        val price = event.amount();
        val desc = event.description();

        Charge charge = paymentGateway.charge(paymentToken, price, desc);

        val tokenChargedNotificationEvent = PaymentTokenChargedNotificationEvent.builder()
                .properties(props)
                .description(desc)
                .token(paymentToken)
                .transactionId(charge.id())
                .build();
        eventPublisher.publish(tokenChargedNotificationEvent);
    }
}
