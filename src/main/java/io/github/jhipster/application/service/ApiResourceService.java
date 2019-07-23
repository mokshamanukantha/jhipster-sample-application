package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiResource;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiResource}.
 */
public interface ApiResourceService {

    /**
     * Save a apiResource.
     *
     * @param apiResource the entity to save.
     * @return the persisted entity.
     */
    ApiResource save(ApiResource apiResource);

    /**
     * Get all the apiResources.
     *
     * @return the list of entities.
     */
    List<ApiResource> findAll();


    /**
     * Get the "id" apiResource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiResource> findOne(Long id);

    /**
     * Delete the "id" apiResource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
