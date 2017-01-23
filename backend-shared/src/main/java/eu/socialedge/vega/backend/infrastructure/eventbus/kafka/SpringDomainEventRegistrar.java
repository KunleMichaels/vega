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
package eu.socialedge.vega.backend.infrastructure.eventbus.kafka;

import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.ddd.DomainEventException;
import eu.socialedge.vega.backend.ddd.DomainEventRegistrar;
import eu.socialedge.vega.backend.ddd.DomainEventHandler;
import lombok.val;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@EnableBinding(DomainEventSink.class)
public class SpringDomainEventRegistrar implements DomainEventRegistrar {

    private final Map<Class<?>, DomainEventHandler<?>> subscribers = new HashMap<>();

    @Override
    public <T extends DomainEvent> void registerEventHandler(DomainEventHandler<T> eventSubscriber, Class<T> eventType) {
        subscribers.put(eventType, eventSubscriber);
    }

    @Override
    public void deregisterEventHandler(Class<?> eventType) {
        subscribers.remove(eventType);
    }

    @SuppressWarnings("unchecked")
    @StreamListener(DomainEventSink.CHANNEL_NAME)
    <T extends DomainEvent> void handleEvent(T event) {
        val domainEventSubscriber = subscribers.get(event.getClass());

        try {
            if (domainEventSubscriber != null)
                ((DomainEventHandler<T>) domainEventSubscriber).handleEvent(event);
        } catch (Exception e) {
            throw new DomainEventException(e);
        }
    }
}
