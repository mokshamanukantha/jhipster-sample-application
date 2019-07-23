package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiSession;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiSession}.
 */
public interface ApiSessionService {

    /**
     * Save a apiSession.
     *
     * @param apiSession the entity to save.
     * @return the persisted entity.
     */
    ApiSession save(ApiSession apiSession);

    /**
     * Get all the apiSessions.
     *
     * @return the list of entities.
     */
    List<ApiSession> findAll();


    /**
     * Get the "id" apiSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiSession> findOne(Long id);

    /**
     * Delete the "id" apiSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
