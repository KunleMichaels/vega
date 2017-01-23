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

import eu.socialedge.vega.backend.boarding.domain.Pass;
import eu.socialedge.vega.backend.boarding.domain.TagRepository;
import eu.socialedge.vega.backend.ddd.DomainEventHandler;
import eu.socialedge.vega.backend.ddd.DomainEventPublisher;
import lombok.val;

import java.time.LocalDateTime;

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
        val activation = LocalDateTime.now();
        val expiration = activation.plus(event.validity());
        val vehicleTypes = event.vehicleTypes();
        val zone = event.zone();
        val operatorsIds = event.operatorIds();

        val tag = tagRepository.get(tagId).orElseThrow(()
                -> new IllegalArgumentException("Cant create pass. Not Tag with given id found"));

        val pass = new Pass(fareId, activation, expiration, vehicleTypes, zone, operatorsIds);
        tag.passes().add(pass);
    }
}
