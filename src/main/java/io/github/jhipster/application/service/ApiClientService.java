package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiClient;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiClient}.
 */
public interface ApiClientService {

    /**
     * Save a apiClient.
     *
     * @param apiClient the entity to save.
     * @return the persisted entity.
     */
    ApiClient save(ApiClient apiClient);

    /**
     * Get all the apiClients.
     *
     * @return the list of entities.
     */
    List<ApiClient> findAll();


    /**
     * Get the "id" apiClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiClient> findOne(Long id);

    /**
     * Delete the "id" apiClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
