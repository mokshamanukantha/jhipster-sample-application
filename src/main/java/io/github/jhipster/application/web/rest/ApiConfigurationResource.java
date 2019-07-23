package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ApiConfiguration;
import io.github.jhipster.application.service.ApiConfigurationService;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ApiConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class ApiConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationResource.class);

    private static final String ENTITY_NAME = "apiConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiConfigurationService apiConfigurationService;

    public ApiConfigurationResource(ApiConfigurationService apiConfigurationService) {
        this.apiConfigurationService = apiConfigurationService;
    }

    /**
     * {@code POST  /api-configurations} : Create a new apiConfiguration.
     *
     * @param apiConfiguration the apiConfiguration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiConfiguration, or with status {@code 400 (Bad Request)} if the apiConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-configurations")
    public ResponseEntity<ApiConfiguration> createApiConfiguration(@RequestBody ApiConfiguration apiConfiguration) throws URISyntaxException {
        log.debug("REST request to save ApiConfiguration : {}", apiConfiguration);
        if (apiConfiguration.getId() != null) {
            throw new BadRequestAlertException("A new apiConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiConfiguration result = apiConfigurationService.save(apiConfiguration);
        return ResponseEntity.created(new URI("/api/api-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-configurations} : Updates an existing apiConfiguration.
     *
     * @param apiConfiguration the apiConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiConfiguration,
     * or with status {@code 400 (Bad Request)} if the apiConfiguration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-configurations")
    public ResponseEntity<ApiConfiguration> updateApiConfiguration(@RequestBody ApiConfiguration apiConfiguration) throws URISyntaxException {
        log.debug("REST request to update ApiConfiguration : {}", apiConfiguration);
        if (apiConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiConfiguration result = apiConfigurationService.save(apiConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-configurations} : get all the apiConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiConfigurations in body.
     */
    @GetMapping("/api-configurations")
    public List<ApiConfiguration> getAllApiConfigurations() {
        log.debug("REST request to get all ApiConfigurations");
        return apiConfigurationService.findAll();
    }

    /**
     * {@code GET  /api-configurations/:id} : get the "id" apiConfiguration.
     *
     * @param id the id of the apiConfiguration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiConfiguration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-configurations/{id}")
    public ResponseEntity<ApiConfiguration> getApiConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ApiConfiguration : {}", id);
        Optional<ApiConfiguration> apiConfiguration = apiConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiConfiguration);
    }

    /**
     * {@code DELETE  /api-configurations/:id} : delete the "id" apiConfiguration.
     *
     * @param id the id of the apiConfiguration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-configurations/{id}")
    public ResponseEntity<Void> deleteApiConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ApiConfiguration : {}", id);
        apiConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
