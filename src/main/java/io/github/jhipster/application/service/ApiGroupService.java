package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ApiGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApiGroup}.
 */
public interface ApiGroupService {

    /**
     * Save a apiGroup.
     *
     * @param apiGroup the entity to save.
     * @return the persisted entity.
     */
    ApiGroup save(ApiGroup apiGroup);

    /**
     * Get all the apiGroups.
     *
     * @return the list of entities.
     */
    List<ApiGroup> findAll();

    /**
     * Get all the apiGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ApiGroup> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" apiGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiGroup> findOne(Long id);

    /**
     * Delete the "id" apiGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
