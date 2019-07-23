package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ApiSessionService;
import io.github.jhipster.application.domain.ApiSession;
import io.github.jhipster.application.repository.ApiSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiSession}.
 */
@Service
@Transactional
public class ApiSessionServiceImpl implements ApiSessionService {

    private final Logger log = LoggerFactory.getLogger(ApiSessionServiceImpl.class);

    private final ApiSessionRepository apiSessionRepository;

    public ApiSessionServiceImpl(ApiSessionRepository apiSessionRepository) {
        this.apiSessionRepository = apiSessionRepository;
    }

    /**
     * Save a apiSession.
     *
     * @param apiSession the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiSession save(ApiSession apiSession) {
        log.debug("Request to save ApiSession : {}", apiSession);
        return apiSessionRepository.save(apiSession);
    }

    /**
     * Get all the apiSessions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiSession> findAll() {
        log.debug("Request to get all ApiSessions");
        return apiSessionRepository.findAll();
    }


    /**
     * Get one apiSession by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiSession> findOne(Long id) {
        log.debug("Request to get ApiSession : {}", id);
        return apiSessionRepository.findById(id);
    }

    /**
     * Delete the apiSession by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiSession : {}", id);
        apiSessionRepository.deleteById(id);
    }
}
