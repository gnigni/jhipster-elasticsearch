package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ElasticQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ElasticQuery entity.
 */
public interface ElasticQuerySearchRepository extends ElasticsearchRepository<ElasticQuery, Long> {
}
