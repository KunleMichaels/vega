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

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.shared.TerminalId;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.apache.commons.lang3.Validate.notNull;

@ToString
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
public class Terminal extends AggregateRoot<TerminalId> {

    private Build build;

    private Installation installation;

    public Terminal(TerminalId id, Build build, Installation installation) {
        super(id);

        this.build = notNull(build);
        this.installation = notNull(installation);
    }
}
