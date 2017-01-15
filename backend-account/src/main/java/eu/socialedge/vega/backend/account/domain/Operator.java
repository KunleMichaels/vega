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
package eu.socialedge.vega.backend.account.domain;

import eu.socialedge.vega.backend.ddd.DeactivatableAggregateRoot;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import static org.apache.commons.lang3.Validate.notBlank;

@ToString
@Getter @Setter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Operator extends DeactivatableAggregateRoot<OperatorId> {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    public Operator(OperatorId id, String name, String description) {
        super(id);

        this.name = notBlank(name);
        this.description = description;
    }

    public void name(String name) {
        this.name = notBlank(name);
    }
}

