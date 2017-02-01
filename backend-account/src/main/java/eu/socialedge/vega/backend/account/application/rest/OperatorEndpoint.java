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
package eu.socialedge.vega.backend.account.application.rest;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/operators")
public class OperatorEndpoint {

    private final static OperatorResourceAssembler assembler = new OperatorResourceAssembler();

    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping
    public ResponseEntity<List<OperatorResource>> operators() {
        val operators = operatorRepository.list();
        val operatorResources = assembler.toResources(operators);

        return ResponseEntity.ok(operatorResources);
    }

    @GetMapping(path = "/{operatorId}")
    public ResponseEntity<OperatorResource> operator(@PathVariable OperatorId operatorId) {
        val operatorOpt = operatorRepository.get(operatorId);

        if (!operatorOpt.isPresent())
            return ResponseEntity.notFound().build();

        val operator = operatorOpt.get();
        val operatorResource = assembler.toResource(operator);

        return ResponseEntity.ok(operatorResource);
    }

}
