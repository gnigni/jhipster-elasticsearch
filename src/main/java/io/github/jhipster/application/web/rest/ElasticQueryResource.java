package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ElasticQuery;
import io.github.jhipster.application.repository.ElasticQueryRepository;
import io.github.jhipster.application.repository.search.ElasticQuerySearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ElasticQuery.
 */
@RestController
@RequestMapping("/api")
public class ElasticQueryResource {

    private final Logger log = LoggerFactory.getLogger(ElasticQueryResource.class);

    private static final String ENTITY_NAME = "elasticQuery";

    private final ElasticQueryRepository elasticQueryRepository;

    private final ElasticQuerySearchRepository elasticQuerySearchRepository;

    public ElasticQueryResource(ElasticQueryRepository elasticQueryRepository, ElasticQuerySearchRepository elasticQuerySearchRepository) {
        this.elasticQueryRepository = elasticQueryRepository;
        this.elasticQuerySearchRepository = elasticQuerySearchRepository;
    }

    /**
     * POST  /elastic-queries : Create a new elasticQuery.
     *
     * @param elasticQuery the elasticQuery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elasticQuery, or with status 400 (Bad Request) if the elasticQuery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elastic-queries")
    @Timed
    public ResponseEntity<ElasticQuery> createElasticQuery(@RequestBody ElasticQuery elasticQuery) throws URISyntaxException {
        log.debug("REST request to save ElasticQuery : {}", elasticQuery);
        if (elasticQuery.getId() != null) {
            throw new BadRequestAlertException("A new elasticQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElasticQuery result = elasticQueryRepository.save(elasticQuery);
        elasticQuerySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/elastic-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elastic-queries : Updates an existing elasticQuery.
     *
     * @param elasticQuery the elasticQuery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elasticQuery,
     * or with status 400 (Bad Request) if the elasticQuery is not valid,
     * or with status 500 (Internal Server Error) if the elasticQuery couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elastic-queries")
    @Timed
    public ResponseEntity<ElasticQuery> updateElasticQuery(@RequestBody ElasticQuery elasticQuery) throws URISyntaxException {
        log.debug("REST request to update ElasticQuery : {}", elasticQuery);
        if (elasticQuery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElasticQuery result = elasticQueryRepository.save(elasticQuery);
        elasticQuerySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elasticQuery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elastic-queries : get all the elasticQueries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elasticQueries in body
     */
    @GetMapping("/elastic-queries")
    @Timed
    public List<ElasticQuery> getAllElasticQueries() {
        log.debug("REST request to get all ElasticQueries");
        return elasticQueryRepository.findAll();
    }

    /**
     * GET  /elastic-queries/:id : get the "id" elasticQuery.
     *
     * @param id the id of the elasticQuery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elasticQuery, or with status 404 (Not Found)
     */
    @GetMapping("/elastic-queries/{id}")
    @Timed
    public ResponseEntity<ElasticQuery> getElasticQuery(@PathVariable Long id) {
        log.debug("REST request to get ElasticQuery : {}", id);
        Optional<ElasticQuery> elasticQuery = elasticQueryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elasticQuery);
    }

    /**
     * DELETE  /elastic-queries/:id : delete the "id" elasticQuery.
     *
     * @param id the id of the elasticQuery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elastic-queries/{id}")
    @Timed
    public ResponseEntity<Void> deleteElasticQuery(@PathVariable Long id) {
        log.debug("REST request to delete ElasticQuery : {}", id);

        elasticQueryRepository.deleteById(id);
        elasticQuerySearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/elastic-queries?query=:query : search for the elasticQuery corresponding
     * to the query.
     *
     * @param query the query of the elasticQuery search
     * @return the result of the search
     */
    @GetMapping("/_search/elastic-queries")
    @Timed
    public List<ElasticQuery> searchElasticQueries(@RequestParam String query) {
        log.debug("REST request to search ElasticQueries for query {}", query);
        return StreamSupport
            .stream(elasticQuerySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
