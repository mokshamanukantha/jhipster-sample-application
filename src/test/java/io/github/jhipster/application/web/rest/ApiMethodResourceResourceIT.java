package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiMethodResource;
import io.github.jhipster.application.repository.ApiMethodResourceRepository;
import io.github.jhipster.application.service.ApiMethodResourceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ApiMethodResourceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiMethodResourceResourceIT {

    private static final String DEFAULT_RESTRICTED_INPUT_PATHS = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTED_INPUT_PATHS = "BBBBBBBBBB";

    private static final String DEFAULT_RESTRICTED_OUTPUT_PATHS = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTED_OUTPUT_PATHS = "BBBBBBBBBB";

    private static final String DEFAULT_MASK = "AAAAAAAAAA";
    private static final String UPDATED_MASK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    @Autowired
    private ApiMethodResourceRepository apiMethodResourceRepository;

    @Mock
    private ApiMethodResourceRepository apiMethodResourceRepositoryMock;

    @Mock
    private ApiMethodResourceService apiMethodResourceServiceMock;

    @Autowired
    private ApiMethodResourceService apiMethodResourceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restApiMethodResourceMockMvc;

    private ApiMethodResource apiMethodResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiMethodResourceResource apiMethodResourceResource = new ApiMethodResourceResource(apiMethodResourceService);
        this.restApiMethodResourceMockMvc = MockMvcBuilders.standaloneSetup(apiMethodResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiMethodResource createEntity(EntityManager em) {
        ApiMethodResource apiMethodResource = new ApiMethodResource()
            .restrictedInputPaths(DEFAULT_RESTRICTED_INPUT_PATHS)
            .restrictedOutputPaths(DEFAULT_RESTRICTED_OUTPUT_PATHS)
            .mask(DEFAULT_MASK)
            .enable(DEFAULT_ENABLE);
        return apiMethodResource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiMethodResource createUpdatedEntity(EntityManager em) {
        ApiMethodResource apiMethodResource = new ApiMethodResource()
            .restrictedInputPaths(UPDATED_RESTRICTED_INPUT_PATHS)
            .restrictedOutputPaths(UPDATED_RESTRICTED_OUTPUT_PATHS)
            .mask(UPDATED_MASK)
            .enable(UPDATED_ENABLE);
        return apiMethodResource;
    }

    @BeforeEach
    public void initTest() {
        apiMethodResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiMethodResource() throws Exception {
        int databaseSizeBeforeCreate = apiMethodResourceRepository.findAll().size();

        // Create the ApiMethodResource
        restApiMethodResourceMockMvc.perform(post("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiMethodResource)))
            .andExpect(status().isCreated());

        // Validate the ApiMethodResource in the database
        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeCreate + 1);
        ApiMethodResource testApiMethodResource = apiMethodResourceList.get(apiMethodResourceList.size() - 1);
        assertThat(testApiMethodResource.getRestrictedInputPaths()).isEqualTo(DEFAULT_RESTRICTED_INPUT_PATHS);
        assertThat(testApiMethodResource.getRestrictedOutputPaths()).isEqualTo(DEFAULT_RESTRICTED_OUTPUT_PATHS);
        assertThat(testApiMethodResource.getMask()).isEqualTo(DEFAULT_MASK);
        assertThat(testApiMethodResource.isEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    public void createApiMethodResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiMethodResourceRepository.findAll().size();

        // Create the ApiMethodResource with an existing ID
        apiMethodResource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiMethodResourceMockMvc.perform(post("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiMethodResource)))
            .andExpect(status().isBadRequest());

        // Validate the ApiMethodResource in the database
        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMethodResourceRepository.findAll().size();
        // set the field null
        apiMethodResource.setMask(null);

        // Create the ApiMethodResource, which fails.

        restApiMethodResourceMockMvc.perform(post("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiMethodResource)))
            .andExpect(status().isBadRequest());

        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiMethodResourceRepository.findAll().size();
        // set the field null
        apiMethodResource.setEnable(null);

        // Create the ApiMethodResource, which fails.

        restApiMethodResourceMockMvc.perform(post("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiMethodResource)))
            .andExpect(status().isBadRequest());

        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiMethodResources() throws Exception {
        // Initialize the database
        apiMethodResourceRepository.saveAndFlush(apiMethodResource);

        // Get all the apiMethodResourceList
        restApiMethodResourceMockMvc.perform(get("/api/api-method-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiMethodResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].restrictedInputPaths").value(hasItem(DEFAULT_RESTRICTED_INPUT_PATHS.toString())))
            .andExpect(jsonPath("$.[*].restrictedOutputPaths").value(hasItem(DEFAULT_RESTRICTED_OUTPUT_PATHS.toString())))
            .andExpect(jsonPath("$.[*].mask").value(hasItem(DEFAULT_MASK.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllApiMethodResourcesWithEagerRelationshipsIsEnabled() throws Exception {
        ApiMethodResourceResource apiMethodResourceResource = new ApiMethodResourceResource(apiMethodResourceServiceMock);
        when(apiMethodResourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restApiMethodResourceMockMvc = MockMvcBuilders.standaloneSetup(apiMethodResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiMethodResourceMockMvc.perform(get("/api/api-method-resources?eagerload=true"))
        .andExpect(status().isOk());

        verify(apiMethodResourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllApiMethodResourcesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ApiMethodResourceResource apiMethodResourceResource = new ApiMethodResourceResource(apiMethodResourceServiceMock);
            when(apiMethodResourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restApiMethodResourceMockMvc = MockMvcBuilders.standaloneSetup(apiMethodResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiMethodResourceMockMvc.perform(get("/api/api-method-resources?eagerload=true"))
        .andExpect(status().isOk());

            verify(apiMethodResourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getApiMethodResource() throws Exception {
        // Initialize the database
        apiMethodResourceRepository.saveAndFlush(apiMethodResource);

        // Get the apiMethodResource
        restApiMethodResourceMockMvc.perform(get("/api/api-method-resources/{id}", apiMethodResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiMethodResource.getId().intValue()))
            .andExpect(jsonPath("$.restrictedInputPaths").value(DEFAULT_RESTRICTED_INPUT_PATHS.toString()))
            .andExpect(jsonPath("$.restrictedOutputPaths").value(DEFAULT_RESTRICTED_OUTPUT_PATHS.toString()))
            .andExpect(jsonPath("$.mask").value(DEFAULT_MASK.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApiMethodResource() throws Exception {
        // Get the apiMethodResource
        restApiMethodResourceMockMvc.perform(get("/api/api-method-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiMethodResource() throws Exception {
        // Initialize the database
        apiMethodResourceService.save(apiMethodResource);

        int databaseSizeBeforeUpdate = apiMethodResourceRepository.findAll().size();

        // Update the apiMethodResource
        ApiMethodResource updatedApiMethodResource = apiMethodResourceRepository.findById(apiMethodResource.getId()).get();
        // Disconnect from session so that the updates on updatedApiMethodResource are not directly saved in db
        em.detach(updatedApiMethodResource);
        updatedApiMethodResource
            .restrictedInputPaths(UPDATED_RESTRICTED_INPUT_PATHS)
            .restrictedOutputPaths(UPDATED_RESTRICTED_OUTPUT_PATHS)
            .mask(UPDATED_MASK)
            .enable(UPDATED_ENABLE);

        restApiMethodResourceMockMvc.perform(put("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiMethodResource)))
            .andExpect(status().isOk());

        // Validate the ApiMethodResource in the database
        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeUpdate);
        ApiMethodResource testApiMethodResource = apiMethodResourceList.get(apiMethodResourceList.size() - 1);
        assertThat(testApiMethodResource.getRestrictedInputPaths()).isEqualTo(UPDATED_RESTRICTED_INPUT_PATHS);
        assertThat(testApiMethodResource.getRestrictedOutputPaths()).isEqualTo(UPDATED_RESTRICTED_OUTPUT_PATHS);
        assertThat(testApiMethodResource.getMask()).isEqualTo(UPDATED_MASK);
        assertThat(testApiMethodResource.isEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingApiMethodResource() throws Exception {
        int databaseSizeBeforeUpdate = apiMethodResourceRepository.findAll().size();

        // Create the ApiMethodResource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiMethodResourceMockMvc.perform(put("/api/api-method-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiMethodResource)))
            .andExpect(status().isBadRequest());

        // Validate the ApiMethodResource in the database
        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiMethodResource() throws Exception {
        // Initialize the database
        apiMethodResourceService.save(apiMethodResource);

        int databaseSizeBeforeDelete = apiMethodResourceRepository.findAll().size();

        // Delete the apiMethodResource
        restApiMethodResourceMockMvc.perform(delete("/api/api-method-resources/{id}", apiMethodResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiMethodResource> apiMethodResourceList = apiMethodResourceRepository.findAll();
        assertThat(apiMethodResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiMethodResource.class);
        ApiMethodResource apiMethodResource1 = new ApiMethodResource();
        apiMethodResource1.setId(1L);
        ApiMethodResource apiMethodResource2 = new ApiMethodResource();
        apiMethodResource2.setId(apiMethodResource1.getId());
        assertThat(apiMethodResource1).isEqualTo(apiMethodResource2);
        apiMethodResource2.setId(2L);
        assertThat(apiMethodResource1).isNotEqualTo(apiMethodResource2);
        apiMethodResource1.setId(null);
        assertThat(apiMethodResource1).isNotEqualTo(apiMethodResource2);
    }
}
