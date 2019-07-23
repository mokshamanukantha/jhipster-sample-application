package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiServiceService;
import io.github.jhipster.application.domain.ApiService;
import io.github.jhipster.application.repository.ApiServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiService}.
 */
@Service
@Transactional
public class ApiServiceServiceImpl implements ApiServiceService {

    private final Logger log = LoggerFactory.getLogger(ApiServiceServiceImpl.class);

    private final ApiServiceRepository apiServiceRepository;

    public ApiServiceServiceImpl(ApiServiceRepository apiServiceRepository) {
        this.apiServiceRepository = apiServiceRepository;
    }

    /**
     * Save a apiService.
     *
     * @param apiService the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiService save(ApiService apiService) {
        log.debug("Request to save ApiService : {}", apiService);
        return apiServiceRepository.save(apiService);
    }

    /**
     * Get all the apiServices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiService> findAll() {
        log.debug("Request to get all ApiServices");
        return apiServiceRepository.findAll();
    }


    /**
     * Get one apiService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiService> findOne(Long id) {
        log.debug("Request to get ApiService : {}", id);
        return apiServiceRepository.findById(id);
    }

    /**
     * Delete the apiService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiService : {}", id);
        apiServiceRepository.deleteById(id);
    }
}
