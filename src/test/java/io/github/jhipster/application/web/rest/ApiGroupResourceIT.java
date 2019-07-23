package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.ApiGroup;
import io.github.jhipster.application.repository.ApiGroupRepository;
import io.github.jhipster.application.service.ApiGroupService;
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
 * Integration tests for the {@Link ApiGroupResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ApiGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiGroupRepository apiGroupRepository;

    @Mock
    private ApiGroupRepository apiGroupRepositoryMock;

    @Mock
    private ApiGroupService apiGroupServiceMock;

    @Autowired
    private ApiGroupService apiGroupService;

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

    private MockMvc restApiGroupMockMvc;

    private ApiGroup apiGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiGroupResource apiGroupResource = new ApiGroupResource(apiGroupService);
        this.restApiGroupMockMvc = MockMvcBuilders.standaloneSetup(apiGroupResource)
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
    public static ApiGroup createEntity(EntityManager em) {
        ApiGroup apiGroup = new ApiGroup()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return apiGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiGroup createUpdatedEntity(EntityManager em) {
        ApiGroup apiGroup = new ApiGroup()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return apiGroup;
    }

    @BeforeEach
    public void initTest() {
        apiGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiGroup() throws Exception {
        int databaseSizeBeforeCreate = apiGroupRepository.findAll().size();

        // Create the ApiGroup
        restApiGroupMockMvc.perform(post("/api/api-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiGroup)))
            .andExpect(status().isCreated());

        // Validate the ApiGroup in the database
        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ApiGroup testApiGroup = apiGroupList.get(apiGroupList.size() - 1);
        assertThat(testApiGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiGroupRepository.findAll().size();

        // Create the ApiGroup with an existing ID
        apiGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiGroupMockMvc.perform(post("/api/api-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ApiGroup in the database
        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiGroupRepository.findAll().size();
        // set the field null
        apiGroup.setCode(null);

        // Create the ApiGroup, which fails.

        restApiGroupMockMvc.perform(post("/api/api-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiGroup)))
            .andExpect(status().isBadRequest());

        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiGroups() throws Exception {
        // Initialize the database
        apiGroupRepository.saveAndFlush(apiGroup);

        // Get all the apiGroupList
        restApiGroupMockMvc.perform(get("/api/api-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllApiGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        ApiGroupResource apiGroupResource = new ApiGroupResource(apiGroupServiceMock);
        when(apiGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restApiGroupMockMvc = MockMvcBuilders.standaloneSetup(apiGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiGroupMockMvc.perform(get("/api/api-groups?eagerload=true"))
        .andExpect(status().isOk());

        verify(apiGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllApiGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ApiGroupResource apiGroupResource = new ApiGroupResource(apiGroupServiceMock);
            when(apiGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restApiGroupMockMvc = MockMvcBuilders.standaloneSetup(apiGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restApiGroupMockMvc.perform(get("/api/api-groups?eagerload=true"))
        .andExpect(status().isOk());

            verify(apiGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getApiGroup() throws Exception {
        // Initialize the database
        apiGroupRepository.saveAndFlush(apiGroup);

        // Get the apiGroup
        restApiGroupMockMvc.perform(get("/api/api-groups/{id}", apiGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiGroup() throws Exception {
        // Get the apiGroup
        restApiGroupMockMvc.perform(get("/api/api-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiGroup() throws Exception {
        // Initialize the database
        apiGroupService.save(apiGroup);

        int databaseSizeBeforeUpdate = apiGroupRepository.findAll().size();

        // Update the apiGroup
        ApiGroup updatedApiGroup = apiGroupRepository.findById(apiGroup.getId()).get();
        // Disconnect from session so that the updates on updatedApiGroup are not directly saved in db
        em.detach(updatedApiGroup);
        updatedApiGroup
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restApiGroupMockMvc.perform(put("/api/api-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiGroup)))
            .andExpect(status().isOk());

        // Validate the ApiGroup in the database
        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeUpdate);
        ApiGroup testApiGroup = apiGroupList.get(apiGroupList.size() - 1);
        assertThat(testApiGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiGroup() throws Exception {
        int databaseSizeBeforeUpdate = apiGroupRepository.findAll().size();

        // Create the ApiGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiGroupMockMvc.perform(put("/api/api-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ApiGroup in the database
        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiGroup() throws Exception {
        // Initialize the database
        apiGroupService.save(apiGroup);

        int databaseSizeBeforeDelete = apiGroupRepository.findAll().size();

        // Delete the apiGroup
        restApiGroupMockMvc.perform(delete("/api/api-groups/{id}", apiGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiGroup> apiGroupList = apiGroupRepository.findAll();
        assertThat(apiGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiGroup.class);
        ApiGroup apiGroup1 = new ApiGroup();
        apiGroup1.setId(1L);
        ApiGroup apiGroup2 = new ApiGroup();
        apiGroup2.setId(apiGroup1.getId());
        assertThat(apiGroup1).isEqualTo(apiGroup2);
        apiGroup2.setId(2L);
        assertThat(apiGroup1).isNotEqualTo(apiGroup2);
        apiGroup1.setId(null);
        assertThat(apiGroup1).isNotEqualTo(apiGroup2);
    }
}
