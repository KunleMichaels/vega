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
package eu.socialedge.vega.backend.account.application;

import eu.socialedge.vega.backend.account.application.config.JpaInfrastructureConfig;
import eu.socialedge.vega.backend.account.application.config.RepositoryRestConfiguration;
import eu.socialedge.vega.backend.infrastructure.eventbus.kafka.SpringEventBusConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import({JpaInfrastructureConfig.class,
        RepositoryRestConfiguration.class,
        SpringEventBusConfig.class})
public class VegaAccountMicroservice {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(VegaAccountMicroservice.class)
                .run(args);
    }
}
