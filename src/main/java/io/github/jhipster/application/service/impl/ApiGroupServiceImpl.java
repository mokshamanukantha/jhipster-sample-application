package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiGroupService;
import io.github.jhipster.application.domain.ApiGroup;
import io.github.jhipster.application.repository.ApiGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiGroup}.
 */
@Service
@Transactional
public class ApiGroupServiceImpl implements ApiGroupService {

    private final Logger log = LoggerFactory.getLogger(ApiGroupServiceImpl.class);

    private final ApiGroupRepository apiGroupRepository;

    public ApiGroupServiceImpl(ApiGroupRepository apiGroupRepository) {
        this.apiGroupRepository = apiGroupRepository;
    }

    /**
     * Save a apiGroup.
     *
     * @param apiGroup the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiGroup save(ApiGroup apiGroup) {
        log.debug("Request to save ApiGroup : {}", apiGroup);
        return apiGroupRepository.save(apiGroup);
    }

    /**
     * Get all the apiGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiGroup> findAll() {
        log.debug("Request to get all ApiGroups");
        return apiGroupRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the apiGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApiGroup> findAllWithEagerRelationships(Pageable pageable) {
        return apiGroupRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one apiGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiGroup> findOne(Long id) {
        log.debug("Request to get ApiGroup : {}", id);
        return apiGroupRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the apiGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiGroup : {}", id);
        apiGroupRepository.deleteById(id);
    }
}
