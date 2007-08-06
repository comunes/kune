package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "translations")
public class Translation implements HasId {
    @Id
    @GeneratedValue
    private Long id;
    private String locale;
    private Long contentId;

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

    public Long getContentId() {
	return contentId;
    }

    public void setContentId(final Long contentId) {
	this.contentId = contentId;
    }

}
