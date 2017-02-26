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
package eu.socialedge.vega.backend.fare.domain.event;

import eu.socialedge.ddd.event.DomainEventHandler;
import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.vega.backend.boarding.domain.event.PassCreationRequestEvent;
import eu.socialedge.vega.backend.fare.domain.FareRepository;
import lombok.val;

import static org.apache.commons.lang3.Validate.notNull;

public class BookFarePaymentRequestEventHandler implements DomainEventHandler<BookFarePaymentRequestEvent> {

    private final FareRepository fareRepository;

    private final DomainEventPublisher eventPublisher;

    public BookFarePaymentRequestEventHandler(FareRepository fareRepository, DomainEventPublisher eventPublisher) {
        this.fareRepository = notNull(fareRepository);
        this.eventPublisher = notNull(eventPublisher);
    }

    @Override
    public void handleEvent(BookFarePaymentRequestEvent event) {
        val fareId = event.fareId();
        val tagId = event.tagId();

        val fare = fareRepository.get(fareId).orElseThrow(()
                -> new IllegalArgumentException("No Fare with given id found"));

        val fareDetailsResponseEvent = PassCreationRequestEvent.builder()
                .fareId(fare.id())
                .tagId(tagId)
                .operatorIds(fare.operatorIds())
                .validity(fare.validity())
                .vehicleTypes(fare.vehicleTypes())
                .zone(fare.zone())
                .build();

        eventPublisher.publish(fareDetailsResponseEvent);
    }
}
