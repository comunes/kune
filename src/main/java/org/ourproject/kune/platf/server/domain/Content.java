/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.NotNull;
import org.ourproject.kune.platf.client.dto.StateToken;

@Entity
@Table(name = "contents")
@Indexed
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content implements HasStateToken {

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<Tag> tags;

    @OneToOne
    private License license;

    @IndexedEmbedded
    @OneToOne(cascade = { CascadeType.ALL })
    private Revision lastRevision;

    @Basic(optional = false)
    private Long createdOn;

    @Basic(optional = true)
    private Date deletedOn;

    @Basic(optional = false)
    private Date publishedOn;

    private String typeId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ContentTranslation> translations;

    @ManyToOne
    @JoinColumn
    @IndexedEmbedded
    private Container container;

    @IndexedEmbedded
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private I18nLanguage language;

    @OneToOne(cascade = CascadeType.ALL)
    private AccessLists accessLists;

    @IndexedEmbedded
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<User> authors;

    @ContainedIn
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    public Content() {
	translations = new ArrayList<ContentTranslation>();
	authors = new ArrayList<User>();
	tags = new ArrayList<Tag>();
	comments = new HashSet<Comment>();
	this.createdOn = System.currentTimeMillis();
	this.lastRevision = new Revision(this);
	accessLists = null;
	status = ContentStatus.publishedOnline;
    }

    public void addAuthor(final User user) {
	if (!this.authors.contains(user)) {
	    this.authors.add(user);
	}
    }

    public void addComment(final Comment comment) {
	// FIXME: something related with lazy initialization (workaround using
	// size())
	comments.size();
	comments.add(comment);
    }

    public void addRevision(final Revision revision) {
	if (lastRevision == null) {
	    lastRevision = revision;
	} else {
	    revision.setPrevious(lastRevision);
	    lastRevision = revision;
	}
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public List<User> getAuthors() {
	return authors;
    }

    public Set<Comment> getComments() {
	return comments;
    }

    public Container getContainer() {
	return container;
    }

    public Long getCreatedOn() {
	return createdOn;
    }

    public Date getDeletedOn() {
	return deletedOn;
    }

    public Long getId() {
	return id;
    }

    public I18nLanguage getLanguage() {
	return language;
    }

    public Revision getLastRevision() {
	return lastRevision;
    }

    public License getLicense() {
	return license;
    }

    public Group getOwner(final Group group) {
	return container.getOwner();
    }

    public Date getPublishedOn() {
	return publishedOn;
    }

    @Transient
    public StateToken getStateToken() {
	return getContainer().getStateToken().clone().setDocument(getId());
    }

    @Transient
    public String getStateTokenEncoded() {
	return getStateToken().getEncoded();
    }

    public ContentStatus getStatus() {
	return status;
    }

    public List<Tag> getTags() {
	return tags;
    }

    public String getTagsAsString() {
	String tagConcatenated = "";
	for (final Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
	    final Tag tag = iterator.next();
	    tagConcatenated = tagConcatenated + tag.getName();
	    if (iterator.hasNext()) {
		tagConcatenated = tagConcatenated + " ";
	    }
	}
	return tagConcatenated;
    }

    public String getTitle() {
	return lastRevision.getTitle();
    }

    public List<ContentTranslation> getTranslations() {
	return translations;
    }

    public String getTypeId() {
	return typeId;
    }

    public int getVersion() {
	return version;
    }

    public boolean hasAccessList() {
	return accessLists != null;
    }

    public void removeAuthor(final User user) {
	this.authors.remove(user);
    }

    public void setAccessLists(final AccessLists accessLists) {
	this.accessLists = accessLists;
    }

    public void setAuthors(final List<User> authors) {
	this.authors = authors;
    }

    public void setComments(final Set<Comment> comments) {
	this.comments = comments;
    }

    public void setContainer(final Container container) {
	this.container = container;
    }

    public void setCreatedOn(final Long createdOn) {
	this.createdOn = createdOn;
    }

    public void setDeletedOn(final Date date) {
	this.deletedOn = date;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setLanguage(final I18nLanguage language) {
	this.language = language;
    }

    public void setLastRevision(final Revision revision) {
	this.lastRevision = revision;
    }

    public void setLicense(final License license) {
	this.license = license;
    }

    public void setPublishedOn(final Date publishedOn) {
	this.publishedOn = publishedOn;
    }

    public void setStatus(final ContentStatus status) {
	this.status = status;
    }

    public void setTags(final List<Tag> tags) {
	this.tags = tags;
    }

    public void setTranslations(final List<ContentTranslation> translations) {
	this.translations = translations;
    }

    public void setTypeId(final String typeId) {
	this.typeId = typeId;
    }

    public void setVersion(final int version) {
	this.version = version;
    }

}
