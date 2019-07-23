package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiServiceMethod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiServiceMethod}.
 */
public interface ApiServiceMethodService {

    /**
     * Save a apiServiceMethod.
     *
     * @param apiServiceMethod the entity to save.
     * @return the persisted entity.
     */
    ApiServiceMethod save(ApiServiceMethod apiServiceMethod);

    /**
     * Get all the apiServiceMethods.
     *
     * @return the list of entities.
     */
    List<ApiServiceMethod> findAll();

    /**
     * Get all the apiServiceMethods with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ApiServiceMethod> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" apiServiceMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiServiceMethod> findOne(Long id);

    /**
     * Delete the "id" apiServiceMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
