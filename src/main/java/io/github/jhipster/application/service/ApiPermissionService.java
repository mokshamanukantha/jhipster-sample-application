package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiPermission;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiPermission}.
 */
public interface ApiPermissionService {

    /**
     * Save a apiPermission.
     *
     * @param apiPermission the entity to save.
     * @return the persisted entity.
     */
    ApiPermission save(ApiPermission apiPermission);

    /**
     * Get all the apiPermissions.
     *
     * @return the list of entities.
     */
    List<ApiPermission> findAll();


    /**
     * Get the "id" apiPermission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiPermission> findOne(Long id);

    /**
     * Delete the "id" apiPermission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
