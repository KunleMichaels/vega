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

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.ddd.Identifier;
import eu.socialedge.vega.backend.ddd.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import static eu.socialedge.vega.backend.infrastructure.persistence.jpa.repository.SpringCruRepository.exec;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface SpringCrudRepository<ID extends Identifier<?>, T extends AggregateRoot<ID>>
        extends SpringCruRepository<ID, T>, CrudRepository<ID, T> {

    @Override
    @Transactional
    default boolean remove(ID id) {
        T entity = exec(() -> findOne(id));

        if (entity == null)
            return false;

        exec(() -> {
            delete(entity);
            flush();
        });

        return true;
    }

    @Override
    @Transactional
    default void remove(Iterable<ID> entityIds) {
        entityIds.forEach(this::remove);
    }

    @Override
    @Transactional
    default void clear() {
        exec(() -> {
            deleteAll();
            flush();
        });
    }
}
