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
package eu.socialedge.vega.backend.account.application.config;

import eu.socialedge.ddd.event.DomainEventHandler;
import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.ddd.event.DomainEventRegistrar;
import eu.socialedge.vega.backend.account.domain.PassengerRepository;
import eu.socialedge.vega.backend.account.domain.event.PassengerChargeRequestEventHandler;
import eu.socialedge.vega.backend.account.domain.event.PassengerFareChargeRequestEvent;
import eu.socialedge.vega.backend.infrastructure.eventbus.cloudstream.config.SpringEventBusConfig;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration @Import(SpringEventBusConfig.class)
public class EventHandlingConfig {

    @Autowired
    private DomainEventRegistrar eventRegistrar;

    @Autowired
    private DomainEventPublisher eventPublisher;

    @Bean
    public DomainEventHandler<?> passengerChargeRequestEventHandler(PassengerRepository passengerRepository) {
        val eventHandler = new PassengerChargeRequestEventHandler(passengerRepository, eventPublisher);

        eventRegistrar.registerEventHandler(eventHandler, PassengerFareChargeRequestEvent.class);
        return eventHandler;
    }
}
