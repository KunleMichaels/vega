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
package eu.socialedge.vega.backend.boarding.domain;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.ddd.AggregateRoot;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Tag extends AggregateRoot<TagId> {

    @Embedded
    @Column(nullable = false)
    private final PassengerId passengerId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="tag_id", referencedColumnName="tag_id")
    private final Set<Pass> passes;

    public Tag(TagId id, PassengerId passengerId, Set<Pass> passes) {
        super(id);
        this.passengerId = notNull(passengerId);
        this.passes = new HashSet<>(notNull(passes));
    }

    public Tag(TagId id, PassengerId passengerId) {
        this(id, passengerId, new HashSet<>());
    }

    public boolean addPass(Pass pass) {
        return passes.add(notNull(pass));
    }

    public boolean removePass(Pass pass) {
        return passes.remove(pass);
    }

    public Set<Pass> passes() {
        return Collections.unmodifiableSet(passes);
    }

    public Set<Pass> nonExpiredPasses() {
        return passes.stream().filter(Pass::isExpired).collect(Collectors.toSet());
    }
}
