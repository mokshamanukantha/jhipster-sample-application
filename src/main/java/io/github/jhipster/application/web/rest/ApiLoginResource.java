package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiLogin;
import io.github.jhipster.application.service.ApiLoginService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiLogin}.
 */
@RestController
@RequestMapping("/api")
public class ApiLoginResource {

    private final Logger log = LoggerFactory.getLogger(ApiLoginResource.class);

    private static final String ENTITY_NAME = "apiLogin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiLoginService apiLoginService;

    public ApiLoginResource(ApiLoginService apiLoginService) {
        this.apiLoginService = apiLoginService;
    }

    /**
     * {@code POST  /api-logins} : Create a new apiLogin.
     *
     * @param apiLogin the apiLogin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiLogin, or with status {@code 400 (Bad Request)} if the apiLogin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-logins")
    public ResponseEntity<ApiLogin> createApiLogin(@RequestBody ApiLogin apiLogin) throws URISyntaxException {
        log.debug("REST request to save ApiLogin : {}", apiLogin);
        if (apiLogin.getId() != null) {
            throw new BadRequestAlertException("A new apiLogin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiLogin result = apiLoginService.save(apiLogin);
        return ResponseEntity.created(new URI("/api/api-logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-logins} : Updates an existing apiLogin.
     *
     * @param apiLogin the apiLogin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiLogin,
     * or with status {@code 400 (Bad Request)} if the apiLogin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiLogin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-logins")
    public ResponseEntity<ApiLogin> updateApiLogin(@RequestBody ApiLogin apiLogin) throws URISyntaxException {
        log.debug("REST request to update ApiLogin : {}", apiLogin);
        if (apiLogin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiLogin result = apiLoginService.save(apiLogin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiLogin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-logins} : get all the apiLogins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiLogins in body.
     */
    @GetMapping("/api-logins")
    public List<ApiLogin> getAllApiLogins() {
        log.debug("REST request to get all ApiLogins");
        return apiLoginService.findAll();
    }

    /**
     * {@code GET  /api-logins/:id} : get the "id" apiLogin.
     *
     * @param id the id of the apiLogin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiLogin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-logins/{id}")
    public ResponseEntity<ApiLogin> getApiLogin(@PathVariable Long id) {
        log.debug("REST request to get ApiLogin : {}", id);
        Optional<ApiLogin> apiLogin = apiLoginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiLogin);
    }

    /**
     * {@code DELETE  /api-logins/:id} : delete the "id" apiLogin.
     *
     * @param id the id of the apiLogin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-logins/{id}")
    public ResponseEntity<Void> deleteApiLogin(@PathVariable Long id) {
        log.debug("REST request to delete ApiLogin : {}", id);
        apiLoginService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
