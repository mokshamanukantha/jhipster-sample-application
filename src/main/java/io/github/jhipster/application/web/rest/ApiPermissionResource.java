package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiPermission;
import io.github.jhipster.application.service.ApiPermissionService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiPermission}.
 */
@RestController
@RequestMapping("/api")
public class ApiPermissionResource {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionResource.class);

    private static final String ENTITY_NAME = "apiPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiPermissionService apiPermissionService;

    public ApiPermissionResource(ApiPermissionService apiPermissionService) {
        this.apiPermissionService = apiPermissionService;
    }

    /**
     * {@code POST  /api-permissions} : Create a new apiPermission.
     *
     * @param apiPermission the apiPermission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiPermission, or with status {@code 400 (Bad Request)} if the apiPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-permissions")
    public ResponseEntity<ApiPermission> createApiPermission(@Valid @RequestBody ApiPermission apiPermission) throws URISyntaxException {
        log.debug("REST request to save ApiPermission : {}", apiPermission);
        if (apiPermission.getId() != null) {
            throw new BadRequestAlertException("A new apiPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiPermission result = apiPermissionService.save(apiPermission);
        return ResponseEntity.created(new URI("/api/api-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-permissions} : Updates an existing apiPermission.
     *
     * @param apiPermission the apiPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiPermission,
     * or with status {@code 400 (Bad Request)} if the apiPermission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-permissions")
    public ResponseEntity<ApiPermission> updateApiPermission(@Valid @RequestBody ApiPermission apiPermission) throws URISyntaxException {
        log.debug("REST request to update ApiPermission : {}", apiPermission);
        if (apiPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiPermission result = apiPermissionService.save(apiPermission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiPermission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-permissions} : get all the apiPermissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiPermissions in body.
     */
    @GetMapping("/api-permissions")
    public List<ApiPermission> getAllApiPermissions() {
        log.debug("REST request to get all ApiPermissions");
        return apiPermissionService.findAll();
    }

    /**
     * {@code GET  /api-permissions/:id} : get the "id" apiPermission.
     *
     * @param id the id of the apiPermission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiPermission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-permissions/{id}")
    public ResponseEntity<ApiPermission> getApiPermission(@PathVariable Long id) {
        log.debug("REST request to get ApiPermission : {}", id);
        Optional<ApiPermission> apiPermission = apiPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiPermission);
    }

    /**
     * {@code DELETE  /api-permissions/:id} : delete the "id" apiPermission.
     *
     * @param id the id of the apiPermission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-permissions/{id}")
    public ResponseEntity<Void> deleteApiPermission(@PathVariable Long id) {
        log.debug("REST request to delete ApiPermission : {}", id);
        apiPermissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
