package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ElasticQuery.
 */
@Entity
@Table(name = "elastic_query")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "elasticquery")
public class ElasticQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quoi")
    private String quoi;

    @Column(name = "ou")
    private String ou;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuoi() {
        return quoi;
    }

    public ElasticQuery quoi(String quoi) {
        this.quoi = quoi;
        return this;
    }

    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    public String getOu() {
        return ou;
    }

    public ElasticQuery ou(String ou) {
        this.ou = ou;
        return this;
    }

    public void setOu(String ou) {
        this.ou = ou;
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
        ElasticQuery elasticQuery = (ElasticQuery) o;
        if (elasticQuery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elasticQuery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElasticQuery{" +
            "id=" + getId() +
            ", quoi='" + getQuoi() + "'" +
            ", ou='" + getOu() + "'" +
            "}";
    }
}
