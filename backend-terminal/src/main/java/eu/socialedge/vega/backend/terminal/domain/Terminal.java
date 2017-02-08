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
package eu.socialedge.vega.backend.terminal.domain;

import eu.socialedge.ddd.domain.AggregateRoot;
import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import static org.apache.commons.lang3.Validate.notNull;

@ToString
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@Entity @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Terminal extends AggregateRoot<TerminalId> {

    @Embedded
    private Build build;

    @Embedded
    private Installation installation;

    public Terminal(TerminalId id, Build build, Installation installation) {
        super(id);

        this.build = notNull(build);
        this.installation = notNull(installation);
    }

    public Terminal(Build build, Installation installation) {
        this(new TerminalId(), build, installation);
    }
}
