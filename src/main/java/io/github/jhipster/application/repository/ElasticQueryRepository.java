package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ElasticQuery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ElasticQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElasticQueryRepository extends JpaRepository<ElasticQuery, Long> {

}
