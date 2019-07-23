package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiPermissionService;
import io.github.jhipster.application.domain.ApiPermission;
import io.github.jhipster.application.repository.ApiPermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiPermission}.
 */
@Service
@Transactional
public class ApiPermissionServiceImpl implements ApiPermissionService {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionServiceImpl.class);

    private final ApiPermissionRepository apiPermissionRepository;

    public ApiPermissionServiceImpl(ApiPermissionRepository apiPermissionRepository) {
        this.apiPermissionRepository = apiPermissionRepository;
    }

    /**
     * Save a apiPermission.
     *
     * @param apiPermission the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiPermission save(ApiPermission apiPermission) {
        log.debug("Request to save ApiPermission : {}", apiPermission);
        return apiPermissionRepository.save(apiPermission);
    }

    /**
     * Get all the apiPermissions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiPermission> findAll() {
        log.debug("Request to get all ApiPermissions");
        return apiPermissionRepository.findAll();
    }


    /**
     * Get one apiPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiPermission> findOne(Long id) {
        log.debug("Request to get ApiPermission : {}", id);
        return apiPermissionRepository.findById(id);
    }

    /**
     * Delete the apiPermission by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiPermission : {}", id);
        apiPermissionRepository.deleteById(id);
    }
}
