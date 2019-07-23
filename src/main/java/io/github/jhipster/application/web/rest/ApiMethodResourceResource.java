package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiMethodResource;
import io.github.jhipster.application.service.ApiMethodResourceService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiMethodResource}.
 */
@RestController
@RequestMapping("/api")
public class ApiMethodResourceResource {

    private final Logger log = LoggerFactory.getLogger(ApiMethodResourceResource.class);

    private static final String ENTITY_NAME = "apiMethodResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiMethodResourceService apiMethodResourceService;

    public ApiMethodResourceResource(ApiMethodResourceService apiMethodResourceService) {
        this.apiMethodResourceService = apiMethodResourceService;
    }

    /**
     * {@code POST  /api-method-resources} : Create a new apiMethodResource.
     *
     * @param apiMethodResource the apiMethodResource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiMethodResource, or with status {@code 400 (Bad Request)} if the apiMethodResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-method-resources")
    public ResponseEntity<ApiMethodResource> createApiMethodResource(@Valid @RequestBody ApiMethodResource apiMethodResource) throws URISyntaxException {
        log.debug("REST request to save ApiMethodResource : {}", apiMethodResource);
        if (apiMethodResource.getId() != null) {
            throw new BadRequestAlertException("A new apiMethodResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiMethodResource result = apiMethodResourceService.save(apiMethodResource);
        return ResponseEntity.created(new URI("/api/api-method-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-method-resources} : Updates an existing apiMethodResource.
     *
     * @param apiMethodResource the apiMethodResource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiMethodResource,
     * or with status {@code 400 (Bad Request)} if the apiMethodResource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiMethodResource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-method-resources")
    public ResponseEntity<ApiMethodResource> updateApiMethodResource(@Valid @RequestBody ApiMethodResource apiMethodResource) throws URISyntaxException {
        log.debug("REST request to update ApiMethodResource : {}", apiMethodResource);
        if (apiMethodResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiMethodResource result = apiMethodResourceService.save(apiMethodResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiMethodResource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-method-resources} : get all the apiMethodResources.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiMethodResources in body.
     */
    @GetMapping("/api-method-resources")
    public List<ApiMethodResource> getAllApiMethodResources(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ApiMethodResources");
        return apiMethodResourceService.findAll();
    }

    /**
     * {@code GET  /api-method-resources/:id} : get the "id" apiMethodResource.
     *
     * @param id the id of the apiMethodResource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiMethodResource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-method-resources/{id}")
    public ResponseEntity<ApiMethodResource> getApiMethodResource(@PathVariable Long id) {
        log.debug("REST request to get ApiMethodResource : {}", id);
        Optional<ApiMethodResource> apiMethodResource = apiMethodResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiMethodResource);
    }

    /**
     * {@code DELETE  /api-method-resources/:id} : delete the "id" apiMethodResource.
     *
     * @param id the id of the apiMethodResource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-method-resources/{id}")
    public ResponseEntity<Void> deleteApiMethodResource(@PathVariable Long id) {
        log.debug("REST request to delete ApiMethodResource : {}", id);
        apiMethodResourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
