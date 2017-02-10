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
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class AntValueRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Setter @Getter
    private ConversionService conversionService = new DefaultConversionService();

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(AntValueRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        val req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        val reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        val antValClass = param.getParameterType();
        val antValAnn = param.getParameterAnnotation(AntValueRequestBody.class);
        val antPattern = antValAnn.value();

        if (isBlank(antValAnn.value()))
            throw new IllegalArgumentException("Pattern (value) must be defined for AntValueRequestBody");

        val antPlaceholder = isBlank(antValAnn.placeholder()) ? param.getParameterName() : antValAnn.placeholder();

        val pathVariables = pathMatcher.extractUriTemplateVariables(antPattern, reqBody);
        if (!pathVariables.containsKey(antPlaceholder))
            throw new IllegalArgumentException("Failed to parse {" + antPlaceholder + "} from " + reqBody);

        val placeholderValue = pathVariables.get(antPlaceholder);
        val placeholderValueClass = placeholderValue.getClass();

        if (placeholderValueClass.equals(antValClass))
            return placeholderValue;

        if (!conversionService.canConvert(placeholderValueClass, antValClass))
            throw new IllegalStateException("Failed to convert AntValueRequestBody of type "
                + antValClass.getName() + " to " + placeholderValueClass.getName());

        return conversionService.convert(placeholderValue, antValClass);
    }
}
