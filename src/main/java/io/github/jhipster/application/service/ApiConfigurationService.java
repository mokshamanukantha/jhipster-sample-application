package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiConfiguration;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiConfiguration}.
 */
public interface ApiConfigurationService {

    /**
     * Save a apiConfiguration.
     *
     * @param apiConfiguration the entity to save.
     * @return the persisted entity.
     */
    ApiConfiguration save(ApiConfiguration apiConfiguration);

    /**
     * Get all the apiConfigurations.
     *
     * @return the list of entities.
     */
    List<ApiConfiguration> findAll();


    /**
     * Get the "id" apiConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiConfiguration> findOne(Long id);

    /**
     * Delete the "id" apiConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
