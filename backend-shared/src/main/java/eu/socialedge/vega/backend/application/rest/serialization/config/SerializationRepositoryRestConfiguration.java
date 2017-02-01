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
package eu.socialedge.vega.backend.application.rest.serialization.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.socialedge.vega.backend.application.rest.serialization.MonetaryAmountToStringConverter;
import eu.socialedge.vega.backend.application.rest.serialization.Path2dToStringConverter;
import eu.socialedge.vega.backend.application.rest.serialization.StringToMonetaryAmountConverter;
import eu.socialedge.vega.backend.application.rest.serialization.StringToPath2dConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToBoardingIdConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToFareIdConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToOperatorIdConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToPassengerIdConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToTagIdConverter;
import eu.socialedge.vega.backend.application.rest.serialization.id.StringToTerminalIdConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SerializationRepositoryRestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(new StringToBoardingIdConverter());
        conversionService.addConverter(new StringToFareIdConverter());
        conversionService.addConverter(new StringToOperatorIdConverter());
        conversionService.addConverter(new StringToPassengerIdConverter());
        conversionService.addConverter(new StringToTagIdConverter());
        conversionService.addConverter(new StringToTerminalIdConverter());

        conversionService.addConverter(new MonetaryAmountToStringConverter());
        conversionService.addConverter(new StringToMonetaryAmountConverter());

        conversionService.addConverter(new Path2dToStringConverter());
        conversionService.addConverter(new StringToPath2dConverter());
    }
}
