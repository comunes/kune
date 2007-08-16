package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "contents")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content implements HasContent {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<Tag> tags;
    @OneToOne
    private License license;

    @OneToOne(cascade = { CascadeType.ALL })
    private Revision lastRevision;

    @OneToOne
    private User creator;

    @Basic(optional = false)
    private Long createdOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Translation> translations;

    @ManyToOne
    private Folder folder;

    // TODO: lang, languages, etc
    private String locale;

    @OneToOne(cascade = CascadeType.ALL)
    private AccessLists accessLists;

    public Content() {
	translations = new ArrayList<Translation>();
	tags = new ArrayList<Tag>();
	this.createdOn = System.currentTimeMillis();
	this.lastRevision = new Revision();
    }

    @Finder(query = "select AVG(r.value) from Rate r where r.content = :descriptor")
    public Double calculateRate(@Named("descriptor")
    final Content descriptor) {
	return null;
    }

    @Finder(query = "select count(*) from Rate r where r.content = :descriptor")
    public Integer calculateRateNumberOfUsers(@Named("descriptor")
    final Content descriptor) {
	return null;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public int getVersion() {
	return version;
    }

    public void setVersion(final int version) {
	this.version = version;
    }

    public String getLocale() {
	return locale;
    }

    public void setLocale(final String locale) {
	this.locale = locale;
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessLists accessLists) {
	this.accessLists = accessLists;
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

    public Revision getLastRevision() {
	return lastRevision;
    }

    public void setLastRevision(final Revision revision) {
	this.lastRevision = revision;
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

    public Folder getFolder() {
	return folder;
    }

    public void setFolder(final Folder folder) {
	this.folder = folder;
    }

    public void addRevision(final Revision revision) {
	if (lastRevision == null) {
	    lastRevision = revision;
	} else {
	    revision.setPrevious(lastRevision);
	    lastRevision = revision;
	}
    }

    public String getTitle() {
	return lastRevision.getData().getTitle();
    }
}
