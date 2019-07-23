package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiConfiguration;
import io.github.jhipster.application.repository.ApiConfigurationRepository;
import io.github.jhipster.application.service.ApiConfigurationService;
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
 * Integration tests for the {@Link ApiConfigurationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiConfigurationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ApiConfigurationRepository apiConfigurationRepository;

    @Autowired
    private ApiConfigurationService apiConfigurationService;

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

    private MockMvc restApiConfigurationMockMvc;

    private ApiConfiguration apiConfiguration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiConfigurationResource apiConfigurationResource = new ApiConfigurationResource(apiConfigurationService);
        this.restApiConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiConfigurationResource)
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
    public static ApiConfiguration createEntity(EntityManager em) {
        ApiConfiguration apiConfiguration = new ApiConfiguration()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
        return apiConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiConfiguration createUpdatedEntity(EntityManager em) {
        ApiConfiguration apiConfiguration = new ApiConfiguration()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
        return apiConfiguration;
    }

    @BeforeEach
    public void initTest() {
        apiConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiConfiguration() throws Exception {
        int databaseSizeBeforeCreate = apiConfigurationRepository.findAll().size();

        // Create the ApiConfiguration
        restApiConfigurationMockMvc.perform(post("/api/api-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConfiguration)))
            .andExpect(status().isCreated());

        // Validate the ApiConfiguration in the database
        List<ApiConfiguration> apiConfigurationList = apiConfigurationRepository.findAll();
        assertThat(apiConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ApiConfiguration testApiConfiguration = apiConfigurationList.get(apiConfigurationList.size() - 1);
        assertThat(testApiConfiguration.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiConfiguration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createApiConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiConfigurationRepository.findAll().size();

        // Create the ApiConfiguration with an existing ID
        apiConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiConfigurationMockMvc.perform(post("/api/api-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the ApiConfiguration in the database
        List<ApiConfiguration> apiConfigurationList = apiConfigurationRepository.findAll();
        assertThat(apiConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApiConfigurations() throws Exception {
        // Initialize the database
        apiConfigurationRepository.saveAndFlush(apiConfiguration);

        // Get all the apiConfigurationList
        restApiConfigurationMockMvc.perform(get("/api/api-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getApiConfiguration() throws Exception {
        // Initialize the database
        apiConfigurationRepository.saveAndFlush(apiConfiguration);

        // Get the apiConfiguration
        restApiConfigurationMockMvc.perform(get("/api/api-configurations/{id}", apiConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiConfiguration() throws Exception {
        // Get the apiConfiguration
        restApiConfigurationMockMvc.perform(get("/api/api-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiConfiguration() throws Exception {
        // Initialize the database
        apiConfigurationService.save(apiConfiguration);

        int databaseSizeBeforeUpdate = apiConfigurationRepository.findAll().size();

        // Update the apiConfiguration
        ApiConfiguration updatedApiConfiguration = apiConfigurationRepository.findById(apiConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedApiConfiguration are not directly saved in db
        em.detach(updatedApiConfiguration);
        updatedApiConfiguration
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restApiConfigurationMockMvc.perform(put("/api/api-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiConfiguration)))
            .andExpect(status().isOk());

        // Validate the ApiConfiguration in the database
        List<ApiConfiguration> apiConfigurationList = apiConfigurationRepository.findAll();
        assertThat(apiConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ApiConfiguration testApiConfiguration = apiConfigurationList.get(apiConfigurationList.size() - 1);
        assertThat(testApiConfiguration.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingApiConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = apiConfigurationRepository.findAll().size();

        // Create the ApiConfiguration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiConfigurationMockMvc.perform(put("/api/api-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the ApiConfiguration in the database
        List<ApiConfiguration> apiConfigurationList = apiConfigurationRepository.findAll();
        assertThat(apiConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiConfiguration() throws Exception {
        // Initialize the database
        apiConfigurationService.save(apiConfiguration);

        int databaseSizeBeforeDelete = apiConfigurationRepository.findAll().size();

        // Delete the apiConfiguration
        restApiConfigurationMockMvc.perform(delete("/api/api-configurations/{id}", apiConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiConfiguration> apiConfigurationList = apiConfigurationRepository.findAll();
        assertThat(apiConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiConfiguration.class);
        ApiConfiguration apiConfiguration1 = new ApiConfiguration();
        apiConfiguration1.setId(1L);
        ApiConfiguration apiConfiguration2 = new ApiConfiguration();
        apiConfiguration2.setId(apiConfiguration1.getId());
        assertThat(apiConfiguration1).isEqualTo(apiConfiguration2);
        apiConfiguration2.setId(2L);
        assertThat(apiConfiguration1).isNotEqualTo(apiConfiguration2);
        apiConfiguration1.setId(null);
        assertThat(apiConfiguration1).isNotEqualTo(apiConfiguration2);
    }
}
