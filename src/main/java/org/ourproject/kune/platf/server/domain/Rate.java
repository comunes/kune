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
    ContentDescriptor contentDescriptor;
    @ManyToOne
    User rater;

    Double value;

    public Rate(final User rater, final ContentDescriptor contentDescriptor, final Double value) {
	this.rater = rater;
	this.contentDescriptor = contentDescriptor;
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

    public ContentDescriptor getContentDescriptor() {
	return contentDescriptor;
    }

    public void setContentDescriptor(final ContentDescriptor contentDescriptor) {
	this.contentDescriptor = contentDescriptor;
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
