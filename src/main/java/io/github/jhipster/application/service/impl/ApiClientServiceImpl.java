package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiClientService;
import io.github.jhipster.application.domain.ApiClient;
import io.github.jhipster.application.repository.ApiClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiClient}.
 */
@Service
@Transactional
public class ApiClientServiceImpl implements ApiClientService {

    private final Logger log = LoggerFactory.getLogger(ApiClientServiceImpl.class);

    private final ApiClientRepository apiClientRepository;

    public ApiClientServiceImpl(ApiClientRepository apiClientRepository) {
        this.apiClientRepository = apiClientRepository;
    }

    /**
     * Save a apiClient.
     *
     * @param apiClient the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiClient save(ApiClient apiClient) {
        log.debug("Request to save ApiClient : {}", apiClient);
        return apiClientRepository.save(apiClient);
    }

    /**
     * Get all the apiClients.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiClient> findAll() {
        log.debug("Request to get all ApiClients");
        return apiClientRepository.findAll();
    }


    /**
     * Get one apiClient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiClient> findOne(Long id) {
        log.debug("Request to get ApiClient : {}", id);
        return apiClientRepository.findById(id);
    }

    /**
     * Delete the apiClient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiClient : {}", id);
        apiClientRepository.deleteById(id);
    }
}
