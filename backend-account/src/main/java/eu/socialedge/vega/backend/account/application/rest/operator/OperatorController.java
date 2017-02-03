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
package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.val;

@RestController
@Transactional(readOnly = true)
@RequestMapping(path = "/operators")
public class OperatorController {

    @Autowired
    private OperatorEntityMapper mapper;

    @Autowired
    private OperatorRepository operatorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @NotNull OperatorResource operatorResource,
                                       UriComponentsBuilder urlBuilder) {

        val operator = mapper.fromResource(operatorResource);
        operatorRepository.add(operator);

        val url = urlBuilder.path("/user/{id}").buildAndExpand(operator.id()).toUri();
        return ResponseEntity.created(url).build();
    }

    @GetMapping(path = "/{operatorId}")
    public ResponseEntity<OperatorResource> read(@PathVariable @NotNull OperatorId operatorId) {
        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent())
            return ResponseEntity.notFound().build();

        val operator = operatorOpt.get();
        val operatorResource = mapper.toResource(operator);

        return ResponseEntity.ok(operatorResource);
    }

    @GetMapping
    public ResponseEntity<Collection<OperatorResource>> read(@RequestParam(required = false)
                                                                     boolean all) {
        Collection<Operator> operators;
        if (all) {
            operators = operatorRepository.list();
        } else {
            operators = operatorRepository.listActive();
        }

        val operatorResources = mapper.toResources(operators);
        return ResponseEntity.ok(operatorResources);
    }

    @Transactional
    @PutMapping(path = "/{operatorId}")
    public ResponseEntity<OperatorResource> update(@PathVariable @NotNull OperatorId operatorId,
                                                   @NotNull OperatorResource operatorResource) {

        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent())
            return ResponseEntity.notFound().build();

        val operator = operatorOpt.get();

        operator.description(operatorResource.description());
        operator.name(operatorResource.name());

        return ResponseEntity.ok(operatorResource);
    }

    @DeleteMapping(path = "/{operatorId}")
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull OperatorId operatorId) {
        if (!operatorRepository.contains(operatorId))
            return ResponseEntity.notFound().build();

        operatorRepository.deactivate(operatorId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{operatorId}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> activate(@PathVariable @NotNull OperatorId operatorId) {
        if (!operatorRepository.contains(operatorId))
            return ResponseEntity.notFound().build();

        operatorRepository.activate(operatorId);

        return ResponseEntity.ok().build();
    }
}
