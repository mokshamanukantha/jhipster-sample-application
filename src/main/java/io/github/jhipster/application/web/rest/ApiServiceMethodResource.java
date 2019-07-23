package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiServiceMethod;
import io.github.jhipster.application.service.ApiServiceMethodService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiServiceMethod}.
 */
@RestController
@RequestMapping("/api")
public class ApiServiceMethodResource {

    private final Logger log = LoggerFactory.getLogger(ApiServiceMethodResource.class);

    private static final String ENTITY_NAME = "apiServiceMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiServiceMethodService apiServiceMethodService;

    public ApiServiceMethodResource(ApiServiceMethodService apiServiceMethodService) {
        this.apiServiceMethodService = apiServiceMethodService;
    }

    /**
     * {@code POST  /api-service-methods} : Create a new apiServiceMethod.
     *
     * @param apiServiceMethod the apiServiceMethod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiServiceMethod, or with status {@code 400 (Bad Request)} if the apiServiceMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-service-methods")
    public ResponseEntity<ApiServiceMethod> createApiServiceMethod(@Valid @RequestBody ApiServiceMethod apiServiceMethod) throws URISyntaxException {
        log.debug("REST request to save ApiServiceMethod : {}", apiServiceMethod);
        if (apiServiceMethod.getId() != null) {
            throw new BadRequestAlertException("A new apiServiceMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiServiceMethod result = apiServiceMethodService.save(apiServiceMethod);
        return ResponseEntity.created(new URI("/api/api-service-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-service-methods} : Updates an existing apiServiceMethod.
     *
     * @param apiServiceMethod the apiServiceMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiServiceMethod,
     * or with status {@code 400 (Bad Request)} if the apiServiceMethod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiServiceMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-service-methods")
    public ResponseEntity<ApiServiceMethod> updateApiServiceMethod(@Valid @RequestBody ApiServiceMethod apiServiceMethod) throws URISyntaxException {
        log.debug("REST request to update ApiServiceMethod : {}", apiServiceMethod);
        if (apiServiceMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiServiceMethod result = apiServiceMethodService.save(apiServiceMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiServiceMethod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-service-methods} : get all the apiServiceMethods.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiServiceMethods in body.
     */
    @GetMapping("/api-service-methods")
    public List<ApiServiceMethod> getAllApiServiceMethods(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ApiServiceMethods");
        return apiServiceMethodService.findAll();
    }

    /**
     * {@code GET  /api-service-methods/:id} : get the "id" apiServiceMethod.
     *
     * @param id the id of the apiServiceMethod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiServiceMethod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-service-methods/{id}")
    public ResponseEntity<ApiServiceMethod> getApiServiceMethod(@PathVariable Long id) {
        log.debug("REST request to get ApiServiceMethod : {}", id);
        Optional<ApiServiceMethod> apiServiceMethod = apiServiceMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiServiceMethod);
    }

    /**
     * {@code DELETE  /api-service-methods/:id} : delete the "id" apiServiceMethod.
     *
     * @param id the id of the apiServiceMethod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-service-methods/{id}")
    public ResponseEntity<Void> deleteApiServiceMethod(@PathVariable Long id) {
        log.debug("REST request to delete ApiServiceMethod : {}", id);
        apiServiceMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
