package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiSession;
import io.github.jhipster.application.service.ApiSessionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiSession}.
 */
@RestController
@RequestMapping("/api")
public class ApiSessionResource {

    private final Logger log = LoggerFactory.getLogger(ApiSessionResource.class);

    private static final String ENTITY_NAME = "apiSession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiSessionService apiSessionService;

    public ApiSessionResource(ApiSessionService apiSessionService) {
        this.apiSessionService = apiSessionService;
    }

    /**
     * {@code POST  /api-sessions} : Create a new apiSession.
     *
     * @param apiSession the apiSession to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiSession, or with status {@code 400 (Bad Request)} if the apiSession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-sessions")
    public ResponseEntity<ApiSession> createApiSession(@RequestBody ApiSession apiSession) throws URISyntaxException {
        log.debug("REST request to save ApiSession : {}", apiSession);
        if (apiSession.getId() != null) {
            throw new BadRequestAlertException("A new apiSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiSession result = apiSessionService.save(apiSession);
        return ResponseEntity.created(new URI("/api/api-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-sessions} : Updates an existing apiSession.
     *
     * @param apiSession the apiSession to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiSession,
     * or with status {@code 400 (Bad Request)} if the apiSession is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiSession couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-sessions")
    public ResponseEntity<ApiSession> updateApiSession(@RequestBody ApiSession apiSession) throws URISyntaxException {
        log.debug("REST request to update ApiSession : {}", apiSession);
        if (apiSession.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiSession result = apiSessionService.save(apiSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiSession.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-sessions} : get all the apiSessions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiSessions in body.
     */
    @GetMapping("/api-sessions")
    public List<ApiSession> getAllApiSessions() {
        log.debug("REST request to get all ApiSessions");
        return apiSessionService.findAll();
    }

    /**
     * {@code GET  /api-sessions/:id} : get the "id" apiSession.
     *
     * @param id the id of the apiSession to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiSession, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-sessions/{id}")
    public ResponseEntity<ApiSession> getApiSession(@PathVariable Long id) {
        log.debug("REST request to get ApiSession : {}", id);
        Optional<ApiSession> apiSession = apiSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiSession);
    }

    /**
     * {@code DELETE  /api-sessions/:id} : delete the "id" apiSession.
     *
     * @param id the id of the apiSession to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-sessions/{id}")
    public ResponseEntity<Void> deleteApiSession(@PathVariable Long id) {
        log.debug("REST request to delete ApiSession : {}", id);
        apiSessionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
