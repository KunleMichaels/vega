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
package eu.socialedge.vega.backend.transit.domain;

import eu.socialedge.vega.backend.ddd.AggregateRoot;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Tag extends AggregateRoot<TagId> {

    @Column(nullable = false)
    private final String body;

    @OneToMany
    @JoinColumn(name="tag_id", referencedColumnName="id")
    private final Set<Pass> passes;

    public Tag(TagId id, String body, Set<Pass> passes) {
        super(id);

        this.body = notBlank(body);
        this.passes = notNull(passes);
    }

    public Tag(TagId id, String body) {
        this(id, body, new HashSet<>());
    }
}
