package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aliases")
public class Alias implements HasId {
    @Id
    @GeneratedValue
    private Long id;
    private String locale;
    private String name;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getLocale() {
	return locale;
    }

    public void setLocale(final String locale) {
	this.locale = locale;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

}
