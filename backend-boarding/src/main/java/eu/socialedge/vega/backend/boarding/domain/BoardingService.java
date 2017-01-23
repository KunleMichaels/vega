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
package eu.socialedge.vega.backend.boarding.domain;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.boarding.domain.event.FareBoardingConfirmationEvent;
import eu.socialedge.vega.backend.boarding.domain.event.PassBoardingConfirmationEvent;
import eu.socialedge.vega.backend.fare.domain.event.FareChargeRequestEvent;
import eu.socialedge.vega.backend.ddd.DomainEventPublisher;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.fare.domain.FareId;
import lombok.val;
import org.apache.commons.lang3.Validate;

import static org.apache.commons.lang3.Validate.notNull;

public class BoardingService {

    private final TagRepository tagRepository;

    private final DomainEventPublisher eventPublisher;

    public BoardingService(TagRepository tagRepository,
                           DomainEventPublisher eventPublisher) {
        this.tagRepository = notNull(tagRepository);
        this.eventPublisher = notNull(eventPublisher);
    }

    public void board(TagId tagId, TerminalId terminalId, Location location) {
        Validate.notNull(tagId);
        Validate.notNull(location);
        Validate.notNull(terminalId);

        val tag = tagRepository.get(tagId).orElseThrow(()
                -> new BoardingException("Could not find tag. User not registered?"));
        val nonExpiredPasses = tag.nonExpiredPasses();

        if (nonExpiredPasses.isEmpty())
            throw new BoardingException("No unexpired pass found");

        val locationIsEligible = nonExpiredPasses.stream()
                .map(Pass::zone)
                .anyMatch(zone -> zone.contains(location));

        if (!locationIsEligible)
            throw new BoardingException("None of pass's zone contains shared provided");

        publishPassBoardingConfirmation(tagId, tag.passengerId(), terminalId, location);
    }

    public void board(TagId tagId, TerminalId terminalId, FareId fareId, Location location) {
        Validate.notNull(tagId);
        Validate.notNull(fareId);
        Validate.notNull(location);
        Validate.notNull(terminalId);

        val tag = tagRepository.get(tagId).orElseThrow(()
                -> new BoardingException("Could not find tag. User not registered?"));

        publishFareChargeRequest(tagId, tag.passengerId(), fareId, location);
        publishFareBoardingConfirmation(tagId, tag.passengerId(), terminalId, fareId, location);
    }

    protected void publishFareChargeRequest(TagId tagId, PassengerId passengerId, FareId fareId, Location location) {
        val fareChargeRequestEvent = FareChargeRequestEvent.builder()
                .tagId(tagId)
                .passengerId(passengerId)
                .fareId(fareId)
                .build();

        eventPublisher.publish(fareChargeRequestEvent);
    }

    protected void publishPassBoardingConfirmation(TagId tagId, PassengerId passengerId, TerminalId terminalId,
                                                   Location location) {
        val boardingConfirmationEvent = PassBoardingConfirmationEvent.builder()
                .tagId(tagId)
                .passengerId(passengerId)
                .terminalId(terminalId)
                .location(location)
                .build();

        eventPublisher.publish(boardingConfirmationEvent);
    }

    protected void publishFareBoardingConfirmation(TagId tagId, PassengerId passengerId, TerminalId terminalId,
                                                   FareId fareId, Location location) {
        val boardingConfirmationEvent = FareBoardingConfirmationEvent.builder()
                .tagId(tagId)
                .fareId(fareId)
                .passengerId(passengerId)
                .terminalId(terminalId)
                .location(location)
                .build();

        eventPublisher.publish(boardingConfirmationEvent);
    }
}
