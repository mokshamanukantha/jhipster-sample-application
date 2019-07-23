package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiCredential;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiCredential}.
 */
public interface ApiCredentialService {

    /**
     * Save a apiCredential.
     *
     * @param apiCredential the entity to save.
     * @return the persisted entity.
     */
    ApiCredential save(ApiCredential apiCredential);

    /**
     * Get all the apiCredentials.
     *
     * @return the list of entities.
     */
    List<ApiCredential> findAll();


    /**
     * Get the "id" apiCredential.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiCredential> findOne(Long id);

    /**
     * Delete the "id" apiCredential.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
