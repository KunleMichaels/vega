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
package eu.socialedge.vega.backend.history.boarding.domain;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.boarding.history.domain.BoardingId;
import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import eu.socialedge.vega.backend.transit.domain.location.Location;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Boarding extends AggregateRoot<BoardingId> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final BoardingType boardingType;

    @Embedded
    @Column(nullable = false)
    private final PassengerId passengerId;

    @Embedded
    @Column(nullable = false)
    private final TerminalId terminalId;

    @Embedded
    @Column(nullable = false)
    private final Location location;

    @Column(nullable = false)
    private final LocalDateTime timestamp;

    public Boarding(BoardingId id, BoardingType boardingType, PassengerId passengerId,
                    TerminalId terminalId, Location location,
                    LocalDateTime timestamp) {
        super(id);

        this.boardingType = notNull(boardingType);
        this.passengerId = notNull(passengerId);
        this.terminalId = notNull(terminalId);
        this.location = notNull(location);
        this.timestamp = notNull(timestamp);
    }

    public Boarding(BoardingId id, BoardingType boardingType, PassengerId passengerId,
                    TerminalId terminalId, Location location) {
        this(id, boardingType, passengerId, terminalId, location, LocalDateTime.now());
    }
}
