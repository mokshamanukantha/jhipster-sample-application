package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiResourceService;
import io.github.jhipster.application.domain.ApiResource;
import io.github.jhipster.application.repository.ApiResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiResource}.
 */
@Service
@Transactional
public class ApiResourceServiceImpl implements ApiResourceService {

    private final Logger log = LoggerFactory.getLogger(ApiResourceServiceImpl.class);

    private final ApiResourceRepository apiResourceRepository;

    public ApiResourceServiceImpl(ApiResourceRepository apiResourceRepository) {
        this.apiResourceRepository = apiResourceRepository;
    }

    /**
     * Save a apiResource.
     *
     * @param apiResource the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiResource save(ApiResource apiResource) {
        log.debug("Request to save ApiResource : {}", apiResource);
        return apiResourceRepository.save(apiResource);
    }

    /**
     * Get all the apiResources.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiResource> findAll() {
        log.debug("Request to get all ApiResources");
        return apiResourceRepository.findAll();
    }


    /**
     * Get one apiResource by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiResource> findOne(Long id) {
        log.debug("Request to get ApiResource : {}", id);
        return apiResourceRepository.findById(id);
    }

    /**
     * Delete the apiResource by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiResource : {}", id);
        apiResourceRepository.deleteById(id);
    }
}
