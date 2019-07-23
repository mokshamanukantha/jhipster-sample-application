package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiSession;
import io.github.jhipster.application.repository.ApiSessionRepository;
import io.github.jhipster.application.service.ApiSessionService;
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
 * Integration tests for the {@Link ApiSessionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiSessionResourceIT {

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private ApiSessionRepository apiSessionRepository;

    @Autowired
    private ApiSessionService apiSessionService;

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

    private MockMvc restApiSessionMockMvc;

    private ApiSession apiSession;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiSessionResource apiSessionResource = new ApiSessionResource(apiSessionService);
        this.restApiSessionMockMvc = MockMvcBuilders.standaloneSetup(apiSessionResource)
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
    public static ApiSession createEntity(EntityManager em) {
        ApiSession apiSession = new ApiSession()
            .sessionId(DEFAULT_SESSION_ID)
            .token(DEFAULT_TOKEN);
        return apiSession;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiSession createUpdatedEntity(EntityManager em) {
        ApiSession apiSession = new ApiSession()
            .sessionId(UPDATED_SESSION_ID)
            .token(UPDATED_TOKEN);
        return apiSession;
    }

    @BeforeEach
    public void initTest() {
        apiSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiSession() throws Exception {
        int databaseSizeBeforeCreate = apiSessionRepository.findAll().size();

        // Create the ApiSession
        restApiSessionMockMvc.perform(post("/api/api-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSession)))
            .andExpect(status().isCreated());

        // Validate the ApiSession in the database
        List<ApiSession> apiSessionList = apiSessionRepository.findAll();
        assertThat(apiSessionList).hasSize(databaseSizeBeforeCreate + 1);
        ApiSession testApiSession = apiSessionList.get(apiSessionList.size() - 1);
        assertThat(testApiSession.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testApiSession.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createApiSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiSessionRepository.findAll().size();

        // Create the ApiSession with an existing ID
        apiSession.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiSessionMockMvc.perform(post("/api/api-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSession)))
            .andExpect(status().isBadRequest());

        // Validate the ApiSession in the database
        List<ApiSession> apiSessionList = apiSessionRepository.findAll();
        assertThat(apiSessionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApiSessions() throws Exception {
        // Initialize the database
        apiSessionRepository.saveAndFlush(apiSession);

        // Get all the apiSessionList
        restApiSessionMockMvc.perform(get("/api/api-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getApiSession() throws Exception {
        // Initialize the database
        apiSessionRepository.saveAndFlush(apiSession);

        // Get the apiSession
        restApiSessionMockMvc.perform(get("/api/api-sessions/{id}", apiSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiSession.getId().intValue()))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiSession() throws Exception {
        // Get the apiSession
        restApiSessionMockMvc.perform(get("/api/api-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiSession() throws Exception {
        // Initialize the database
        apiSessionService.save(apiSession);

        int databaseSizeBeforeUpdate = apiSessionRepository.findAll().size();

        // Update the apiSession
        ApiSession updatedApiSession = apiSessionRepository.findById(apiSession.getId()).get();
        // Disconnect from session so that the updates on updatedApiSession are not directly saved in db
        em.detach(updatedApiSession);
        updatedApiSession
            .sessionId(UPDATED_SESSION_ID)
            .token(UPDATED_TOKEN);

        restApiSessionMockMvc.perform(put("/api/api-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiSession)))
            .andExpect(status().isOk());

        // Validate the ApiSession in the database
        List<ApiSession> apiSessionList = apiSessionRepository.findAll();
        assertThat(apiSessionList).hasSize(databaseSizeBeforeUpdate);
        ApiSession testApiSession = apiSessionList.get(apiSessionList.size() - 1);
        assertThat(testApiSession.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testApiSession.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingApiSession() throws Exception {
        int databaseSizeBeforeUpdate = apiSessionRepository.findAll().size();

        // Create the ApiSession

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiSessionMockMvc.perform(put("/api/api-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSession)))
            .andExpect(status().isBadRequest());

        // Validate the ApiSession in the database
        List<ApiSession> apiSessionList = apiSessionRepository.findAll();
        assertThat(apiSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiSession() throws Exception {
        // Initialize the database
        apiSessionService.save(apiSession);

        int databaseSizeBeforeDelete = apiSessionRepository.findAll().size();

        // Delete the apiSession
        restApiSessionMockMvc.perform(delete("/api/api-sessions/{id}", apiSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiSession> apiSessionList = apiSessionRepository.findAll();
        assertThat(apiSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiSession.class);
        ApiSession apiSession1 = new ApiSession();
        apiSession1.setId(1L);
        ApiSession apiSession2 = new ApiSession();
        apiSession2.setId(apiSession1.getId());
        assertThat(apiSession1).isEqualTo(apiSession2);
        apiSession2.setId(2L);
        assertThat(apiSession1).isNotEqualTo(apiSession2);
        apiSession1.setId(null);
        assertThat(apiSession1).isNotEqualTo(apiSession2);
    }
}
