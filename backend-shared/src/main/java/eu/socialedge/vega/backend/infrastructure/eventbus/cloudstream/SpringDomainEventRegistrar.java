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
package eu.socialedge.vega.backend.infrastructure.eventbus.cloudstream;

import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.ddd.DomainEventHandler;
import eu.socialedge.vega.backend.ddd.DomainEventRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableBinding(DomainEventSink.class)
public class SpringDomainEventRegistrar implements DomainEventRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(SpringDomainEventRegistrar.class);

    private final Map<Class<? extends DomainEvent>, List<DomainEventHandler<?>>> handlers = new HashMap<>();

    @Override
    public <T extends DomainEvent> void registerEventHandler(DomainEventHandler<T> eventHandler, Class<T> eventType) {
        logger.debug("Registering event handler '{}' for event type '{}'", eventHandler, eventType);

        handlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(eventHandler);
    }

    @Override
    public void deregisterEventHandler(Class<? extends DomainEventHandler> eventHandlerClass) {
        logger.debug("Deregistering event handler '{}'", eventHandlerClass);

        handlers.values().forEach(eventHandlers -> {
            eventHandlers.removeIf(handler -> handler.getClass().equals(eventHandlerClass));
        });
    }

    @SuppressWarnings("unchecked")
    @StreamListener(DomainEventSink.CHANNEL_NAME)
    <T extends DomainEvent> void handleEvent(T event) {
        logger.debug("Received event '{}' for handling", event);

        handlers.keySet().stream()
                .filter(eventType -> eventType.isAssignableFrom(event.getClass()))
                .flatMap(handledEvent -> handlers.get(handledEvent).stream())
                .forEach(eventHandler -> {
                    try {
                        logger.debug("Delegating event '{}' to handler '{}'", event, eventHandler);
                        ((DomainEventHandler<T>) eventHandler).handleEvent(event);
                    } catch (Exception exception) {
                        //Ignored to let other handlers handle this event
                        logger.error("Handler '{}' threw exception for event '{}'", eventHandler, event, exception);
                    }
                });
    }
}
