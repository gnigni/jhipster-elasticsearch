package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActionFormation.
 */
@Entity
@Table(name = "action_formation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "actionformation")
public class ActionFormation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule_formation")
    private String intituleFormation;

    @Column(name = "description")
    private String description;

    @Column(name = "nombre_heures_total")
    private Integer nombreHeuresTotal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleFormation() {
        return intituleFormation;
    }

    public ActionFormation intituleFormation(String intituleFormation) {
        this.intituleFormation = intituleFormation;
        return this;
    }

    public void setIntituleFormation(String intituleFormation) {
        this.intituleFormation = intituleFormation;
    }

    public String getDescription() {
        return description;
    }

    public ActionFormation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNombreHeuresTotal() {
        return nombreHeuresTotal;
    }

    public ActionFormation nombreHeuresTotal(Integer nombreHeuresTotal) {
        this.nombreHeuresTotal = nombreHeuresTotal;
        return this;
    }

    public void setNombreHeuresTotal(Integer nombreHeuresTotal) {
        this.nombreHeuresTotal = nombreHeuresTotal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActionFormation actionFormation = (ActionFormation) o;
        if (actionFormation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionFormation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionFormation{" +
            "id=" + getId() +
            ", intituleFormation='" + getIntituleFormation() + "'" +
            ", description='" + getDescription() + "'" +
            ", nombreHeuresTotal=" + getNombreHeuresTotal() +
            "}";
    }
}
