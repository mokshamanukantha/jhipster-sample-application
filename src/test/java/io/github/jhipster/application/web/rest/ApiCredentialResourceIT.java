package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiCredential;
import io.github.jhipster.application.repository.ApiCredentialRepository;
import io.github.jhipster.application.service.ApiCredentialService;
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
 * Integration tests for the {@Link ApiCredentialResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiCredentialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IDP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_DSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_IDP_DSCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUER = "AAAAAAAAAA";
    private static final String UPDATED_ISSUER = "BBBBBBBBBB";

    private static final String DEFAULT_SCOPE = "AAAAAAAAAA";
    private static final String UPDATED_SCOPE = "BBBBBBBBBB";

    private static final String DEFAULT_CALLBACK_URL = "AAAAAAAAAA";
    private static final String UPDATED_CALLBACK_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_CERT_CACHE_TTL = 1L;
    private static final Long UPDATED_CERT_CACHE_TTL = 2L;

    private static final Long DEFAULT_TOKEN_TTTL = 1L;
    private static final Long UPDATED_TOKEN_TTTL = 2L;

    private static final String DEFAULT_IDP_URL = "AAAAAAAAAA";
    private static final String UPDATED_IDP_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_VALIDATE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IDP_VALIDATE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_USER_INFO_URL = "AAAAAAAAAA";
    private static final String UPDATED_IDP_USER_INFO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IDP_LOGOUT_URL = "AAAAAAAAAA";
    private static final String UPDATED_IDP_LOGOUT_URL = "BBBBBBBBBB";

    @Autowired
    private ApiCredentialRepository apiCredentialRepository;

    @Autowired
    private ApiCredentialService apiCredentialService;

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

    private MockMvc restApiCredentialMockMvc;

    private ApiCredential apiCredential;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiCredentialResource apiCredentialResource = new ApiCredentialResource(apiCredentialService);
        this.restApiCredentialMockMvc = MockMvcBuilders.standaloneSetup(apiCredentialResource)
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
    public static ApiCredential createEntity(EntityManager em) {
        ApiCredential apiCredential = new ApiCredential()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .idpName(DEFAULT_IDP_NAME)
            .idpDscription(DEFAULT_IDP_DSCRIPTION)
            .entityId(DEFAULT_ENTITY_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .clientSecret(DEFAULT_CLIENT_SECRET)
            .issuer(DEFAULT_ISSUER)
            .scope(DEFAULT_SCOPE)
            .callbackUrl(DEFAULT_CALLBACK_URL)
            .certCacheTTL(DEFAULT_CERT_CACHE_TTL)
            .tokenTTTL(DEFAULT_TOKEN_TTTL)
            .idpUrl(DEFAULT_IDP_URL)
            .idpValidateUrl(DEFAULT_IDP_VALIDATE_URL)
            .idpUserInfoUrl(DEFAULT_IDP_USER_INFO_URL)
            .idpLogoutUrl(DEFAULT_IDP_LOGOUT_URL);
        return apiCredential;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiCredential createUpdatedEntity(EntityManager em) {
        ApiCredential apiCredential = new ApiCredential()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .idpName(UPDATED_IDP_NAME)
            .idpDscription(UPDATED_IDP_DSCRIPTION)
            .entityId(UPDATED_ENTITY_ID)
            .clientId(UPDATED_CLIENT_ID)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .issuer(UPDATED_ISSUER)
            .scope(UPDATED_SCOPE)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .certCacheTTL(UPDATED_CERT_CACHE_TTL)
            .tokenTTTL(UPDATED_TOKEN_TTTL)
            .idpUrl(UPDATED_IDP_URL)
            .idpValidateUrl(UPDATED_IDP_VALIDATE_URL)
            .idpUserInfoUrl(UPDATED_IDP_USER_INFO_URL)
            .idpLogoutUrl(UPDATED_IDP_LOGOUT_URL);
        return apiCredential;
    }

    @BeforeEach
    public void initTest() {
        apiCredential = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiCredential() throws Exception {
        int databaseSizeBeforeCreate = apiCredentialRepository.findAll().size();

        // Create the ApiCredential
        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isCreated());

        // Validate the ApiCredential in the database
        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeCreate + 1);
        ApiCredential testApiCredential = apiCredentialList.get(apiCredentialList.size() - 1);
        assertThat(testApiCredential.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiCredential.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiCredential.getIdpName()).isEqualTo(DEFAULT_IDP_NAME);
        assertThat(testApiCredential.getIdpDscription()).isEqualTo(DEFAULT_IDP_DSCRIPTION);
        assertThat(testApiCredential.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testApiCredential.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testApiCredential.getClientSecret()).isEqualTo(DEFAULT_CLIENT_SECRET);
        assertThat(testApiCredential.getIssuer()).isEqualTo(DEFAULT_ISSUER);
        assertThat(testApiCredential.getScope()).isEqualTo(DEFAULT_SCOPE);
        assertThat(testApiCredential.getCallbackUrl()).isEqualTo(DEFAULT_CALLBACK_URL);
        assertThat(testApiCredential.getCertCacheTTL()).isEqualTo(DEFAULT_CERT_CACHE_TTL);
        assertThat(testApiCredential.getTokenTTTL()).isEqualTo(DEFAULT_TOKEN_TTTL);
        assertThat(testApiCredential.getIdpUrl()).isEqualTo(DEFAULT_IDP_URL);
        assertThat(testApiCredential.getIdpValidateUrl()).isEqualTo(DEFAULT_IDP_VALIDATE_URL);
        assertThat(testApiCredential.getIdpUserInfoUrl()).isEqualTo(DEFAULT_IDP_USER_INFO_URL);
        assertThat(testApiCredential.getIdpLogoutUrl()).isEqualTo(DEFAULT_IDP_LOGOUT_URL);
    }

    @Test
    @Transactional
    public void createApiCredentialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiCredentialRepository.findAll().size();

        // Create the ApiCredential with an existing ID
        apiCredential.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        // Validate the ApiCredential in the database
        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setName(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setDescription(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpName(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpDscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpDscription(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setEntityId(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setClientId(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setClientSecret(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuerIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIssuer(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setScope(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCallbackUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setCallbackUrl(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertCacheTTLIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setCertCacheTTL(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenTTTLIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setTokenTTTL(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpUrl(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpValidateUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpValidateUrl(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpUserInfoUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpUserInfoUrl(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdpLogoutUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiCredentialRepository.findAll().size();
        // set the field null
        apiCredential.setIdpLogoutUrl(null);

        // Create the ApiCredential, which fails.

        restApiCredentialMockMvc.perform(post("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiCredentials() throws Exception {
        // Initialize the database
        apiCredentialRepository.saveAndFlush(apiCredential);

        // Get all the apiCredentialList
        restApiCredentialMockMvc.perform(get("/api/api-credentials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiCredential.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].idpName").value(hasItem(DEFAULT_IDP_NAME.toString())))
            .andExpect(jsonPath("$.[*].idpDscription").value(hasItem(DEFAULT_IDP_DSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].clientSecret").value(hasItem(DEFAULT_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].issuer").value(hasItem(DEFAULT_ISSUER.toString())))
            .andExpect(jsonPath("$.[*].scope").value(hasItem(DEFAULT_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].callbackUrl").value(hasItem(DEFAULT_CALLBACK_URL.toString())))
            .andExpect(jsonPath("$.[*].certCacheTTL").value(hasItem(DEFAULT_CERT_CACHE_TTL.intValue())))
            .andExpect(jsonPath("$.[*].tokenTTTL").value(hasItem(DEFAULT_TOKEN_TTTL.intValue())))
            .andExpect(jsonPath("$.[*].idpUrl").value(hasItem(DEFAULT_IDP_URL.toString())))
            .andExpect(jsonPath("$.[*].idpValidateUrl").value(hasItem(DEFAULT_IDP_VALIDATE_URL.toString())))
            .andExpect(jsonPath("$.[*].idpUserInfoUrl").value(hasItem(DEFAULT_IDP_USER_INFO_URL.toString())))
            .andExpect(jsonPath("$.[*].idpLogoutUrl").value(hasItem(DEFAULT_IDP_LOGOUT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getApiCredential() throws Exception {
        // Initialize the database
        apiCredentialRepository.saveAndFlush(apiCredential);

        // Get the apiCredential
        restApiCredentialMockMvc.perform(get("/api/api-credentials/{id}", apiCredential.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiCredential.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.idpName").value(DEFAULT_IDP_NAME.toString()))
            .andExpect(jsonPath("$.idpDscription").value(DEFAULT_IDP_DSCRIPTION.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.clientSecret").value(DEFAULT_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.issuer").value(DEFAULT_ISSUER.toString()))
            .andExpect(jsonPath("$.scope").value(DEFAULT_SCOPE.toString()))
            .andExpect(jsonPath("$.callbackUrl").value(DEFAULT_CALLBACK_URL.toString()))
            .andExpect(jsonPath("$.certCacheTTL").value(DEFAULT_CERT_CACHE_TTL.intValue()))
            .andExpect(jsonPath("$.tokenTTTL").value(DEFAULT_TOKEN_TTTL.intValue()))
            .andExpect(jsonPath("$.idpUrl").value(DEFAULT_IDP_URL.toString()))
            .andExpect(jsonPath("$.idpValidateUrl").value(DEFAULT_IDP_VALIDATE_URL.toString()))
            .andExpect(jsonPath("$.idpUserInfoUrl").value(DEFAULT_IDP_USER_INFO_URL.toString()))
            .andExpect(jsonPath("$.idpLogoutUrl").value(DEFAULT_IDP_LOGOUT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiCredential() throws Exception {
        // Get the apiCredential
        restApiCredentialMockMvc.perform(get("/api/api-credentials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiCredential() throws Exception {
        // Initialize the database
        apiCredentialService.save(apiCredential);

        int databaseSizeBeforeUpdate = apiCredentialRepository.findAll().size();

        // Update the apiCredential
        ApiCredential updatedApiCredential = apiCredentialRepository.findById(apiCredential.getId()).get();
        // Disconnect from session so that the updates on updatedApiCredential are not directly saved in db
        em.detach(updatedApiCredential);
        updatedApiCredential
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .idpName(UPDATED_IDP_NAME)
            .idpDscription(UPDATED_IDP_DSCRIPTION)
            .entityId(UPDATED_ENTITY_ID)
            .clientId(UPDATED_CLIENT_ID)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .issuer(UPDATED_ISSUER)
            .scope(UPDATED_SCOPE)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .certCacheTTL(UPDATED_CERT_CACHE_TTL)
            .tokenTTTL(UPDATED_TOKEN_TTTL)
            .idpUrl(UPDATED_IDP_URL)
            .idpValidateUrl(UPDATED_IDP_VALIDATE_URL)
            .idpUserInfoUrl(UPDATED_IDP_USER_INFO_URL)
            .idpLogoutUrl(UPDATED_IDP_LOGOUT_URL);

        restApiCredentialMockMvc.perform(put("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiCredential)))
            .andExpect(status().isOk());

        // Validate the ApiCredential in the database
        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeUpdate);
        ApiCredential testApiCredential = apiCredentialList.get(apiCredentialList.size() - 1);
        assertThat(testApiCredential.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiCredential.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiCredential.getIdpName()).isEqualTo(UPDATED_IDP_NAME);
        assertThat(testApiCredential.getIdpDscription()).isEqualTo(UPDATED_IDP_DSCRIPTION);
        assertThat(testApiCredential.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testApiCredential.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testApiCredential.getClientSecret()).isEqualTo(UPDATED_CLIENT_SECRET);
        assertThat(testApiCredential.getIssuer()).isEqualTo(UPDATED_ISSUER);
        assertThat(testApiCredential.getScope()).isEqualTo(UPDATED_SCOPE);
        assertThat(testApiCredential.getCallbackUrl()).isEqualTo(UPDATED_CALLBACK_URL);
        assertThat(testApiCredential.getCertCacheTTL()).isEqualTo(UPDATED_CERT_CACHE_TTL);
        assertThat(testApiCredential.getTokenTTTL()).isEqualTo(UPDATED_TOKEN_TTTL);
        assertThat(testApiCredential.getIdpUrl()).isEqualTo(UPDATED_IDP_URL);
        assertThat(testApiCredential.getIdpValidateUrl()).isEqualTo(UPDATED_IDP_VALIDATE_URL);
        assertThat(testApiCredential.getIdpUserInfoUrl()).isEqualTo(UPDATED_IDP_USER_INFO_URL);
        assertThat(testApiCredential.getIdpLogoutUrl()).isEqualTo(UPDATED_IDP_LOGOUT_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingApiCredential() throws Exception {
        int databaseSizeBeforeUpdate = apiCredentialRepository.findAll().size();

        // Create the ApiCredential

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiCredentialMockMvc.perform(put("/api/api-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiCredential)))
            .andExpect(status().isBadRequest());

        // Validate the ApiCredential in the database
        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiCredential() throws Exception {
        // Initialize the database
        apiCredentialService.save(apiCredential);

        int databaseSizeBeforeDelete = apiCredentialRepository.findAll().size();

        // Delete the apiCredential
        restApiCredentialMockMvc.perform(delete("/api/api-credentials/{id}", apiCredential.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiCredential> apiCredentialList = apiCredentialRepository.findAll();
        assertThat(apiCredentialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiCredential.class);
        ApiCredential apiCredential1 = new ApiCredential();
        apiCredential1.setId(1L);
        ApiCredential apiCredential2 = new ApiCredential();
        apiCredential2.setId(apiCredential1.getId());
        assertThat(apiCredential1).isEqualTo(apiCredential2);
        apiCredential2.setId(2L);
        assertThat(apiCredential1).isNotEqualTo(apiCredential2);
        apiCredential1.setId(null);
        assertThat(apiCredential1).isNotEqualTo(apiCredential2);
    }
}
