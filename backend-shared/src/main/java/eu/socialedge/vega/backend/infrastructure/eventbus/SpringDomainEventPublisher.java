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
package eu.socialedge.vega.backend.infrastructure.eventbus;

import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.ddd.DomainEventPublisher;
import eu.socialedge.vega.backend.infrastructure.eventbus.kafka.DomainEventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(DomainEventSource.class)
public class SpringDomainEventPublisher implements DomainEventPublisher {

    @Autowired @Qualifier(DomainEventSource.CHANNEL_NAME)
    private final MessageChannel eventBus;

    @Autowired
    public SpringDomainEventPublisher(MessageChannel eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public <T extends DomainEvent> void publish(T event) {
        eventBus.send(MessageBuilder.withPayload(event).build());
    }
}
