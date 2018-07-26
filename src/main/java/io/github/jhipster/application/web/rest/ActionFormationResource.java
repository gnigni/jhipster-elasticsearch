package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ActionFormation;
import io.github.jhipster.application.repository.ActionFormationRepository;
import io.github.jhipster.application.repository.search.ActionFormationSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing ActionFormation.
 */
@RestController
@RequestMapping("/api")
public class ActionFormationResource {

    private final Logger log = LoggerFactory.getLogger(ActionFormationResource.class);

    private static final String ENTITY_NAME = "actionFormation";

    private final ActionFormationRepository actionFormationRepository;

    private final ActionFormationSearchRepository actionFormationSearchRepository;

    public ActionFormationResource(ActionFormationRepository actionFormationRepository, ActionFormationSearchRepository actionFormationSearchRepository) {
        this.actionFormationRepository = actionFormationRepository;
        this.actionFormationSearchRepository = actionFormationSearchRepository;
    }

    /**
     * POST  /action-formations : Create a new actionFormation.
     *
     * @param actionFormation the actionFormation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionFormation, or with status 400 (Bad Request) if the actionFormation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/action-formations")
    @Timed
    public ResponseEntity<ActionFormation> createActionFormation(@RequestBody ActionFormation actionFormation) throws URISyntaxException {
        log.debug("REST request to save ActionFormation : {}", actionFormation);
        if (actionFormation.getId() != null) {
            throw new BadRequestAlertException("A new actionFormation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionFormation result = actionFormationRepository.save(actionFormation);
        actionFormationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/action-formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /action-formations : Updates an existing actionFormation.
     *
     * @param actionFormation the actionFormation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionFormation,
     * or with status 400 (Bad Request) if the actionFormation is not valid,
     * or with status 500 (Internal Server Error) if the actionFormation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/action-formations")
    @Timed
    public ResponseEntity<ActionFormation> updateActionFormation(@RequestBody ActionFormation actionFormation) throws URISyntaxException {
        log.debug("REST request to update ActionFormation : {}", actionFormation);
        if (actionFormation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionFormation result = actionFormationRepository.save(actionFormation);
        actionFormationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actionFormation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /action-formations : get all the actionFormations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actionFormations in body
     */
    @GetMapping("/action-formations")
    @Timed
    public ResponseEntity<List<ActionFormation>> getAllActionFormations(Pageable pageable) {
        log.debug("REST request to get a page of ActionFormations");
        Page<ActionFormation> page = actionFormationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/action-formations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /action-formations/:id : get the "id" actionFormation.
     *
     * @param id the id of the actionFormation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionFormation, or with status 404 (Not Found)
     */
    @GetMapping("/action-formations/{id}")
    @Timed
    public ResponseEntity<ActionFormation> getActionFormation(@PathVariable Long id) {
        log.debug("REST request to get ActionFormation : {}", id);
        Optional<ActionFormation> actionFormation = actionFormationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actionFormation);
    }

    /**
     * DELETE  /action-formations/:id : delete the "id" actionFormation.
     *
     * @param id the id of the actionFormation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/action-formations/{id}")
    @Timed
    public ResponseEntity<Void> deleteActionFormation(@PathVariable Long id) {
        log.debug("REST request to delete ActionFormation : {}", id);

        actionFormationRepository.deleteById(id);
        actionFormationSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/action-formations?query=:query : search for the actionFormation corresponding
     * to the query.
     *
     * @param query the query of the actionFormation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/action-formations")
    @Timed
    public ResponseEntity<List<ActionFormation>> searchActionFormations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActionFormations for query {}", query);
        Page<ActionFormation> page = actionFormationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/action-formations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
