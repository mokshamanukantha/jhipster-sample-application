package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiLogin;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiLogin}.
 */
public interface ApiLoginService {

    /**
     * Save a apiLogin.
     *
     * @param apiLogin the entity to save.
     * @return the persisted entity.
     */
    ApiLogin save(ApiLogin apiLogin);

    /**
     * Get all the apiLogins.
     *
     * @return the list of entities.
     */
    List<ApiLogin> findAll();


    /**
     * Get the "id" apiLogin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiLogin> findOne(Long id);

    /**
     * Delete the "id" apiLogin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
