package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiGroup;
import io.github.jhipster.application.service.ApiGroupService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiGroup}.
 */
@RestController
@RequestMapping("/api")
public class ApiGroupResource {

    private final Logger log = LoggerFactory.getLogger(ApiGroupResource.class);

    private static final String ENTITY_NAME = "apiGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiGroupService apiGroupService;

    public ApiGroupResource(ApiGroupService apiGroupService) {
        this.apiGroupService = apiGroupService;
    }

    /**
     * {@code POST  /api-groups} : Create a new apiGroup.
     *
     * @param apiGroup the apiGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiGroup, or with status {@code 400 (Bad Request)} if the apiGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-groups")
    public ResponseEntity<ApiGroup> createApiGroup(@Valid @RequestBody ApiGroup apiGroup) throws URISyntaxException {
        log.debug("REST request to save ApiGroup : {}", apiGroup);
        if (apiGroup.getId() != null) {
            throw new BadRequestAlertException("A new apiGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiGroup result = apiGroupService.save(apiGroup);
        return ResponseEntity.created(new URI("/api/api-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-groups} : Updates an existing apiGroup.
     *
     * @param apiGroup the apiGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiGroup,
     * or with status {@code 400 (Bad Request)} if the apiGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-groups")
    public ResponseEntity<ApiGroup> updateApiGroup(@Valid @RequestBody ApiGroup apiGroup) throws URISyntaxException {
        log.debug("REST request to update ApiGroup : {}", apiGroup);
        if (apiGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiGroup result = apiGroupService.save(apiGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-groups} : get all the apiGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiGroups in body.
     */
    @GetMapping("/api-groups")
    public List<ApiGroup> getAllApiGroups(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ApiGroups");
        return apiGroupService.findAll();
    }

    /**
     * {@code GET  /api-groups/:id} : get the "id" apiGroup.
     *
     * @param id the id of the apiGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-groups/{id}")
    public ResponseEntity<ApiGroup> getApiGroup(@PathVariable Long id) {
        log.debug("REST request to get ApiGroup : {}", id);
        Optional<ApiGroup> apiGroup = apiGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiGroup);
    }

    /**
     * {@code DELETE  /api-groups/:id} : delete the "id" apiGroup.
     *
     * @param id the id of the apiGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-groups/{id}")
    public ResponseEntity<Void> deleteApiGroup(@PathVariable Long id) {
        log.debug("REST request to delete ApiGroup : {}", id);
        apiGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
