package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiService;
import io.github.jhipster.application.service.ApiServiceService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiService}.
 */
@RestController
@RequestMapping("/api")
public class ApiServiceResource {

    private final Logger log = LoggerFactory.getLogger(ApiServiceResource.class);

    private static final String ENTITY_NAME = "apiService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiServiceService apiServiceService;

    public ApiServiceResource(ApiServiceService apiServiceService) {
        this.apiServiceService = apiServiceService;
    }

    /**
     * {@code POST  /api-services} : Create a new apiService.
     *
     * @param apiService the apiService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiService, or with status {@code 400 (Bad Request)} if the apiService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-services")
    public ResponseEntity<ApiService> createApiService(@Valid @RequestBody ApiService apiService) throws URISyntaxException {
        log.debug("REST request to save ApiService : {}", apiService);
        if (apiService.getId() != null) {
            throw new BadRequestAlertException("A new apiService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiService result = apiServiceService.save(apiService);
        return ResponseEntity.created(new URI("/api/api-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-services} : Updates an existing apiService.
     *
     * @param apiService the apiService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiService,
     * or with status {@code 400 (Bad Request)} if the apiService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-services")
    public ResponseEntity<ApiService> updateApiService(@Valid @RequestBody ApiService apiService) throws URISyntaxException {
        log.debug("REST request to update ApiService : {}", apiService);
        if (apiService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiService result = apiServiceService.save(apiService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-services} : get all the apiServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiServices in body.
     */
    @GetMapping("/api-services")
    public List<ApiService> getAllApiServices() {
        log.debug("REST request to get all ApiServices");
        return apiServiceService.findAll();
    }

    /**
     * {@code GET  /api-services/:id} : get the "id" apiService.
     *
     * @param id the id of the apiService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-services/{id}")
    public ResponseEntity<ApiService> getApiService(@PathVariable Long id) {
        log.debug("REST request to get ApiService : {}", id);
        Optional<ApiService> apiService = apiServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiService);
    }

    /**
     * {@code DELETE  /api-services/:id} : delete the "id" apiService.
     *
     * @param id the id of the apiService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-services/{id}")
    public ResponseEntity<Void> deleteApiService(@PathVariable Long id) {
        log.debug("REST request to delete ApiService : {}", id);
        apiServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
