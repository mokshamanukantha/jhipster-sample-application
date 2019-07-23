package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiService;
import io.github.jhipster.application.repository.ApiServiceRepository;
import io.github.jhipster.application.service.ApiServiceService;
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

import io.github.jhipster.application.domain.enumeration.AuthState;
import io.github.jhipster.application.domain.enumeration.AuthState;
/**
 * Integration tests for the {@Link ApiServiceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AuthState DEFAULT_AUTHENTICATION_STATE = AuthState.ENABLED;
    private static final AuthState UPDATED_AUTHENTICATION_STATE = AuthState.DISABLED;

    private static final AuthState DEFAULT_AUTHORIZATION_STATE = AuthState.ENABLED;
    private static final AuthState UPDATED_AUTHORIZATION_STATE = AuthState.DISABLED;

    @Autowired
    private ApiServiceRepository apiServiceRepository;

    @Autowired
    private ApiServiceService apiServiceService;

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

    private MockMvc restApiServiceMockMvc;

    private ApiService apiService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiServiceResource apiServiceResource = new ApiServiceResource(apiServiceService);
        this.restApiServiceMockMvc = MockMvcBuilders.standaloneSetup(apiServiceResource)
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
    public static ApiService createEntity(EntityManager em) {
        ApiService apiService = new ApiService()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .authenticationState(DEFAULT_AUTHENTICATION_STATE)
            .authorizationState(DEFAULT_AUTHORIZATION_STATE);
        return apiService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiService createUpdatedEntity(EntityManager em) {
        ApiService apiService = new ApiService()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .authenticationState(UPDATED_AUTHENTICATION_STATE)
            .authorizationState(UPDATED_AUTHORIZATION_STATE);
        return apiService;
    }

    @BeforeEach
    public void initTest() {
        apiService = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiService() throws Exception {
        int databaseSizeBeforeCreate = apiServiceRepository.findAll().size();

        // Create the ApiService
        restApiServiceMockMvc.perform(post("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiService)))
            .andExpect(status().isCreated());

        // Validate the ApiService in the database
        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeCreate + 1);
        ApiService testApiService = apiServiceList.get(apiServiceList.size() - 1);
        assertThat(testApiService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiService.getAuthenticationState()).isEqualTo(DEFAULT_AUTHENTICATION_STATE);
        assertThat(testApiService.getAuthorizationState()).isEqualTo(DEFAULT_AUTHORIZATION_STATE);
    }

    @Test
    @Transactional
    public void createApiServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiServiceRepository.findAll().size();

        // Create the ApiService with an existing ID
        apiService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiServiceMockMvc.perform(post("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiService)))
            .andExpect(status().isBadRequest());

        // Validate the ApiService in the database
        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceRepository.findAll().size();
        // set the field null
        apiService.setName(null);

        // Create the ApiService, which fails.

        restApiServiceMockMvc.perform(post("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiService)))
            .andExpect(status().isBadRequest());

        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceRepository.findAll().size();
        // set the field null
        apiService.setDescription(null);

        // Create the ApiService, which fails.

        restApiServiceMockMvc.perform(post("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiService)))
            .andExpect(status().isBadRequest());

        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiServices() throws Exception {
        // Initialize the database
        apiServiceRepository.saveAndFlush(apiService);

        // Get all the apiServiceList
        restApiServiceMockMvc.perform(get("/api/api-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].authenticationState").value(hasItem(DEFAULT_AUTHENTICATION_STATE.toString())))
            .andExpect(jsonPath("$.[*].authorizationState").value(hasItem(DEFAULT_AUTHORIZATION_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApiService() throws Exception {
        // Initialize the database
        apiServiceRepository.saveAndFlush(apiService);

        // Get the apiService
        restApiServiceMockMvc.perform(get("/api/api-services/{id}", apiService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.authenticationState").value(DEFAULT_AUTHENTICATION_STATE.toString()))
            .andExpect(jsonPath("$.authorizationState").value(DEFAULT_AUTHORIZATION_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiService() throws Exception {
        // Get the apiService
        restApiServiceMockMvc.perform(get("/api/api-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiService() throws Exception {
        // Initialize the database
        apiServiceService.save(apiService);

        int databaseSizeBeforeUpdate = apiServiceRepository.findAll().size();

        // Update the apiService
        ApiService updatedApiService = apiServiceRepository.findById(apiService.getId()).get();
        // Disconnect from session so that the updates on updatedApiService are not directly saved in db
        em.detach(updatedApiService);
        updatedApiService
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .authenticationState(UPDATED_AUTHENTICATION_STATE)
            .authorizationState(UPDATED_AUTHORIZATION_STATE);

        restApiServiceMockMvc.perform(put("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiService)))
            .andExpect(status().isOk());

        // Validate the ApiService in the database
        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeUpdate);
        ApiService testApiService = apiServiceList.get(apiServiceList.size() - 1);
        assertThat(testApiService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiService.getAuthenticationState()).isEqualTo(UPDATED_AUTHENTICATION_STATE);
        assertThat(testApiService.getAuthorizationState()).isEqualTo(UPDATED_AUTHORIZATION_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApiService() throws Exception {
        int databaseSizeBeforeUpdate = apiServiceRepository.findAll().size();

        // Create the ApiService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiServiceMockMvc.perform(put("/api/api-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiService)))
            .andExpect(status().isBadRequest());

        // Validate the ApiService in the database
        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiService() throws Exception {
        // Initialize the database
        apiServiceService.save(apiService);

        int databaseSizeBeforeDelete = apiServiceRepository.findAll().size();

        // Delete the apiService
        restApiServiceMockMvc.perform(delete("/api/api-services/{id}", apiService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiService> apiServiceList = apiServiceRepository.findAll();
        assertThat(apiServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiService.class);
        ApiService apiService1 = new ApiService();
        apiService1.setId(1L);
        ApiService apiService2 = new ApiService();
        apiService2.setId(apiService1.getId());
        assertThat(apiService1).isEqualTo(apiService2);
        apiService2.setId(2L);
        assertThat(apiService1).isNotEqualTo(apiService2);
        apiService1.setId(null);
        assertThat(apiService1).isNotEqualTo(apiService2);
    }
}
