package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiResource;
import io.github.jhipster.application.repository.ApiResourceRepository;
import io.github.jhipster.application.service.ApiResourceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ApiResourceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiResourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiResourceRepository apiResourceRepository;

    @Autowired
    private ApiResourceService apiResourceService;

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

    private MockMvc restApiResourceMockMvc;

    private ApiResource apiResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiResourceResource apiResourceResource = new ApiResourceResource(apiResourceService);
        this.restApiResourceMockMvc = MockMvcBuilders.standaloneSetup(apiResourceResource)
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
    public static ApiResource createEntity(EntityManager em) {
        ApiResource apiResource = new ApiResource()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return apiResource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiResource createUpdatedEntity(EntityManager em) {
        ApiResource apiResource = new ApiResource()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return apiResource;
    }

    @BeforeEach
    public void initTest() {
        apiResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiResource() throws Exception {
        int databaseSizeBeforeCreate = apiResourceRepository.findAll().size();

        // Create the ApiResource
        restApiResourceMockMvc.perform(post("/api/api-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiResource)))
            .andExpect(status().isCreated());

        // Validate the ApiResource in the database
        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeCreate + 1);
        ApiResource testApiResource = apiResourceList.get(apiResourceList.size() - 1);
        assertThat(testApiResource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiResource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiResourceRepository.findAll().size();

        // Create the ApiResource with an existing ID
        apiResource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiResourceMockMvc.perform(post("/api/api-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiResource)))
            .andExpect(status().isBadRequest());

        // Validate the ApiResource in the database
        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiResourceRepository.findAll().size();
        // set the field null
        apiResource.setName(null);

        // Create the ApiResource, which fails.

        restApiResourceMockMvc.perform(post("/api/api-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiResource)))
            .andExpect(status().isBadRequest());

        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiResources() throws Exception {
        // Initialize the database
        apiResourceRepository.saveAndFlush(apiResource);

        // Get all the apiResourceList
        restApiResourceMockMvc.perform(get("/api/api-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getApiResource() throws Exception {
        // Initialize the database
        apiResourceRepository.saveAndFlush(apiResource);

        // Get the apiResource
        restApiResourceMockMvc.perform(get("/api/api-resources/{id}", apiResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiResource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiResource() throws Exception {
        // Get the apiResource
        restApiResourceMockMvc.perform(get("/api/api-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiResource() throws Exception {
        // Initialize the database
        apiResourceService.save(apiResource);

        int databaseSizeBeforeUpdate = apiResourceRepository.findAll().size();

        // Update the apiResource
        ApiResource updatedApiResource = apiResourceRepository.findById(apiResource.getId()).get();
        // Disconnect from session so that the updates on updatedApiResource are not directly saved in db
        em.detach(updatedApiResource);
        updatedApiResource
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restApiResourceMockMvc.perform(put("/api/api-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiResource)))
            .andExpect(status().isOk());

        // Validate the ApiResource in the database
        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeUpdate);
        ApiResource testApiResource = apiResourceList.get(apiResourceList.size() - 1);
        assertThat(testApiResource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiResource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiResource() throws Exception {
        int databaseSizeBeforeUpdate = apiResourceRepository.findAll().size();

        // Create the ApiResource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiResourceMockMvc.perform(put("/api/api-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiResource)))
            .andExpect(status().isBadRequest());

        // Validate the ApiResource in the database
        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiResource() throws Exception {
        // Initialize the database
        apiResourceService.save(apiResource);

        int databaseSizeBeforeDelete = apiResourceRepository.findAll().size();

        // Delete the apiResource
        restApiResourceMockMvc.perform(delete("/api/api-resources/{id}", apiResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiResource> apiResourceList = apiResourceRepository.findAll();
        assertThat(apiResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiResource.class);
        ApiResource apiResource1 = new ApiResource();
        apiResource1.setId(1L);
        ApiResource apiResource2 = new ApiResource();
        apiResource2.setId(apiResource1.getId());
        assertThat(apiResource1).isEqualTo(apiResource2);
        apiResource2.setId(2L);
        assertThat(apiResource1).isNotEqualTo(apiResource2);
        apiResource1.setId(null);
        assertThat(apiResource1).isNotEqualTo(apiResource2);
    }
}
