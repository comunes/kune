/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
public class Content implements HasStakeToken {
    private static final String TOKEN_SEPARATOR = ".";

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
    private Group creator;

    @Basic(optional = false)
    private Long createdOn;

    // @Basic(optional = false)
    private String typeId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Translation> translations;

    @ManyToOne
    private Container container;

    // TODO: lang, languages, etc
    private String locale;

    @OneToOne(cascade = CascadeType.ALL)
    private AccessLists accessLists;

    public Content() {
	translations = new ArrayList<Translation>();
	tags = new ArrayList<Tag>();
	this.createdOn = System.currentTimeMillis();
	this.lastRevision = new Revision();
	accessLists = null;
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

    public Group getCreator() {
	return creator;
    }

    public void setCreator(final Group creator) {
	this.creator = creator;
    }

    public Long getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(final Long createdOn) {
	this.createdOn = createdOn;
    }

    public Container getFolder() {
	return container;
    }

    public void setFolder(final Container container) {
	this.container = container;
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

    public String getTypeId() {
	return typeId;
    }

    public void setTypeId(final String typeId) {
	this.typeId = typeId;
    }

    public boolean hasAccessList() {
	return accessLists != null;
    }

    public String getStateToken() {
	return getFolder().getOwner().getShortName() + TOKEN_SEPARATOR + getFolder().getToolName() + TOKEN_SEPARATOR
		+ getFolder().getId() + TOKEN_SEPARATOR + getId();
    }

}
