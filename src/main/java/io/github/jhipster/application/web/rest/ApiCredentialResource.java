package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiCredential;
import io.github.jhipster.application.service.ApiCredentialService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiCredential}.
 */
@RestController
@RequestMapping("/api")
public class ApiCredentialResource {

    private final Logger log = LoggerFactory.getLogger(ApiCredentialResource.class);

    private static final String ENTITY_NAME = "apiCredential";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiCredentialService apiCredentialService;

    public ApiCredentialResource(ApiCredentialService apiCredentialService) {
        this.apiCredentialService = apiCredentialService;
    }

    /**
     * {@code POST  /api-credentials} : Create a new apiCredential.
     *
     * @param apiCredential the apiCredential to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiCredential, or with status {@code 400 (Bad Request)} if the apiCredential has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-credentials")
    public ResponseEntity<ApiCredential> createApiCredential(@Valid @RequestBody ApiCredential apiCredential) throws URISyntaxException {
        log.debug("REST request to save ApiCredential : {}", apiCredential);
        if (apiCredential.getId() != null) {
            throw new BadRequestAlertException("A new apiCredential cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiCredential result = apiCredentialService.save(apiCredential);
        return ResponseEntity.created(new URI("/api/api-credentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-credentials} : Updates an existing apiCredential.
     *
     * @param apiCredential the apiCredential to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiCredential,
     * or with status {@code 400 (Bad Request)} if the apiCredential is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiCredential couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-credentials")
    public ResponseEntity<ApiCredential> updateApiCredential(@Valid @RequestBody ApiCredential apiCredential) throws URISyntaxException {
        log.debug("REST request to update ApiCredential : {}", apiCredential);
        if (apiCredential.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiCredential result = apiCredentialService.save(apiCredential);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiCredential.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-credentials} : get all the apiCredentials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiCredentials in body.
     */
    @GetMapping("/api-credentials")
    public List<ApiCredential> getAllApiCredentials() {
        log.debug("REST request to get all ApiCredentials");
        return apiCredentialService.findAll();
    }

    /**
     * {@code GET  /api-credentials/:id} : get the "id" apiCredential.
     *
     * @param id the id of the apiCredential to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiCredential, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-credentials/{id}")
    public ResponseEntity<ApiCredential> getApiCredential(@PathVariable Long id) {
        log.debug("REST request to get ApiCredential : {}", id);
        Optional<ApiCredential> apiCredential = apiCredentialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiCredential);
    }

    /**
     * {@code DELETE  /api-credentials/:id} : delete the "id" apiCredential.
     *
     * @param id the id of the apiCredential to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-credentials/{id}")
    public ResponseEntity<Void> deleteApiCredential(@PathVariable Long id) {
        log.debug("REST request to delete ApiCredential : {}", id);
        apiCredentialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
