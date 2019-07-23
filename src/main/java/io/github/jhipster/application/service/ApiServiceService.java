package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiService;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiService}.
 */
public interface ApiServiceService {

    /**
     * Save a apiService.
     *
     * @param apiService the entity to save.
     * @return the persisted entity.
     */
    ApiService save(ApiService apiService);

    /**
     * Get all the apiServices.
     *
     * @return the list of entities.
     */
    List<ApiService> findAll();


    /**
     * Get the "id" apiService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiService> findOne(Long id);

    /**
     * Delete the "id" apiService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
