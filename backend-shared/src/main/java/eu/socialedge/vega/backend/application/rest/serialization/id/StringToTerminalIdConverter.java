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
package eu.socialedge.vega.backend.application.rest.serialization.id;

import eu.socialedge.vega.backend.terminal.domain.TerminalId;

import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;

public class StringToTerminalIdConverter implements Converter<Serializable, TerminalId> {

    @Override
    public TerminalId convert(Serializable source) {
        return new TerminalId(Long.valueOf(source.toString()));
    }
}
