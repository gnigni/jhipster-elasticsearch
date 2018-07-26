package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterElasticsearchApp;

import io.github.jhipster.application.domain.ActionFormation;
import io.github.jhipster.application.repository.ActionFormationRepository;
import io.github.jhipster.application.repository.search.ActionFormationSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActionFormationResource REST controller.
 *
 * @see ActionFormationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterElasticsearchApp.class)
public class ActionFormationResourceIntTest {

    private static final String DEFAULT_INTITULE_FORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_FORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_HEURES_TOTAL = 1;
    private static final Integer UPDATED_NOMBRE_HEURES_TOTAL = 2;

    @Autowired
    private ActionFormationRepository actionFormationRepository;


    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ActionFormationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActionFormationSearchRepository mockActionFormationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActionFormationMockMvc;

    private ActionFormation actionFormation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionFormationResource actionFormationResource = new ActionFormationResource(actionFormationRepository, mockActionFormationSearchRepository);
        this.restActionFormationMockMvc = MockMvcBuilders.standaloneSetup(actionFormationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionFormation createEntity(EntityManager em) {
        ActionFormation actionFormation = new ActionFormation()
            .intituleFormation(DEFAULT_INTITULE_FORMATION)
            .description(DEFAULT_DESCRIPTION)
            .nombreHeuresTotal(DEFAULT_NOMBRE_HEURES_TOTAL);
        return actionFormation;
    }

    @Before
    public void initTest() {
        actionFormation = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionFormation() throws Exception {
        int databaseSizeBeforeCreate = actionFormationRepository.findAll().size();

        // Create the ActionFormation
        restActionFormationMockMvc.perform(post("/api/action-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionFormation)))
            .andExpect(status().isCreated());

        // Validate the ActionFormation in the database
        List<ActionFormation> actionFormationList = actionFormationRepository.findAll();
        assertThat(actionFormationList).hasSize(databaseSizeBeforeCreate + 1);
        ActionFormation testActionFormation = actionFormationList.get(actionFormationList.size() - 1);
        assertThat(testActionFormation.getIntituleFormation()).isEqualTo(DEFAULT_INTITULE_FORMATION);
        assertThat(testActionFormation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActionFormation.getNombreHeuresTotal()).isEqualTo(DEFAULT_NOMBRE_HEURES_TOTAL);

        // Validate the ActionFormation in Elasticsearch
        verify(mockActionFormationSearchRepository, times(1)).save(testActionFormation);
    }

    @Test
    @Transactional
    public void createActionFormationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionFormationRepository.findAll().size();

        // Create the ActionFormation with an existing ID
        actionFormation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionFormationMockMvc.perform(post("/api/action-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionFormation)))
            .andExpect(status().isBadRequest());

        // Validate the ActionFormation in the database
        List<ActionFormation> actionFormationList = actionFormationRepository.findAll();
        assertThat(actionFormationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActionFormation in Elasticsearch
        verify(mockActionFormationSearchRepository, times(0)).save(actionFormation);
    }

    @Test
    @Transactional
    public void getAllActionFormations() throws Exception {
        // Initialize the database
        actionFormationRepository.saveAndFlush(actionFormation);

        // Get all the actionFormationList
        restActionFormationMockMvc.perform(get("/api/action-formations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleFormation").value(hasItem(DEFAULT_INTITULE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nombreHeuresTotal").value(hasItem(DEFAULT_NOMBRE_HEURES_TOTAL)));
    }
    

    @Test
    @Transactional
    public void getActionFormation() throws Exception {
        // Initialize the database
        actionFormationRepository.saveAndFlush(actionFormation);

        // Get the actionFormation
        restActionFormationMockMvc.perform(get("/api/action-formations/{id}", actionFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actionFormation.getId().intValue()))
            .andExpect(jsonPath("$.intituleFormation").value(DEFAULT_INTITULE_FORMATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.nombreHeuresTotal").value(DEFAULT_NOMBRE_HEURES_TOTAL));
    }
    @Test
    @Transactional
    public void getNonExistingActionFormation() throws Exception {
        // Get the actionFormation
        restActionFormationMockMvc.perform(get("/api/action-formations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionFormation() throws Exception {
        // Initialize the database
        actionFormationRepository.saveAndFlush(actionFormation);

        int databaseSizeBeforeUpdate = actionFormationRepository.findAll().size();

        // Update the actionFormation
        ActionFormation updatedActionFormation = actionFormationRepository.findById(actionFormation.getId()).get();
        // Disconnect from session so that the updates on updatedActionFormation are not directly saved in db
        em.detach(updatedActionFormation);
        updatedActionFormation
            .intituleFormation(UPDATED_INTITULE_FORMATION)
            .description(UPDATED_DESCRIPTION)
            .nombreHeuresTotal(UPDATED_NOMBRE_HEURES_TOTAL);

        restActionFormationMockMvc.perform(put("/api/action-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActionFormation)))
            .andExpect(status().isOk());

        // Validate the ActionFormation in the database
        List<ActionFormation> actionFormationList = actionFormationRepository.findAll();
        assertThat(actionFormationList).hasSize(databaseSizeBeforeUpdate);
        ActionFormation testActionFormation = actionFormationList.get(actionFormationList.size() - 1);
        assertThat(testActionFormation.getIntituleFormation()).isEqualTo(UPDATED_INTITULE_FORMATION);
        assertThat(testActionFormation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActionFormation.getNombreHeuresTotal()).isEqualTo(UPDATED_NOMBRE_HEURES_TOTAL);

        // Validate the ActionFormation in Elasticsearch
        verify(mockActionFormationSearchRepository, times(1)).save(testActionFormation);
    }

    @Test
    @Transactional
    public void updateNonExistingActionFormation() throws Exception {
        int databaseSizeBeforeUpdate = actionFormationRepository.findAll().size();

        // Create the ActionFormation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActionFormationMockMvc.perform(put("/api/action-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionFormation)))
            .andExpect(status().isBadRequest());

        // Validate the ActionFormation in the database
        List<ActionFormation> actionFormationList = actionFormationRepository.findAll();
        assertThat(actionFormationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActionFormation in Elasticsearch
        verify(mockActionFormationSearchRepository, times(0)).save(actionFormation);
    }

    @Test
    @Transactional
    public void deleteActionFormation() throws Exception {
        // Initialize the database
        actionFormationRepository.saveAndFlush(actionFormation);

        int databaseSizeBeforeDelete = actionFormationRepository.findAll().size();

        // Get the actionFormation
        restActionFormationMockMvc.perform(delete("/api/action-formations/{id}", actionFormation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionFormation> actionFormationList = actionFormationRepository.findAll();
        assertThat(actionFormationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActionFormation in Elasticsearch
        verify(mockActionFormationSearchRepository, times(1)).deleteById(actionFormation.getId());
    }

    @Test
    @Transactional
    public void searchActionFormation() throws Exception {
        // Initialize the database
        actionFormationRepository.saveAndFlush(actionFormation);
        when(mockActionFormationSearchRepository.search(queryStringQuery("id:" + actionFormation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(actionFormation), PageRequest.of(0, 1), 1));
        // Search the actionFormation
        restActionFormationMockMvc.perform(get("/api/_search/action-formations?query=id:" + actionFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleFormation").value(hasItem(DEFAULT_INTITULE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nombreHeuresTotal").value(hasItem(DEFAULT_NOMBRE_HEURES_TOTAL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionFormation.class);
        ActionFormation actionFormation1 = new ActionFormation();
        actionFormation1.setId(1L);
        ActionFormation actionFormation2 = new ActionFormation();
        actionFormation2.setId(actionFormation1.getId());
        assertThat(actionFormation1).isEqualTo(actionFormation2);
        actionFormation2.setId(2L);
        assertThat(actionFormation1).isNotEqualTo(actionFormation2);
        actionFormation1.setId(null);
        assertThat(actionFormation1).isNotEqualTo(actionFormation2);
    }
}
