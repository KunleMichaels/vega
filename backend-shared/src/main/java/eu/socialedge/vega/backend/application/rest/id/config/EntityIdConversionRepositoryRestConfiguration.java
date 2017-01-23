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
package eu.socialedge.vega.backend.application.rest.id.config;

import eu.socialedge.vega.backend.application.rest.id.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
@ComponentScan("eu.socialedge.vega.backend.application.rest.id")
public class EntityIdConversionRepositoryRestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(new BoardingIdConverter());
        conversionService.addConverter(new FareIdConverter());
        conversionService.addConverter(new OperatorIdConverter());
        conversionService.addConverter(new PassengerIdConverter());
        conversionService.addConverter(new TagIdConverter());
        conversionService.addConverter(new TerminalIdConverter());
    }
}
