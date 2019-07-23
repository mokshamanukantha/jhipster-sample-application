package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiLogin;
import io.github.jhipster.application.repository.ApiLoginRepository;
import io.github.jhipster.application.service.ApiLoginService;
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
 * Integration tests for the {@Link ApiLoginResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiLoginResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiLoginRepository apiLoginRepository;

    @Autowired
    private ApiLoginService apiLoginService;

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

    private MockMvc restApiLoginMockMvc;

    private ApiLogin apiLogin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiLoginResource apiLoginResource = new ApiLoginResource(apiLoginService);
        this.restApiLoginMockMvc = MockMvcBuilders.standaloneSetup(apiLoginResource)
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
    public static ApiLogin createEntity(EntityManager em) {
        ApiLogin apiLogin = new ApiLogin()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return apiLogin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiLogin createUpdatedEntity(EntityManager em) {
        ApiLogin apiLogin = new ApiLogin()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return apiLogin;
    }

    @BeforeEach
    public void initTest() {
        apiLogin = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiLogin() throws Exception {
        int databaseSizeBeforeCreate = apiLoginRepository.findAll().size();

        // Create the ApiLogin
        restApiLoginMockMvc.perform(post("/api/api-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiLogin)))
            .andExpect(status().isCreated());

        // Validate the ApiLogin in the database
        List<ApiLogin> apiLoginList = apiLoginRepository.findAll();
        assertThat(apiLoginList).hasSize(databaseSizeBeforeCreate + 1);
        ApiLogin testApiLogin = apiLoginList.get(apiLoginList.size() - 1);
        assertThat(testApiLogin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiLogin.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiLoginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiLoginRepository.findAll().size();

        // Create the ApiLogin with an existing ID
        apiLogin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiLoginMockMvc.perform(post("/api/api-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiLogin)))
            .andExpect(status().isBadRequest());

        // Validate the ApiLogin in the database
        List<ApiLogin> apiLoginList = apiLoginRepository.findAll();
        assertThat(apiLoginList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApiLogins() throws Exception {
        // Initialize the database
        apiLoginRepository.saveAndFlush(apiLogin);

        // Get all the apiLoginList
        restApiLoginMockMvc.perform(get("/api/api-logins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getApiLogin() throws Exception {
        // Initialize the database
        apiLoginRepository.saveAndFlush(apiLogin);

        // Get the apiLogin
        restApiLoginMockMvc.perform(get("/api/api-logins/{id}", apiLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiLogin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiLogin() throws Exception {
        // Get the apiLogin
        restApiLoginMockMvc.perform(get("/api/api-logins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiLogin() throws Exception {
        // Initialize the database
        apiLoginService.save(apiLogin);

        int databaseSizeBeforeUpdate = apiLoginRepository.findAll().size();

        // Update the apiLogin
        ApiLogin updatedApiLogin = apiLoginRepository.findById(apiLogin.getId()).get();
        // Disconnect from session so that the updates on updatedApiLogin are not directly saved in db
        em.detach(updatedApiLogin);
        updatedApiLogin
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restApiLoginMockMvc.perform(put("/api/api-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiLogin)))
            .andExpect(status().isOk());

        // Validate the ApiLogin in the database
        List<ApiLogin> apiLoginList = apiLoginRepository.findAll();
        assertThat(apiLoginList).hasSize(databaseSizeBeforeUpdate);
        ApiLogin testApiLogin = apiLoginList.get(apiLoginList.size() - 1);
        assertThat(testApiLogin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiLogin.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiLogin() throws Exception {
        int databaseSizeBeforeUpdate = apiLoginRepository.findAll().size();

        // Create the ApiLogin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiLoginMockMvc.perform(put("/api/api-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiLogin)))
            .andExpect(status().isBadRequest());

        // Validate the ApiLogin in the database
        List<ApiLogin> apiLoginList = apiLoginRepository.findAll();
        assertThat(apiLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiLogin() throws Exception {
        // Initialize the database
        apiLoginService.save(apiLogin);

        int databaseSizeBeforeDelete = apiLoginRepository.findAll().size();

        // Delete the apiLogin
        restApiLoginMockMvc.perform(delete("/api/api-logins/{id}", apiLogin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiLogin> apiLoginList = apiLoginRepository.findAll();
        assertThat(apiLoginList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiLogin.class);
        ApiLogin apiLogin1 = new ApiLogin();
        apiLogin1.setId(1L);
        ApiLogin apiLogin2 = new ApiLogin();
        apiLogin2.setId(apiLogin1.getId());
        assertThat(apiLogin1).isEqualTo(apiLogin2);
        apiLogin2.setId(2L);
        assertThat(apiLogin1).isNotEqualTo(apiLogin2);
        apiLogin1.setId(null);
        assertThat(apiLogin1).isNotEqualTo(apiLogin2);
    }
}
