package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contents")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ContentDescriptor extends Ajo implements HasContent {
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<Tag> tags;
    @OneToOne
    private License license;
    private Rate rate;
    @OneToOne
    private Revision revision;

    @OneToOne
    Container container;

    @OneToOne
    private User creator;

    @Basic(optional = false)
    private Long createdOn;

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

    public User getCreator() {
	return creator;
    }

    public void setCreator(final User creator) {
	this.creator = creator;
    }

    public Long getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(final Long createdOn) {
	this.createdOn = createdOn;
    }

}
