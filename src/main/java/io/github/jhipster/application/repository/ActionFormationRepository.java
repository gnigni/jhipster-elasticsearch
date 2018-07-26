package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ActionFormation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActionFormation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionFormationRepository extends JpaRepository<ActionFormation, Long> {

}
