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

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;
import eu.socialedge.vega.backend.application.api.Endpoints;
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
public class OperatorController {

    @Autowired
    private OperatorResourceMapper mapper;

    @Autowired
    private OperatorRepository operatorRepository;

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.OPERATORS_ROOT)
    public ResponseEntity<Void> create(@RequestBody @NotNull @Valid OperatorResource operatorResource,
                                       UriComponentsBuilder uriBuilder) {
        val operator = mapper.fromResource(operatorResource);
        operatorRepository.add(operator);

        val uri = uriBuilder.path(Endpoints.OPERATORS_ID).buildAndExpand(operator.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = GET, path = Endpoints.OPERATORS_ID)
    public ResponseEntity<OperatorResource> read(@PathVariable @NotNull OperatorId operatorId) {
        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent())
            return ResponseEntity.notFound().build();

        val operatorResource = mapper.toResource(operatorOpt.get());
        return ResponseEntity.ok(operatorResource);
    }

    @RequestMapping(method = GET, path = Endpoints.OPERATORS_ROOT)
    public ResponseEntity<Collection<OperatorResource>> read() {
        val operators = operatorRepository.listActive();

        val operatorResources = mapper.toResources(operators);
        return ResponseEntity.ok(operatorResources);
    }

    @Transactional
    @RequestMapping(method = PATCH, path = Endpoints.OPERATORS_ID)
    public ResponseEntity<Void> update(@PathVariable @NotNull OperatorId operatorId,
                                       @RequestBody @NotNull OperatorResource operatorResource) {

        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent())
            return ResponseEntity.notFound().build();

        val operator = operatorOpt.get();
        mapper.applyResource(operatorResource, operator);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @RequestMapping(method = DELETE, path = Endpoints.OPERATORS_ID)
    public ResponseEntity<Void> deactivate(@PathVariable @NotNull OperatorId operatorId) {
        if (!operatorRepository.contains(operatorId))
            return ResponseEntity.notFound().build();

        operatorRepository.deactivate(operatorId);
        return ResponseEntity.ok().build();
    }
}
