package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contents")
public class ContentDescriptor extends Ajo implements HasContent {
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<Tag> tags;
    @OneToOne
    private License license;
    private Rate rate;
    @OneToOne
    private Revision revision;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Translation> translations;

    public ContentDescriptor() {
	translations = new ArrayList<Translation>();
	tags = new ArrayList<Tag>();
    }

    public List<Tag> getTags() {
	return tags;
    }

    public void setTags(final List<Tag> tags) {
	this.tags = tags;
    }

    public License getLicense() {
	return license;
    }

    public void setLicense(final License license) {
	this.license = license;
    }

    public Rate getRate() {
	return rate;
    }

    public void setRate(final Rate rate) {
	this.rate = rate;
    }

    public Revision getRevision() {
	return revision;
    }

    public void setRevision(final Revision revision) {
	this.revision = revision;
    }

    public List<Translation> getTranslations() {
	return translations;
    }

    public void setTranslations(final List<Translation> translations) {
	this.translations = translations;
    }

}
