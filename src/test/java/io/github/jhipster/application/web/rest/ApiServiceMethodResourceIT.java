package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiServiceMethod;
import io.github.jhipster.application.repository.ApiServiceMethodRepository;
import io.github.jhipster.application.service.ApiServiceMethodService;
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

import io.github.jhipster.application.domain.enumeration.MethodType;
import io.github.jhipster.application.domain.enumeration.HttpMethod;
/**
 * Integration tests for the {@Link ApiServiceMethodResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiServiceMethodResourceIT {

    private static final MethodType DEFAULT_TYPE = MethodType.URL;
    private static final MethodType UPDATED_TYPE = MethodType.COMPONENT;

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final HttpMethod DEFAULT_METHOD = HttpMethod.GET;
    private static final HttpMethod UPDATED_METHOD = HttpMethod.POST;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiServiceMethodRepository apiServiceMethodRepository;

    @Mock
    private ApiServiceMethodRepository apiServiceMethodRepositoryMock;

    @Mock
    private ApiServiceMethodService apiServiceMethodServiceMock;

    @Autowired
    private ApiServiceMethodService apiServiceMethodService;

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

    private MockMvc restApiServiceMethodMockMvc;

    private ApiServiceMethod apiServiceMethod;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiServiceMethodResource apiServiceMethodResource = new ApiServiceMethodResource(apiServiceMethodService);
        this.restApiServiceMethodMockMvc = MockMvcBuilders.standaloneSetup(apiServiceMethodResource)
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
    public static ApiServiceMethod createEntity(EntityManager em) {
        ApiServiceMethod apiServiceMethod = new ApiServiceMethod()
            .type(DEFAULT_TYPE)
            .version(DEFAULT_VERSION)
            .path(DEFAULT_PATH)
            .method(DEFAULT_METHOD)
            .description(DEFAULT_DESCRIPTION);
        return apiServiceMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiServiceMethod createUpdatedEntity(EntityManager em) {
        ApiServiceMethod apiServiceMethod = new ApiServiceMethod()
            .type(UPDATED_TYPE)
            .version(UPDATED_VERSION)
            .path(UPDATED_PATH)
            .method(UPDATED_METHOD)
            .description(UPDATED_DESCRIPTION);
        return apiServiceMethod;
    }

    @BeforeEach
    public void initTest() {
        apiServiceMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiServiceMethod() throws Exception {
        int databaseSizeBeforeCreate = apiServiceMethodRepository.findAll().size();

        // Create the ApiServiceMethod
        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isCreated());

        // Validate the ApiServiceMethod in the database
        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeCreate + 1);
        ApiServiceMethod testApiServiceMethod = apiServiceMethodList.get(apiServiceMethodList.size() - 1);
        assertThat(testApiServiceMethod.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApiServiceMethod.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testApiServiceMethod.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testApiServiceMethod.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testApiServiceMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiServiceMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiServiceMethodRepository.findAll().size();

        // Create the ApiServiceMethod with an existing ID
        apiServiceMethod.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceMethod in the database
        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceMethodRepository.findAll().size();
        // set the field null
        apiServiceMethod.setType(null);

        // Create the ApiServiceMethod, which fails.

        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceMethodRepository.findAll().size();
        // set the field null
        apiServiceMethod.setVersion(null);

        // Create the ApiServiceMethod, which fails.

        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceMethodRepository.findAll().size();
        // set the field null
        apiServiceMethod.setPath(null);

        // Create the ApiServiceMethod, which fails.

        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceMethodRepository.findAll().size();
        // set the field null
        apiServiceMethod.setMethod(null);

        // Create the ApiServiceMethod, which fails.

        restApiServiceMethodMockMvc.perform(post("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiServiceMethods() throws Exception {
        // Initialize the database
        apiServiceMethodRepository.saveAndFlush(apiServiceMethod);

        // Get all the apiServiceMethodList
        restApiServiceMethodMockMvc.perform(get("/api/api-service-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiServiceMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllApiServiceMethodsWithEagerRelationshipsIsEnabled() throws Exception {
        ApiServiceMethodResource apiServiceMethodResource = new ApiServiceMethodResource(apiServiceMethodServiceMock);
        when(apiServiceMethodServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restApiServiceMethodMockMvc = MockMvcBuilders.standaloneSetup(apiServiceMethodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiServiceMethodMockMvc.perform(get("/api/api-service-methods?eagerload=true"))
        .andExpect(status().isOk());

        verify(apiServiceMethodServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllApiServiceMethodsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ApiServiceMethodResource apiServiceMethodResource = new ApiServiceMethodResource(apiServiceMethodServiceMock);
            when(apiServiceMethodServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restApiServiceMethodMockMvc = MockMvcBuilders.standaloneSetup(apiServiceMethodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiServiceMethodMockMvc.perform(get("/api/api-service-methods?eagerload=true"))
        .andExpect(status().isOk());

            verify(apiServiceMethodServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getApiServiceMethod() throws Exception {
        // Initialize the database
        apiServiceMethodRepository.saveAndFlush(apiServiceMethod);

        // Get the apiServiceMethod
        restApiServiceMethodMockMvc.perform(get("/api/api-service-methods/{id}", apiServiceMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiServiceMethod.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiServiceMethod() throws Exception {
        // Get the apiServiceMethod
        restApiServiceMethodMockMvc.perform(get("/api/api-service-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiServiceMethod() throws Exception {
        // Initialize the database
        apiServiceMethodService.save(apiServiceMethod);

        int databaseSizeBeforeUpdate = apiServiceMethodRepository.findAll().size();

        // Update the apiServiceMethod
        ApiServiceMethod updatedApiServiceMethod = apiServiceMethodRepository.findById(apiServiceMethod.getId()).get();
        // Disconnect from session so that the updates on updatedApiServiceMethod are not directly saved in db
        em.detach(updatedApiServiceMethod);
        updatedApiServiceMethod
            .type(UPDATED_TYPE)
            .version(UPDATED_VERSION)
            .path(UPDATED_PATH)
            .method(UPDATED_METHOD)
            .description(UPDATED_DESCRIPTION);

        restApiServiceMethodMockMvc.perform(put("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiServiceMethod)))
            .andExpect(status().isOk());

        // Validate the ApiServiceMethod in the database
        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeUpdate);
        ApiServiceMethod testApiServiceMethod = apiServiceMethodList.get(apiServiceMethodList.size() - 1);
        assertThat(testApiServiceMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApiServiceMethod.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testApiServiceMethod.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testApiServiceMethod.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testApiServiceMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiServiceMethod() throws Exception {
        int databaseSizeBeforeUpdate = apiServiceMethodRepository.findAll().size();

        // Create the ApiServiceMethod

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiServiceMethodMockMvc.perform(put("/api/api-service-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceMethod)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceMethod in the database
        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiServiceMethod() throws Exception {
        // Initialize the database
        apiServiceMethodService.save(apiServiceMethod);

        int databaseSizeBeforeDelete = apiServiceMethodRepository.findAll().size();

        // Delete the apiServiceMethod
        restApiServiceMethodMockMvc.perform(delete("/api/api-service-methods/{id}", apiServiceMethod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiServiceMethod> apiServiceMethodList = apiServiceMethodRepository.findAll();
        assertThat(apiServiceMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiServiceMethod.class);
        ApiServiceMethod apiServiceMethod1 = new ApiServiceMethod();
        apiServiceMethod1.setId(1L);
        ApiServiceMethod apiServiceMethod2 = new ApiServiceMethod();
        apiServiceMethod2.setId(apiServiceMethod1.getId());
        assertThat(apiServiceMethod1).isEqualTo(apiServiceMethod2);
        apiServiceMethod2.setId(2L);
        assertThat(apiServiceMethod1).isNotEqualTo(apiServiceMethod2);
        apiServiceMethod1.setId(null);
        assertThat(apiServiceMethod1).isNotEqualTo(apiServiceMethod2);
    }
}
