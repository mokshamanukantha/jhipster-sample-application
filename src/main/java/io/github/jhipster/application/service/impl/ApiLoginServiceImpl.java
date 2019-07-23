package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiLoginService;
import io.github.jhipster.application.domain.ApiLogin;
import io.github.jhipster.application.repository.ApiLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiLogin}.
 */
@Service
@Transactional
public class ApiLoginServiceImpl implements ApiLoginService {

    private final Logger log = LoggerFactory.getLogger(ApiLoginServiceImpl.class);

    private final ApiLoginRepository apiLoginRepository;

    public ApiLoginServiceImpl(ApiLoginRepository apiLoginRepository) {
        this.apiLoginRepository = apiLoginRepository;
    }

    /**
     * Save a apiLogin.
     *
     * @param apiLogin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiLogin save(ApiLogin apiLogin) {
        log.debug("Request to save ApiLogin : {}", apiLogin);
        return apiLoginRepository.save(apiLogin);
    }

    /**
     * Get all the apiLogins.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiLogin> findAll() {
        log.debug("Request to get all ApiLogins");
        return apiLoginRepository.findAll();
    }


    /**
     * Get one apiLogin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiLogin> findOne(Long id) {
        log.debug("Request to get ApiLogin : {}", id);
        return apiLoginRepository.findById(id);
    }

    /**
     * Delete the apiLogin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiLogin : {}", id);
        apiLoginRepository.deleteById(id);
    }
}
