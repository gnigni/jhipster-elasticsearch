package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterElasticsearchApp;

import io.github.jhipster.application.domain.ElasticQuery;
import io.github.jhipster.application.repository.ElasticQueryRepository;
import io.github.jhipster.application.repository.search.ElasticQuerySearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Test class for the ElasticQueryResource REST controller.
 *
 * @see ElasticQueryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterElasticsearchApp.class)
public class ElasticQueryResourceIntTest {

    private static final String DEFAULT_QUOI = "AAAAAAAAAA";
    private static final String UPDATED_QUOI = "BBBBBBBBBB";

    private static final String DEFAULT_OU = "AAAAAAAAAA";
    private static final String UPDATED_OU = "BBBBBBBBBB";

    @Autowired
    private ElasticQueryRepository elasticQueryRepository;


    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ElasticQuerySearchRepositoryMockConfiguration
     */
    @Autowired
    private ElasticQuerySearchRepository mockElasticQuerySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElasticQueryMockMvc;

    private ElasticQuery elasticQuery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElasticQueryResource elasticQueryResource = new ElasticQueryResource(elasticQueryRepository, mockElasticQuerySearchRepository);
        this.restElasticQueryMockMvc = MockMvcBuilders.standaloneSetup(elasticQueryResource)
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
    public static ElasticQuery createEntity(EntityManager em) {
        ElasticQuery elasticQuery = new ElasticQuery()
            .quoi(DEFAULT_QUOI)
            .ou(DEFAULT_OU);
        return elasticQuery;
    }

    @Before
    public void initTest() {
        elasticQuery = createEntity(em);
    }

