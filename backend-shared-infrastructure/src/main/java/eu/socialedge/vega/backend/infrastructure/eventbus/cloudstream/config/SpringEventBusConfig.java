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
package eu.socialedge.vega.backend.infrastructure.eventbus.cloudstream.config;

import eu.socialedge.ddd.event.DomainEventPublisher;
import eu.socialedge.ddd.event.DomainEventRegistrar;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This configuration enables Spring Cloud Stream based implementations
 * of {@link DomainEventPublisher} and {@link DomainEventRegistrar}.
 */
@Configuration
@ComponentScan("eu.socialedge.ddd.infrastructure.eventbus")
@Import(EmbeddedKafkaConfig.class)
public class SpringEventBusConfig {
}
