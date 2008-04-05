
package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Range;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "rates", uniqueConstraints = { @UniqueConstraint(columnNames = { "content_id", "rater_id" }) })
public class Rate {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Content content;

    @ManyToOne
    User rater;

    @Range(min = 0, max = 5)
    Double value;

    public Rate(final User rater, final Content content, final Double value) {
        this.rater = rater;
        this.content = content;
        this.value = value;
    }

    public Rate() {
        this(null, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(final User rater) {
        this.rater = rater;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    @Finder(query = "SELECT r FROM Rate r WHERE r.rater = :user AND r.content = :content")
    public Rate find(@Named("user")
    final User user, @Named("content")
    final Content content) {
        return null;
    }

    @Finder(query = "SELECT AVG(r.value) FROM Rate r WHERE r.content = :content")
    public Double calculateRate(@Named("content")
    final Content content) {
        return null;
    }

    @Finder(query = "SELECT count(*) FROM Rate r WHERE r.content = :content")
    public Long calculateRateNumberOfUsers(@Named("content")
    final Content content) {
        return null;
    }

}