    @Test
    @Transactional
    public void createElasticQuery() throws Exception {
        int databaseSizeBeforeCreate = elasticQueryRepository.findAll().size();

        // Create the ElasticQuery
        restElasticQueryMockMvc.perform(post("/api/elastic-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elasticQuery)))
            .andExpect(status().isCreated());

        // Validate the ElasticQuery in the database
        List<ElasticQuery> elasticQueryList = elasticQueryRepository.findAll();
        assertThat(elasticQueryList).hasSize(databaseSizeBeforeCreate + 1);
        ElasticQuery testElasticQuery = elasticQueryList.get(elasticQueryList.size() - 1);
        assertThat(testElasticQuery.getQuoi()).isEqualTo(DEFAULT_QUOI);
        assertThat(testElasticQuery.getOu()).isEqualTo(DEFAULT_OU);

        // Validate the ElasticQuery in Elasticsearch
        verify(mockElasticQuerySearchRepository, times(1)).save(testElasticQuery);
    }

    @Test
    @Transactional
    public void createElasticQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elasticQueryRepository.findAll().size();

        // Create the ElasticQuery with an existing ID
        elasticQuery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElasticQueryMockMvc.perform(post("/api/elastic-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elasticQuery)))
            .andExpect(status().isBadRequest());

        // Validate the ElasticQuery in the database
        List<ElasticQuery> elasticQueryList = elasticQueryRepository.findAll();
        assertThat(elasticQueryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ElasticQuery in Elasticsearch
        verify(mockElasticQuerySearchRepository, times(0)).save(elasticQuery);
    }

    @Test
    @Transactional
    public void getAllElasticQueries() throws Exception {
        // Initialize the database
        elasticQueryRepository.saveAndFlush(elasticQuery);

        // Get all the elasticQueryList
        restElasticQueryMockMvc.perform(get("/api/elastic-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elasticQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].quoi").value(hasItem(DEFAULT_QUOI.toString())))
            .andExpect(jsonPath("$.[*].ou").value(hasItem(DEFAULT_OU.toString())));
    }
    

    @Test
    @Transactional
    public void getElasticQuery() throws Exception {
        // Initialize the database
        elasticQueryRepository.saveAndFlush(elasticQuery);

        // Get the elasticQuery
        restElasticQueryMockMvc.perform(get("/api/elastic-queries/{id}", elasticQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elasticQuery.getId().intValue()))
            .andExpect(jsonPath("$.quoi").value(DEFAULT_QUOI.toString()))
            .andExpect(jsonPath("$.ou").value(DEFAULT_OU.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingElasticQuery() throws Exception {
        // Get the elasticQuery
        restElasticQueryMockMvc.perform(get("/api/elastic-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElasticQuery() throws Exception {
        // Initialize the database
        elasticQueryRepository.saveAndFlush(elasticQuery);

        int databaseSizeBeforeUpdate = elasticQueryRepository.findAll().size();

        // Update the elasticQuery
        ElasticQuery updatedElasticQuery = elasticQueryRepository.findById(elasticQuery.getId()).get();
        // Disconnect from session so that the updates on updatedElasticQuery are not directly saved in db
        em.detach(updatedElasticQuery);
        updatedElasticQuery
            .quoi(UPDATED_QUOI)
            .ou(UPDATED_OU);

        restElasticQueryMockMvc.perform(put("/api/elastic-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElasticQuery)))
            .andExpect(status().isOk());

        // Validate the ElasticQuery in the database
        List<ElasticQuery> elasticQueryList = elasticQueryRepository.findAll();
        assertThat(elasticQueryList).hasSize(databaseSizeBeforeUpdate);
        ElasticQuery testElasticQuery = elasticQueryList.get(elasticQueryList.size() - 1);
        assertThat(testElasticQuery.getQuoi()).isEqualTo(UPDATED_QUOI);
        assertThat(testElasticQuery.getOu()).isEqualTo(UPDATED_OU);

        // Validate the ElasticQuery in Elasticsearch
        verify(mockElasticQuerySearchRepository, times(1)).save(testElasticQuery);
    }

    @Test
    @Transactional
    public void updateNonExistingElasticQuery() throws Exception {
        int databaseSizeBeforeUpdate = elasticQueryRepository.findAll().size();

        // Create the ElasticQuery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restElasticQueryMockMvc.perform(put("/api/elastic-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elasticQuery)))
            .andExpect(status().isBadRequest());

        // Validate the ElasticQuery in the database
        List<ElasticQuery> elasticQueryList = elasticQueryRepository.findAll();
        assertThat(elasticQueryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ElasticQuery in Elasticsearch
        verify(mockElasticQuerySearchRepository, times(0)).save(elasticQuery);
    }

    @Test
    @Transactional
    public void deleteElasticQuery() throws Exception {
        // Initialize the database
        elasticQueryRepository.saveAndFlush(elasticQuery);

        int databaseSizeBeforeDelete = elasticQueryRepository.findAll().size();

        // Get the elasticQuery
        restElasticQueryMockMvc.perform(delete("/api/elastic-queries/{id}", elasticQuery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ElasticQuery> elasticQueryList = elasticQueryRepository.findAll();
        assertThat(elasticQueryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ElasticQuery in Elasticsearch
        verify(mockElasticQuerySearchRepository, times(1)).deleteById(elasticQuery.getId());
    }

    @Test
    @Transactional
    public void searchElasticQuery() throws Exception {
        // Initialize the database
        elasticQueryRepository.saveAndFlush(elasticQuery);
        when(mockElasticQuerySearchRepository.search(queryStringQuery("id:" + elasticQuery.getId())))
            .thenReturn(Collections.singletonList(elasticQuery));
        // Search the elasticQuery
        restElasticQueryMockMvc.perform(get("/api/_search/elastic-queries?query=id:" + elasticQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elasticQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].quoi").value(hasItem(DEFAULT_QUOI.toString())))
            .andExpect(jsonPath("$.[*].ou").value(hasItem(DEFAULT_OU.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElasticQuery.class);
        ElasticQuery elasticQuery1 = new ElasticQuery();
        elasticQuery1.setId(1L);
        ElasticQuery elasticQuery2 = new ElasticQuery();
        elasticQuery2.setId(elasticQuery1.getId());
        assertThat(elasticQuery1).isEqualTo(elasticQuery2);
        elasticQuery2.setId(2L);
        assertThat(elasticQuery1).isNotEqualTo(elasticQuery2);
        elasticQuery1.setId(null);
        assertThat(elasticQuery1).isNotEqualTo(elasticQuery2);
    }
}
