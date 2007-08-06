package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License implements HasId {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private LicenseTypes type;
    private String url;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public LicenseTypes getType() {
	return type;
    }

    public void setType(final LicenseTypes type) {
	this.type = type;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(final String url) {
	this.url = url;
    }

}
