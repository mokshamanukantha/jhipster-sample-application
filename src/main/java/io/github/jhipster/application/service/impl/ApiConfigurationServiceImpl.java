package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiConfigurationService;
import io.github.jhipster.application.domain.ApiConfiguration;
import io.github.jhipster.application.repository.ApiConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiConfiguration}.
 */
@Service
@Transactional
public class ApiConfigurationServiceImpl implements ApiConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationServiceImpl.class);

    private final ApiConfigurationRepository apiConfigurationRepository;

    public ApiConfigurationServiceImpl(ApiConfigurationRepository apiConfigurationRepository) {
        this.apiConfigurationRepository = apiConfigurationRepository;
    }

    /**
     * Save a apiConfiguration.
     *
     * @param apiConfiguration the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiConfiguration save(ApiConfiguration apiConfiguration) {
        log.debug("Request to save ApiConfiguration : {}", apiConfiguration);
        return apiConfigurationRepository.save(apiConfiguration);
    }

    /**
     * Get all the apiConfigurations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiConfiguration> findAll() {
        log.debug("Request to get all ApiConfigurations");
        return apiConfigurationRepository.findAll();
    }


    /**
     * Get one apiConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiConfiguration> findOne(Long id) {
        log.debug("Request to get ApiConfiguration : {}", id);
        return apiConfigurationRepository.findById(id);
    }

    /**
     * Delete the apiConfiguration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiConfiguration : {}", id);
        apiConfigurationRepository.deleteById(id);
    }
}
