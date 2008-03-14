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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "contents")
@Indexed
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Content implements HasStateToken {
    private static final String TOKEN_SEPARATOR = ".";

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

    private boolean markForDeletion;

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

    public Content() {
        translations = new ArrayList<ContentTranslation>();
        authors = new ArrayList<User>();
        tags = new ArrayList<Tag>();
        this.createdOn = System.currentTimeMillis();
        this.lastRevision = new Revision(this);
        accessLists = null;
        markForDeletion = false;
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

    public List<ContentTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(final List<ContentTranslation> translations) {
        this.translations = translations;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final Long createdOn) {
        this.createdOn = createdOn;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(final Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(final Container container) {
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
        return lastRevision.getTitle();
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

    public I18nLanguage getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    @Transient
    public String getStateToken() {
        return getContainer().getOwner().getShortName() + TOKEN_SEPARATOR + getContainer().getToolName()
                + TOKEN_SEPARATOR + getContainer().getId() + TOKEN_SEPARATOR + getId();
    }

    public List<User> getAuthors() {
        return authors;
    }

    public void setAuthors(final List<User> authors) {
        this.authors = authors;
    }

    public void addAuthor(final User user) {
        if (!this.authors.contains(user)) {
            this.authors.add(user);
        }
    }

    public void removeAuthor(final User user) {
        this.authors.remove(user);
    }

    public String getTagsAsString() {
        String tagConcatenated = "";
        for (Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
            Tag tag = iterator.next();
            tagConcatenated = tagConcatenated + tag.getName();
            if (iterator.hasNext()) {
                tagConcatenated = tagConcatenated + " ";
            }
        }
        return tagConcatenated;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(final Date date) {
        this.deletedOn = date;
    }

    public boolean isMarkForDeletion() {
        return markForDeletion;
    }

    public void setMarkForDeletion(final boolean markForDeletion) {
        this.markForDeletion = markForDeletion;
    }

    public Group getOwner(final Group group) {
        return container.getOwner();
    }

}
