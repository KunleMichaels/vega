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

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;

/**
 * Represents a value extracted from text/url-list against
 * Ant Path pattern's placeholder.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlListValue {

    @AliasFor("pattern")
    String value() default "";

    /**
     * Ant Style URI that will be used as a mask for the
     * content of a request body
     */
    @AliasFor("value")
    String pattern() default "";

    /**
     * A placeholder's name in pattern that will be used to
     * extract value from a request body.
     *
     * If empty, Java 8 parameter name is used as placeholder
     */
    String placeholder() default "";

    /**
     * If true, pattern will be applied only for URL's path part
     *
     * @see URL#getPath()
     */
    boolean matchPathOnly() default true;
}
