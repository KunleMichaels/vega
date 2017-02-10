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

import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class AntValueRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.getParameterAnnotation(AntValueRequestBody.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        val req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        val reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        val antValAnn = param.getParameterAnnotation(AntValueRequestBody.class);
        val antPattern = antValAnn.value();

        if (isBlank(antValAnn.value()))
            throw new IllegalArgumentException("Pattern (value) must be defined for AntValueRequestBody");

        val antPlaceholder = isBlank(antValAnn.placeholder()) ? param.getParameterName() : antValAnn.placeholder();


        val pathVariables = pathMatcher.extractUriTemplateVariables(antPattern, reqBody);

        if (!pathVariables.containsKey(antPlaceholder))
            throw new IllegalArgumentException("Failed to parse {" + antPlaceholder + "} from " + reqBody);


        return pathVariables.get(antPlaceholder);
    }
}
