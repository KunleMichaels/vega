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
package eu.socialedge.vega.backend.application.api.serialization;

import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;

import static java.util.Objects.isNull;

public abstract class ModelMapperSupport {

    protected ModelMapper modelMapper;

    public ModelMapperSupport() {
        this(null);
    }

    public ModelMapperSupport(ModelMapper modelMapper) {
        this.modelMapper = isNull(modelMapper) ? createDefaultModelMapper() : modelMapper;
    }

    private ModelMapper createDefaultModelMapper() {
        val modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true)
            .setSourceNamingConvention(NamingConventions.NONE)
            .setDestinationNamingConvention(NamingConventions.NONE)
            .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
            .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);

        return modelMapper;
    }

}
