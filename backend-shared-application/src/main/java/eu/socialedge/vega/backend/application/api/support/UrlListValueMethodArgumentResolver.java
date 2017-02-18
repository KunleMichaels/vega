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
package eu.socialedge.vega.backend.application.api.support;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Accessors(fluent = true)
public class UrlListValueMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static AntPathMatcher pathMatcher = new AntPathMatcher();

    @Setter @Getter
    private ConversionService conversionService;

    public UrlListValueMethodArgumentResolver() {
        this(new DefaultConversionService());
    }

    public UrlListValueMethodArgumentResolver(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        val paramIsIterable = Iterable.class.isAssignableFrom(param.getParameterType());

        return param.hasParameterAnnotation(UrlListValue.class) && !paramIsIterable;
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mvc, NativeWebRequest req,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        val paramClz = param.getParameterType();
        val paramIsArray = paramClz.isArray();
        val paramName = param.getParameterName();

        val urlListValAnn = param.getParameterAnnotation(UrlListValue.class);
        val antPlaceholder = extractAntPlaceholder(urlListValAnn, paramIsArray, paramName);
        val antPattern = urlListValAnn.pattern();
        val matchPathOnly = urlListValAnn.matchPathOnly();

        val reqBodyLinesStream = extractRequestBodyLines(req);
        val pathOperandsStream = matchPathOnly ? extractUrlPaths(reqBodyLinesStream) : reqBodyLinesStream;

        val matchedVals = pathOperandsStream.map(path -> extractPlaceholderValue(path, antPattern, antPlaceholder));

        Object resolvedParamValue;

        if (paramIsArray) {
            resolvedParamValue = matchedVals.toArray();
        } else {
            resolvedParamValue = matchedVals.findFirst().orElseGet(null);
        }

        return convert(resolvedParamValue, paramClz);
    }

    private static String extractAntPlaceholder(UrlListValue ann, boolean singularize, String defaultPlaceholder) {
        val antPlaceholder = defaultIfBlank(ann.placeholder(), defaultPlaceholder);

        if (singularize) {
            return depluralize(antPlaceholder);
        } else {
            return antPlaceholder;
        }
    }

    private <T> T convert(Object source, Class<T> targetClz) {
        if (source == null)
            return null;

        val valClz = source.getClass();

        if (valClz.equals(targetClz))
            return targetClz.cast(source);

        if (!conversionService.canConvert(valClz, targetClz))
            throw new IllegalStateException("No required converter found (" + valClz.getName() +
                " -> " + targetClz.getName() + ")");

        return conversionService.convert(source, targetClz);
    }

    private String extractPlaceholderValue(String path, String antPattern, String placeholder) {
        val pathVariables = pathMatcher.extractUriTemplateVariables(antPattern, path);

        if (!pathVariables.containsKey(placeholder))
            throw new IllegalArgumentException("Failed to parse {" + placeholder + "} from " + path);

        return pathVariables.get(placeholder);
    }

    private static Stream<String> extractRequestBodyLines(NativeWebRequest nativeWebRequest)
            throws IOException {
        val req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return req.getReader().lines();
    }

    private static String extractUrlPath(String uri) {
        try {
            return new URI(uri).getPath();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Provided uri is not valid: '%s'", uri));
        }
    }

    private static Stream<String> extractUrlPaths(Stream<String> urls) {
        return urls.map(UrlListValueMethodArgumentResolver::extractUrlPath);
    }

    /**
     * Convert singular name to plural form.
     * @see <a href="https://goo.gl/05UK6z">GC: org.jibx.util.NameUtilities#depluralize</a>
     */
    private static String depluralize(String name) {
        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + 'y';
        } else if (name.endsWith("sses")) {
            return name.substring(0, name.length() - 2);
        } else if (name.endsWith("s") && !name.endsWith("ss")) {
            return name.substring(0, name.length() - 1);
        } else if (name.endsWith("List")) {
            return name.substring(0, name.length() - 4);
        } else {
            return name;
        }
    }
}
