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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
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
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Accessors(fluent = true)
public class AntValueRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String ANT_IGNORE_PREFIX = "**";

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Setter @Getter
    private ConversionService conversionService;

    public AntValueRequestBodyMethodArgumentResolver() {
        this(new DefaultConversionService());
    }

    public AntValueRequestBodyMethodArgumentResolver(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(AntValueRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        val reqBody = extractRequestBody(nativeWebRequest);

        val paramClz = param.getParameterType();
        val paramMatchAnn = param.getParameterAnnotation(AntValueRequestBody.class);

        if (isBlank(paramMatchAnn.value()))
            throw new IllegalArgumentException("Pattern (value) must be defined for AntValueRequestBody");

        val antPlaceholder = isBlank(paramMatchAnn.placeholder()) ? param.getParameterName() : paramMatchAnn.placeholder();
        val antPattern = paramMatchAnn.matchTrail() ? ANT_IGNORE_PREFIX + paramMatchAnn.value(): paramMatchAnn.value();

        val matchedValue = this.extractPlaceholderValue(reqBody, antPattern, antPlaceholder);

        return convert(matchedValue, paramClz);
    }

    private String extractPlaceholderValue(String body, String antPattern, String placeholder) {
        val pathVariables = pathMatcher.extractUriTemplateVariables(antPattern, body);

        if (!pathVariables.containsKey(placeholder))
            throw new IllegalArgumentException("Failed to parse {" + placeholder + "} from " + body);

        return pathVariables.get(placeholder);
    }

    private <T> T convert(Object value, Class<T> targetClz) {
        val valClz = value.getClass();

        if (valClz.equals(targetClz))
            return targetClz.cast(value);

        if (!conversionService.canConvert(valClz, targetClz))
            throw new IllegalStateException("No required converter found (" + valClz.getName() +
                " -> " + targetClz.getName() + ")");

        return conversionService.convert(value, targetClz);
    }

    private static String extractRequestBody(NativeWebRequest nativeWebRequest) throws IOException {
        val req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
