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
package eu.socialedge.vega.backend.application.rest.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Accessors(fluent = true)
public class AntValueRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Setter @Getter
    private ConversionService conversionService;

    @Setter @Getter
    private ObjectMapper objectMapper;

    public AntValueRequestBodyMethodArgumentResolver() {
        this(new DefaultConversionService(), new ObjectMapper());
    }

    public AntValueRequestBodyMethodArgumentResolver(ConversionService conversionService) {
        this(conversionService, new ObjectMapper());
    }

    public AntValueRequestBodyMethodArgumentResolver(ConversionService conversionService, ObjectMapper objectMapper) {
        this.conversionService = conversionService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(AntValueRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        val parameterType = param.getParameterType();
        val antValueRequestAnnotation = param.getParameterAnnotation(AntValueRequestBody.class);
        val requestBody = extractRequestBody(nativeWebRequest);
        val placeholderValues = extractPlaceholderValues(requestBody, antValueRequestAnnotation);

        return convert(placeholderValues, parameterType);
    }

    private String[] extractPlaceholderValues(String requestBody, AntValueRequestBody annotation) {
        try {
            val requestBodyElements = objectMapper.readValue(requestBody, String[].class);
            return Arrays.stream(requestBodyElements)
                .map(reqBodyElement -> extractPlaceholderValue(reqBodyElement, annotation.pattern(),
                    annotation.placeholder(), annotation.isPartialMatch()))
                .toArray(String[]::new);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse request payload. Make sure it is json array");
        }
    }

    private String extractPlaceholderValue(String body, String pattern, String placeholder, boolean partial) {
        pattern = partial ? "**/" + pattern : pattern;
        
        val pathVariables = pathMatcher.extractUriTemplateVariables(pattern, body);
        if (!pathVariables.containsKey(placeholder)) {
            throw new IllegalArgumentException("Failed to parse {" + placeholder + "} from " + body);
        }
        return pathVariables.get(placeholder);
    }

    private <T> T convert(Object value, Class<T> targetClz) {
        val valClz = value.getClass();

        if (valClz.equals(targetClz)) {
            return (T) value;
        }

        if (!conversionService.canConvert(valClz, targetClz)) {
            throw new IllegalStateException("No required converter found (" + valClz.getName() +
                " -> " + targetClz.getName() + ")");
        }

        return conversionService.convert(value, targetClz);
    }

    private static String extractRequestBody(NativeWebRequest nativeWebRequest) throws IOException {
        val req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
