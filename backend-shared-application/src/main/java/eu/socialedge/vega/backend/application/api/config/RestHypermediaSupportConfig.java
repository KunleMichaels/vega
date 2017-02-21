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
package eu.socialedge.vega.backend.application.api.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.socialedge.vega.backend.application.api.convert.UriListHttpMessageConverter;
import eu.socialedge.vega.backend.application.api.support.UrlListValueMethodArgumentResolver;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RestHypermediaSupportConfig extends WebMvcConfigurationSupport {

    private ObjectMapper objectMapper;

    @Autowired
    public RestHypermediaSupportConfig(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.objectMapper = objectMapper;
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        val conversionService = mvcConversionService();

        argumentResolvers.add(new UrlListValueMethodArgumentResolver(conversionService));
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(new UriListHttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiation) {
        contentNegotiation
            .favorPathExtension(false)
            .favorParameter(false)
            .useJaf(false)
            .defaultContentType(MediaTypes.HAL_JSON)
            .mediaType("json", MediaTypes.HAL_JSON)
            .mediaType("uri-list", MediaType.parseMediaType("text/uri-list"))
            .mediaType("json", MediaType.APPLICATION_JSON_UTF8)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }
}
