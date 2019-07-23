package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiCredentialService;
import io.github.jhipster.application.domain.ApiCredential;
import io.github.jhipster.application.repository.ApiCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiCredential}.
 */
@Service
@Transactional
public class ApiCredentialServiceImpl implements ApiCredentialService {

    private final Logger log = LoggerFactory.getLogger(ApiCredentialServiceImpl.class);

    private final ApiCredentialRepository apiCredentialRepository;

    public ApiCredentialServiceImpl(ApiCredentialRepository apiCredentialRepository) {
        this.apiCredentialRepository = apiCredentialRepository;
    }

    /**
     * Save a apiCredential.
     *
     * @param apiCredential the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiCredential save(ApiCredential apiCredential) {
        log.debug("Request to save ApiCredential : {}", apiCredential);
        return apiCredentialRepository.save(apiCredential);
    }

    /**
     * Get all the apiCredentials.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiCredential> findAll() {
        log.debug("Request to get all ApiCredentials");
        return apiCredentialRepository.findAll();
    }


    /**
     * Get one apiCredential by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiCredential> findOne(Long id) {
        log.debug("Request to get ApiCredential : {}", id);
        return apiCredentialRepository.findById(id);
    }

    /**
     * Delete the apiCredential by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiCredential : {}", id);
        apiCredentialRepository.deleteById(id);
    }
}
