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
package eu.socialedge.vega.backend.application.api.resource;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public interface ResourceDisassembler<E, R> {

    E fromResource(R resource);

    default Collection<E> fromResources(Collection<R> resources) {
        return notNull(resources).stream().map(this::fromResource).collect(Collectors.toList());
    }

}
