package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiPermission;
import io.github.jhipster.application.repository.ApiPermissionRepository;
import io.github.jhipster.application.service.ApiPermissionService;
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
 * Integration tests for the {@Link ApiPermissionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiPermissionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;

    @Autowired
    private ApiPermissionService apiPermissionService;

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

    private MockMvc restApiPermissionMockMvc;

    private ApiPermission apiPermission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiPermissionResource apiPermissionResource = new ApiPermissionResource(apiPermissionService);
        this.restApiPermissionMockMvc = MockMvcBuilders.standaloneSetup(apiPermissionResource)
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
    public static ApiPermission createEntity(EntityManager em) {
        ApiPermission apiPermission = new ApiPermission()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return apiPermission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiPermission createUpdatedEntity(EntityManager em) {
        ApiPermission apiPermission = new ApiPermission()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return apiPermission;
    }

    @BeforeEach
    public void initTest() {
        apiPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiPermission() throws Exception {
        int databaseSizeBeforeCreate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission
        restApiPermissionMockMvc.perform(post("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPermission)))
            .andExpect(status().isCreated());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        ApiPermission testApiPermission = apiPermissionList.get(apiPermissionList.size() - 1);
        assertThat(testApiPermission.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission with an existing ID
        apiPermission.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiPermissionMockMvc.perform(post("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPermission)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiPermissionRepository.findAll().size();
        // set the field null
        apiPermission.setCode(null);

        // Create the ApiPermission, which fails.

        restApiPermissionMockMvc.perform(post("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPermission)))
            .andExpect(status().isBadRequest());

        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiPermissions() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList
        restApiPermissionMockMvc.perform(get("/api/api-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getApiPermission() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get the apiPermission
        restApiPermissionMockMvc.perform(get("/api/api-permissions/{id}", apiPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiPermission.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiPermission() throws Exception {
        // Get the apiPermission
        restApiPermissionMockMvc.perform(get("/api/api-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiPermission() throws Exception {
        // Initialize the database
        apiPermissionService.save(apiPermission);

        int databaseSizeBeforeUpdate = apiPermissionRepository.findAll().size();

        // Update the apiPermission
        ApiPermission updatedApiPermission = apiPermissionRepository.findById(apiPermission.getId()).get();
        // Disconnect from session so that the updates on updatedApiPermission are not directly saved in db
        em.detach(updatedApiPermission);
        updatedApiPermission
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restApiPermissionMockMvc.perform(put("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiPermission)))
            .andExpect(status().isOk());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeUpdate);
        ApiPermission testApiPermission = apiPermissionList.get(apiPermissionList.size() - 1);
        assertThat(testApiPermission.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiPermission() throws Exception {
        int databaseSizeBeforeUpdate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiPermissionMockMvc.perform(put("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPermission)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiPermission() throws Exception {
        // Initialize the database
        apiPermissionService.save(apiPermission);

        int databaseSizeBeforeDelete = apiPermissionRepository.findAll().size();

        // Delete the apiPermission
        restApiPermissionMockMvc.perform(delete("/api/api-permissions/{id}", apiPermission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiPermission.class);
        ApiPermission apiPermission1 = new ApiPermission();
        apiPermission1.setId(1L);
        ApiPermission apiPermission2 = new ApiPermission();
        apiPermission2.setId(apiPermission1.getId());
        assertThat(apiPermission1).isEqualTo(apiPermission2);
        apiPermission2.setId(2L);
        assertThat(apiPermission1).isNotEqualTo(apiPermission2);
        apiPermission1.setId(null);
        assertThat(apiPermission1).isNotEqualTo(apiPermission2);
    }
}
