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
import eu.socialedge.vega.backend.ddd.repository.CruRepository;
import eu.socialedge.vega.backend.ddd.repository.RepositoryException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface SpringCruRepository<ID extends Identifier<?>, T extends AggregateRoot<ID>>
        extends CruRepository<ID, T>, JpaRepository<T, ID> {

    @Override
    @Transactional
    default void add(T entity) {
        exec(() -> saveAndFlush(entity));
    }

    @Override
    @Transactional
    default void add(Collection<T> entities) {
        entities.forEach(this::add);
    }

    @Override
    @Transactional
    default void update(T entity) {
        exec(() -> saveAndFlush(entity));
    }

    @Override
    @Transactional
    default void update(Collection<T> entities) {
        entities.forEach(this::update);
    }

    @Override
    default Optional<T> get(ID id) {
        return Optional.ofNullable(exec(() -> findOne(id)));
    }

    @Override
    default Collection<T> list() {
        return exec(() -> findAll());
    }

    @Override
    default boolean contains(ID id) {
        return get(id).isPresent();
    }

    @Override
    default long size() {
        return exec(() -> count());
    }

    @Override
    default boolean isEmpty() {
        return size() == 0L;
    }

    @Override
    @Transactional
    default void clear() {
        exec(() -> {
            deleteAll();
            flush();
        });
    }

    static <E> E exec(Supplier<E> func) {
        try {
            return func.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    static void exec(Runnable func) {
        try {
            func.run();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
