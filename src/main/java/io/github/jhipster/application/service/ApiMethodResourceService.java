package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiMethodResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiMethodResource}.
 */
public interface ApiMethodResourceService {

    /**
     * Save a apiMethodResource.
     *
     * @param apiMethodResource the entity to save.
     * @return the persisted entity.
     */
    ApiMethodResource save(ApiMethodResource apiMethodResource);

    /**
     * Get all the apiMethodResources.
     *
     * @return the list of entities.
     */
    List<ApiMethodResource> findAll();

    /**
     * Get all the apiMethodResources with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ApiMethodResource> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" apiMethodResource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiMethodResource> findOne(Long id);

    /**
     * Delete the "id" apiMethodResource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
