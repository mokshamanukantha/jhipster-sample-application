package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiResource;
import io.github.jhipster.application.service.ApiResourceService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiResource}.
 */
@RestController
@RequestMapping("/api")
public class ApiResourceResource {

    private final Logger log = LoggerFactory.getLogger(ApiResourceResource.class);

    private static final String ENTITY_NAME = "apiResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiResourceService apiResourceService;

    public ApiResourceResource(ApiResourceService apiResourceService) {
        this.apiResourceService = apiResourceService;
    }

    /**
     * {@code POST  /api-resources} : Create a new apiResource.
     *
     * @param apiResource the apiResource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiResource, or with status {@code 400 (Bad Request)} if the apiResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-resources")
    public ResponseEntity<ApiResource> createApiResource(@Valid @RequestBody ApiResource apiResource) throws URISyntaxException {
        log.debug("REST request to save ApiResource : {}", apiResource);
        if (apiResource.getId() != null) {
            throw new BadRequestAlertException("A new apiResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiResource result = apiResourceService.save(apiResource);
        return ResponseEntity.created(new URI("/api/api-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-resources} : Updates an existing apiResource.
     *
     * @param apiResource the apiResource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiResource,
     * or with status {@code 400 (Bad Request)} if the apiResource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiResource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-resources")
    public ResponseEntity<ApiResource> updateApiResource(@Valid @RequestBody ApiResource apiResource) throws URISyntaxException {
        log.debug("REST request to update ApiResource : {}", apiResource);
        if (apiResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiResource result = apiResourceService.save(apiResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiResource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-resources} : get all the apiResources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiResources in body.
     */
    @GetMapping("/api-resources")
    public List<ApiResource> getAllApiResources() {
        log.debug("REST request to get all ApiResources");
        return apiResourceService.findAll();
    }

    /**
     * {@code GET  /api-resources/:id} : get the "id" apiResource.
     *
     * @param id the id of the apiResource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiResource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-resources/{id}")
    public ResponseEntity<ApiResource> getApiResource(@PathVariable Long id) {
        log.debug("REST request to get ApiResource : {}", id);
        Optional<ApiResource> apiResource = apiResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiResource);
    }

    /**
     * {@code DELETE  /api-resources/:id} : delete the "id" apiResource.
     *
     * @param id the id of the apiResource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-resources/{id}")
    public ResponseEntity<Void> deleteApiResource(@PathVariable Long id) {
        log.debug("REST request to delete ApiResource : {}", id);
        apiResourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
