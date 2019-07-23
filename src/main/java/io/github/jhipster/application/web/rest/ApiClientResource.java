package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiClient;
import io.github.jhipster.application.service.ApiClientService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiClient}.
 */
@RestController
@RequestMapping("/api")
public class ApiClientResource {

    private final Logger log = LoggerFactory.getLogger(ApiClientResource.class);

    private static final String ENTITY_NAME = "apiClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiClientService apiClientService;

    public ApiClientResource(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    /**
     * {@code POST  /api-clients} : Create a new apiClient.
     *
     * @param apiClient the apiClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiClient, or with status {@code 400 (Bad Request)} if the apiClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-clients")
    public ResponseEntity<ApiClient> createApiClient(@Valid @RequestBody ApiClient apiClient) throws URISyntaxException {
        log.debug("REST request to save ApiClient : {}", apiClient);
        if (apiClient.getId() != null) {
            throw new BadRequestAlertException("A new apiClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiClient result = apiClientService.save(apiClient);
        return ResponseEntity.created(new URI("/api/api-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-clients} : Updates an existing apiClient.
     *
     * @param apiClient the apiClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiClient,
     * or with status {@code 400 (Bad Request)} if the apiClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-clients")
    public ResponseEntity<ApiClient> updateApiClient(@Valid @RequestBody ApiClient apiClient) throws URISyntaxException {
        log.debug("REST request to update ApiClient : {}", apiClient);
        if (apiClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiClient result = apiClientService.save(apiClient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-clients} : get all the apiClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiClients in body.
     */
    @GetMapping("/api-clients")
    public List<ApiClient> getAllApiClients() {
        log.debug("REST request to get all ApiClients");
        return apiClientService.findAll();
    }

    /**
     * {@code GET  /api-clients/:id} : get the "id" apiClient.
     *
     * @param id the id of the apiClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-clients/{id}")
    public ResponseEntity<ApiClient> getApiClient(@PathVariable Long id) {
        log.debug("REST request to get ApiClient : {}", id);
        Optional<ApiClient> apiClient = apiClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiClient);
    }

    /**
     * {@code DELETE  /api-clients/:id} : delete the "id" apiClient.
     *
     * @param id the id of the apiClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-clients/{id}")
    public ResponseEntity<Void> deleteApiClient(@PathVariable Long id) {
        log.debug("REST request to delete ApiClient : {}", id);
        apiClientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
