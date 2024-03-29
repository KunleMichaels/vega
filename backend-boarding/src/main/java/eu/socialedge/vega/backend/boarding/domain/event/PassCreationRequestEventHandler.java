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
package eu.socialedge.vega.backend.boarding.domain.event;

import eu.socialedge.ddd.event.DomainEventException;
import eu.socialedge.ddd.event.DomainEventHandler;
import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.vega.backend.boarding.domain.Pass;
import eu.socialedge.vega.backend.boarding.domain.TagRepository;
import eu.socialedge.vega.backend.payment.domain.ExpirationDate;
import lombok.val;

import java.time.Instant;
import java.time.LocalDate;

import static org.apache.commons.lang3.Validate.notNull;

public class PassCreationRequestEventHandler implements DomainEventHandler<PassCreationRequestEvent> {

    private final TagRepository tagRepository;

    private final DomainEventPublisher eventPublisher;

    public PassCreationRequestEventHandler(TagRepository tagRepository, DomainEventPublisher eventPublisher) {
        this.tagRepository = notNull(tagRepository);
        this.eventPublisher = notNull(eventPublisher);
    }

    @Override
    public void handleEvent(PassCreationRequestEvent event) {
        val tagId = event.tagId();
        val fareId = event.fareId();
        val activation = Instant.now();
        val expirationDateTime = LocalDate.from(activation.plus(event.validity()));
        val expirationDate = new ExpirationDate(expirationDateTime);
        val vehicleTypes = event.vehicleTypes();
        val zone = event.zone();
        val operatorsIds = event.operatorIds();

        val tag = tagRepository.get(tagId).orElseThrow(()
                -> new DomainEventException("Cant create pass. Not Tag with given id found"));

        val pass = new Pass(fareId, activation, expirationDate, vehicleTypes, zone, operatorsIds);
        tag.addPass(pass);
    }
}
