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
package eu.socialedge.vega.backend.account.application.api.operator;

import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.application.api.resource.ResourceApplier;
import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import lombok.val;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OperatorResourceMapper
        implements ResourceMapper<Operator,OperatorResource>, ResourceApplier<Operator, OperatorResource> {

    @Override
    public OperatorResource toResource(Operator entity) {
        if (isNull(entity)) return null;

        val resId = entity.id();
        val resName = entity.name();
        val resDescription = entity.description();

        return new OperatorResource(resId, resName, resDescription);
    }

    @Override
    public void applyResource(OperatorResource resource, Operator entity) {
        val entName = resource.name();
        val entDescription = resource.description();

        if (!isNull(entName)) entity.name(entName);
        if (!isNull(entDescription)) entity.description(entDescription);
    }

    @Override
    public Operator fromResource(OperatorResource resource) {
        if (isNull(resource)) return null;

        val entName = resource.name();
        val entDescription = resource.description();

        return new Operator(entName, entDescription);
    }
}
