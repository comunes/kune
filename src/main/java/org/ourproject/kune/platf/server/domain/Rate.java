package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Content content;
    @ManyToOne
    User rater;

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

}
