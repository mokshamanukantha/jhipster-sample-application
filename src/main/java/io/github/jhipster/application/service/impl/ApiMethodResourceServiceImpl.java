package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiMethodResourceService;
import io.github.jhipster.application.domain.ApiMethodResource;
import io.github.jhipster.application.repository.ApiMethodResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiMethodResource}.
 */
@Service
@Transactional
public class ApiMethodResourceServiceImpl implements ApiMethodResourceService {

    private final Logger log = LoggerFactory.getLogger(ApiMethodResourceServiceImpl.class);

    private final ApiMethodResourceRepository apiMethodResourceRepository;

    public ApiMethodResourceServiceImpl(ApiMethodResourceRepository apiMethodResourceRepository) {
        this.apiMethodResourceRepository = apiMethodResourceRepository;
    }

    /**
     * Save a apiMethodResource.
     *
     * @param apiMethodResource the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiMethodResource save(ApiMethodResource apiMethodResource) {
        log.debug("Request to save ApiMethodResource : {}", apiMethodResource);
        return apiMethodResourceRepository.save(apiMethodResource);
    }

    /**
     * Get all the apiMethodResources.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiMethodResource> findAll() {
        log.debug("Request to get all ApiMethodResources");
        return apiMethodResourceRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the apiMethodResources with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApiMethodResource> findAllWithEagerRelationships(Pageable pageable) {
        return apiMethodResourceRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one apiMethodResource by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiMethodResource> findOne(Long id) {
        log.debug("Request to get ApiMethodResource : {}", id);
        return apiMethodResourceRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the apiMethodResource by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiMethodResource : {}", id);
        apiMethodResourceRepository.deleteById(id);
    }
}
