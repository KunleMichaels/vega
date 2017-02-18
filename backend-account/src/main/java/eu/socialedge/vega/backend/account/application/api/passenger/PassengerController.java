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
import eu.socialedge.vega.backend.application.api.support.UrlListValue;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import lombok.val;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Transactional(readOnly = true)
public class PassengerController {

    @Autowired
    private PassengerEntityMapper mapper;

    @Autowired
    private PassengerRepository passengerRepository;

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.PASSENGERS_ROOT)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid PassengerResource passengerResource) {
        val passenger = mapper.fromResource(passengerResource);
        passengerRepository.add(passenger);

        val uri = linkTo(getClass()).slash(passenger.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<PassengerResource> read(@PathVariable @NotNull PassengerId passengerId) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        val passengerResource = mapper.toResource(passengerOpt.get());
        return ResponseEntity.ok(passengerResource);
    }

    @RequestMapping(method = GET, path = Endpoints.PASSENGERS_ROOT)
    public ResponseEntity<Resources<PassengerResource>> read() {
        val passengers = passengerRepository.listActive();

        val passengerResources = mapper.toResources(passengers);
        return ResponseEntity.ok(passengerResources);
    }

    @Transactional
    @RequestMapping(method = DELETE, path = Endpoints.PASSENGERS_ID)
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull PassengerId passengerId) {
        if (!passengerRepository.contains(passengerId)) {
            return ResponseEntity.notFound().build();
        }

        passengerRepository.deactivate(passengerId);
        return ResponseEntity.ok().build();
    }


    @Transactional
    @RequestMapping(method = POST, path = Endpoints.PASSENGERS_TAGS, consumes = "text/uri-list")
    public ResponseEntity<Void> updateTags(@PathVariable @NotNull PassengerId passengerId,
                                           @UrlListValue(Endpoints.TAGS_ID) @NotEmpty TagId[] tagIds) {
        val passengerOpt = passengerRepository.get(passengerId);
        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        val passenger = passengerOpt.get();

        passenger.tagIds().forEach(passenger::removeTagId);
        Arrays.stream(tagIds).forEach(passenger::addTagId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = GET, path = Endpoints.PASSENGERS_TAGS, produces = "text/uri-list")
    public ResponseEntity<List<Link>> tags(@PathVariable @NotNull PassengerId passengerId, UriComponentsBuilder uriBuilder) {
        val passengerOpt = passengerRepository.get(passengerId);

        if (!passengerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        val passenger = passengerOpt.get();

        val tagLinks = passenger.tagIds().stream()
            .map(tagId -> uriBuilder.cloneBuilder().path(Endpoints.TAGS_ID).buildAndExpand(tagId))
            .map(ub -> new Link(ub.toString()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(tagLinks);
    }
}
