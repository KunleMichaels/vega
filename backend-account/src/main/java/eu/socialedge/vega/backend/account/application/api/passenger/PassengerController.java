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
package eu.socialedge.vega.backend.account.application.api.passenger;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.account.domain.PassengerRepository;
import eu.socialedge.vega.backend.application.api.Endpoints;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Transactional(readOnly = true)
public class PassengerController {

    @Autowired
    private PassengerResourceMapper mapper;

    @Autowired
    private PassengerRepository passengerRepository;

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.PASSENGERS_ROOT)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid PassengerResource passengerResource,
                                       UriComponentsBuilder uriBuilder) {
        val passenger = mapper.fromResource(passengerResource);
        passengerRepository.add(passenger);

        val uri = uriBuilder.path(Endpoints.PASSENGERS_ID).buildAndExpand(passenger.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<PassengerResource> read(@PathVariable @NotNull PassengerId passengerId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent())
            return ResponseEntity.notFound().build();

        val passenger = passengerOpt.get();
        val passengerResource = mapper.toResource(passenger);

        return ResponseEntity.ok(passengerResource);
    }

    @RequestMapping(method = GET, path = Endpoints.PASSENGERS_ROOT)
    public ResponseEntity<Collection<PassengerResource>> read() {
        val passengers = passengerRepository.listActive();

        val passengerResources = mapper.toResources(passengers);
        return ResponseEntity.ok(passengerResources);
    }

    @Transactional
    @RequestMapping(method = PATCH, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<Void> update(@PathVariable @NotNull PassengerId passengerId,
                                       @RequestBody @NotNull PassengerResource passengerResource) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent())
            return ResponseEntity.notFound().build();

        val passenger = passengerOpt.get();
        mapper.applyResource(passengerResource, passenger);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<Void> activate(@PathVariable @NotNull PassengerId passengerId) {
        if (!passengerRepository.contains(passengerId))
            return ResponseEntity.notFound().build();

        passengerRepository.activate(passengerId);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull PassengerId passengerId) {
        if (!passengerRepository.contains(passengerId))
            return ResponseEntity.notFound().build();

        passengerRepository.deactivate(passengerId);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.PASSENGERS_TAGS)
    public ResponseEntity<Void> attachTag(@PathVariable @NotNull PassengerId passengerId,
                                          @RequestBody @NotNull TagId tagId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent())
            return ResponseEntity.notFound().build();

        val passenger = passengerOpt.get();
        passenger.addTagId(tagId);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = Endpoints.PASSENGERS_TAGS_ID)
    public ResponseEntity<Void> detachTag(@PathVariable @NotNull PassengerId passengerId,
                                            @PathVariable @NotNull TagId tagId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent())
            return ResponseEntity.notFound().build();

        val passenger = passengerOpt.get();
        passenger.removeTagId(tagId);

        return ResponseEntity.ok().build();
    }
}
