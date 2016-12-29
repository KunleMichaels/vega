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
package eu.socialedge.vega.backend.infrastructure.persistence.jpa.repository;

import eu.socialedge.vega.backend.ddd.DeactivatableAggregateRoot;
import eu.socialedge.vega.backend.ddd.DeactivatableAggregateRoot_;
import eu.socialedge.vega.backend.ddd.Identifier;
import eu.socialedge.vega.backend.ddd.repository.CruaRepository;
import eu.socialedge.vega.backend.ddd.repository.RepositoryException;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static eu.socialedge.vega.backend.infrastructure.persistence.jpa.repository.SpringCruRepository.exec;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface SpringCruaRepository<ID extends Identifier<?>, T extends DeactivatableAggregateRoot<ID>>
        extends SpringCruRepository<ID, T>, JpaSpecificationExecutor<T>, CruaRepository<ID, T> {

    @Override
    default boolean isActive(ID id) {
        return getActive(id).isPresent();
    }

    @Override
    @Transactional
    default void activate(ID id) {
        T entity = get(id).orElseThrow(()
                -> new RepositoryException("Cannot activate entity: Entity not found"));

        entity.activate();
        update(entity);
    }

    @Override
    @Transactional
    default void activate(Iterable<ID> entityIds) {
        entityIds.forEach(this::activate);
    }

    @Override
    default void deactivate(ID id) {
        T entity = get(id).orElseThrow(()
                -> new RepositoryException("Cannot deactivate entity: Entity not found"));

        entity.deactivate();
        update(entity);
    }

    @Override
    default void deactivate(Iterable<ID> entityIds) {
        entityIds.forEach(this::deactivate);
    }

    @Override
    default Optional<T> getActive(ID id) {
        Optional<T> entityOpt = get(id);

        if (!entityOpt.isPresent() || !entityOpt.get().isActive())
            return Optional.empty();

        return entityOpt;
    }


    @Override
    default Collection<T> listActive() {
        return exec(() -> findAll((root, query, cb)
                -> cb.equal(root.<Boolean>get(DeactivatableAggregateRoot_.isActive), true)));
    }
}
