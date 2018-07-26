package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ActionFormation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActionFormation entity.
 */
public interface ActionFormationSearchRepository extends ElasticsearchRepository<ActionFormation, Long> {
}
