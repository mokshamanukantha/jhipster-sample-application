package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiServiceMethodService;
import io.github.jhipster.application.domain.ApiServiceMethod;
import io.github.jhipster.application.repository.ApiServiceMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiServiceMethod}.
 */
@Service
@Transactional
public class ApiServiceMethodServiceImpl implements ApiServiceMethodService {

    private final Logger log = LoggerFactory.getLogger(ApiServiceMethodServiceImpl.class);

    private final ApiServiceMethodRepository apiServiceMethodRepository;

    public ApiServiceMethodServiceImpl(ApiServiceMethodRepository apiServiceMethodRepository) {
        this.apiServiceMethodRepository = apiServiceMethodRepository;
    }

    /**
     * Save a apiServiceMethod.
     *
     * @param apiServiceMethod the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiServiceMethod save(ApiServiceMethod apiServiceMethod) {
        log.debug("Request to save ApiServiceMethod : {}", apiServiceMethod);
        return apiServiceMethodRepository.save(apiServiceMethod);
    }

    /**
     * Get all the apiServiceMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiServiceMethod> findAll() {
        log.debug("Request to get all ApiServiceMethods");
        return apiServiceMethodRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the apiServiceMethods with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApiServiceMethod> findAllWithEagerRelationships(Pageable pageable) {
        return apiServiceMethodRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one apiServiceMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiServiceMethod> findOne(Long id) {
        log.debug("Request to get ApiServiceMethod : {}", id);
        return apiServiceMethodRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the apiServiceMethod by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiServiceMethod : {}", id);
        apiServiceMethodRepository.deleteById(id);
    }
}
