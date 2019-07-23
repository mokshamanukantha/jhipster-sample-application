package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiClient;
import io.github.jhipster.application.repository.ApiClientRepository;
import io.github.jhipster.application.service.ApiClientService;
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
 * Integration tests for the {@Link ApiClientResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiClientResourceIT {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COOKIE_ENABLE = false;
    private static final Boolean UPDATED_COOKIE_ENABLE = true;

    private static final Long DEFAULT_COOKIE_TTL = 1L;
    private static final Long UPDATED_COOKIE_TTL = 2L;

    private static final String DEFAULT_CLIENT_CALLBACK = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_CALLBACK = "BBBBBBBBBB";

    @Autowired
    private ApiClientRepository apiClientRepository;

    @Autowired
    private ApiClientService apiClientService;

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

    private MockMvc restApiClientMockMvc;

    private ApiClient apiClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiClientResource apiClientResource = new ApiClientResource(apiClientService);
        this.restApiClientMockMvc = MockMvcBuilders.standaloneSetup(apiClientResource)
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
    public static ApiClient createEntity(EntityManager em) {
        ApiClient apiClient = new ApiClient()
            .clientId(DEFAULT_CLIENT_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .cookieEnable(DEFAULT_COOKIE_ENABLE)
            .cookieTTL(DEFAULT_COOKIE_TTL)
            .clientCallback(DEFAULT_CLIENT_CALLBACK);
        return apiClient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiClient createUpdatedEntity(EntityManager em) {
        ApiClient apiClient = new ApiClient()
            .clientId(UPDATED_CLIENT_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cookieEnable(UPDATED_COOKIE_ENABLE)
            .cookieTTL(UPDATED_COOKIE_TTL)
            .clientCallback(UPDATED_CLIENT_CALLBACK);
        return apiClient;
    }

    @BeforeEach
    public void initTest() {
        apiClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiClient() throws Exception {
        int databaseSizeBeforeCreate = apiClientRepository.findAll().size();

        // Create the ApiClient
        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isCreated());

        // Validate the ApiClient in the database
        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeCreate + 1);
        ApiClient testApiClient = apiClientList.get(apiClientList.size() - 1);
        assertThat(testApiClient.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testApiClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiClient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiClient.isCookieEnable()).isEqualTo(DEFAULT_COOKIE_ENABLE);
        assertThat(testApiClient.getCookieTTL()).isEqualTo(DEFAULT_COOKIE_TTL);
        assertThat(testApiClient.getClientCallback()).isEqualTo(DEFAULT_CLIENT_CALLBACK);
    }

    @Test
    @Transactional
    public void createApiClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiClientRepository.findAll().size();

        // Create the ApiClient with an existing ID
        apiClient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        // Validate the ApiClient in the database
        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiClientRepository.findAll().size();
        // set the field null
        apiClient.setName(null);

        // Create the ApiClient, which fails.

        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiClientRepository.findAll().size();
        // set the field null
        apiClient.setDescription(null);

        // Create the ApiClient, which fails.

        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookieEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiClientRepository.findAll().size();
        // set the field null
        apiClient.setCookieEnable(null);

        // Create the ApiClient, which fails.

        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookieTTLIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiClientRepository.findAll().size();
        // set the field null
        apiClient.setCookieTTL(null);

        // Create the ApiClient, which fails.

        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientCallbackIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiClientRepository.findAll().size();
        // set the field null
        apiClient.setClientCallback(null);

        // Create the ApiClient, which fails.

        restApiClientMockMvc.perform(post("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiClients() throws Exception {
        // Initialize the database
        apiClientRepository.saveAndFlush(apiClient);

        // Get all the apiClientList
        restApiClientMockMvc.perform(get("/api/api-clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cookieEnable").value(hasItem(DEFAULT_COOKIE_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].cookieTTL").value(hasItem(DEFAULT_COOKIE_TTL.intValue())))
            .andExpect(jsonPath("$.[*].clientCallback").value(hasItem(DEFAULT_CLIENT_CALLBACK.toString())));
    }
    
    @Test
    @Transactional
    public void getApiClient() throws Exception {
        // Initialize the database
        apiClientRepository.saveAndFlush(apiClient);

        // Get the apiClient
        restApiClientMockMvc.perform(get("/api/api-clients/{id}", apiClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiClient.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cookieEnable").value(DEFAULT_COOKIE_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.cookieTTL").value(DEFAULT_COOKIE_TTL.intValue()))
            .andExpect(jsonPath("$.clientCallback").value(DEFAULT_CLIENT_CALLBACK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiClient() throws Exception {
        // Get the apiClient
        restApiClientMockMvc.perform(get("/api/api-clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiClient() throws Exception {
        // Initialize the database
        apiClientService.save(apiClient);

        int databaseSizeBeforeUpdate = apiClientRepository.findAll().size();

        // Update the apiClient
        ApiClient updatedApiClient = apiClientRepository.findById(apiClient.getId()).get();
        // Disconnect from session so that the updates on updatedApiClient are not directly saved in db
        em.detach(updatedApiClient);
        updatedApiClient
            .clientId(UPDATED_CLIENT_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cookieEnable(UPDATED_COOKIE_ENABLE)
            .cookieTTL(UPDATED_COOKIE_TTL)
            .clientCallback(UPDATED_CLIENT_CALLBACK);

        restApiClientMockMvc.perform(put("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiClient)))
            .andExpect(status().isOk());

        // Validate the ApiClient in the database
        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeUpdate);
        ApiClient testApiClient = apiClientList.get(apiClientList.size() - 1);
        assertThat(testApiClient.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testApiClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiClient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiClient.isCookieEnable()).isEqualTo(UPDATED_COOKIE_ENABLE);
        assertThat(testApiClient.getCookieTTL()).isEqualTo(UPDATED_COOKIE_TTL);
        assertThat(testApiClient.getClientCallback()).isEqualTo(UPDATED_CLIENT_CALLBACK);
    }

    @Test
    @Transactional
    public void updateNonExistingApiClient() throws Exception {
        int databaseSizeBeforeUpdate = apiClientRepository.findAll().size();

        // Create the ApiClient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiClientMockMvc.perform(put("/api/api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiClient)))
            .andExpect(status().isBadRequest());

        // Validate the ApiClient in the database
        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiClient() throws Exception {
        // Initialize the database
        apiClientService.save(apiClient);

        int databaseSizeBeforeDelete = apiClientRepository.findAll().size();

        // Delete the apiClient
        restApiClientMockMvc.perform(delete("/api/api-clients/{id}", apiClient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiClient> apiClientList = apiClientRepository.findAll();
        assertThat(apiClientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiClient.class);
        ApiClient apiClient1 = new ApiClient();
        apiClient1.setId(1L);
        ApiClient apiClient2 = new ApiClient();
        apiClient2.setId(apiClient1.getId());
        assertThat(apiClient1).isEqualTo(apiClient2);
        apiClient2.setId(2L);
        assertThat(apiClient1).isNotEqualTo(apiClient2);
        apiClient1.setId(null);
        assertThat(apiClient1).isNotEqualTo(apiClient2);
    }
}
