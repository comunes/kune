/*
 *

 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import cc.kune.barters.shared.BartersToolConstants;
import cc.kune.blogs.shared.BlogsToolConstants;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.domain.utils.HasStateToken;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.lists.shared.ListsToolConstants;
import cc.kune.tasks.shared.TasksToolConstants;
import cc.kune.wiki.shared.WikiToolConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class Content.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "contents")
@Indexed
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content implements HasStateToken {

  /** The Constant GROUP. */
  public static final String GROUP = "group";

  /** The Constant MIMETYPE. */
  public static final String MIMETYPE = "mimetype";

  /** The Constant NO_CONTENT. */
  public static final Content NO_CONTENT = new Content();

  /** The Constant TITLE. */
  public static final String TITLE = "title";

  /** The access lists. */
  @OneToOne(cascade = CascadeType.ALL)
  private AccessLists accessLists;

  /** The authors. */
  @IndexedEmbedded
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private List<User> authors;

  /** The container. */
  @ManyToOne
  @JoinColumn
  @IndexedEmbedded
  private Container container;

  /** The created on. */
  @org.hibernate.annotations.Index(name = "createdOn")
  @Basic(optional = false)
  private Long createdOn;

  /** The deleted on. */
  @org.hibernate.annotations.Index(name = "deletedOn")
  @Basic(optional = true)
  private Date deletedOn;

  /** filename if is an uploaded content. */
  private String filename;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  // @PMD:REVIEWED:ShortVariable: by vjrj on 21/05/09 15:28
  private Long id;

  /** The language. */
  @IndexedEmbedded
  @Fetch(FetchMode.JOIN)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private I18nLanguage language;

  /** The last revision. */
  @IndexedEmbedded
  @OneToOne(cascade = { CascadeType.ALL })
  private Revision lastRevision;

  /** The license. */
  @OneToOne
  private License license;

  /** The mime type. */
  @IndexedEmbedded
  @Embedded
  private BasicMimeType mimeType;

  /** The modified on. */
  @org.hibernate.annotations.Index(name = "modifiedOn")
  @Basic(optional = true)
  private Long modifiedOn;

  /** The published on. */
  @org.hibernate.annotations.Index(name = "publishedOn")
  @Basic(optional = true)
  private Date publishedOn;

  /** The status. */
  @org.hibernate.annotations.Index(name = "status")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ContentStatus status;

  /** The translations. */
  @OneToMany(cascade = CascadeType.ALL)
  private List<ContentTranslation> translations;

  // @NotNull??
  /** The type id. */
  private String typeId;

  /** The version. */
  @org.hibernate.annotations.Index(name = "version")
  @Version
  private Integer version;

  /** The wave id. */
  @org.hibernate.annotations.Index(name = "waveId")
  private String waveId;

  /**
   * Instantiates a new content.
   */
  public Content() {
    translations = new ArrayList<ContentTranslation>();
    authors = new ArrayList<User>();
    createdOn = System.currentTimeMillis();
    modifiedOn = System.currentTimeMillis();
    lastRevision = new Revision(this);
    accessLists = null;
    status = ContentStatus.editingInProgress;
  }

  /**
   * Adds the author.
   * 
   * @param user
   *          the user
   */
  public void addAuthor(final User user) {
    if (!this.authors.contains(user)) {
      this.authors.add(user);
    }
  }

  /**
   * Adds the revision.
   * 
   * @param revision
   *          the revision
   */
  public void addRevision(final Revision revision) {
    if (lastRevision == null) {
      lastRevision = revision;
    } else {
      revision.setPrevious(lastRevision);
      lastRevision = revision;
    }
  }

  /**
   * Authors clear.
   */
  public void authorsClear() {
    authors.clear();
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  @Transient
  public AccessLists getAccessLists() {
    return hasAccessList() ? accessLists : getContainer().getAccessLists();
  }

  /**
   * Gets the authors.
   * 
   * @return the authors
   */
  public List<User> getAuthors() {
    return authors;
  }

  /**
   * Gets the container.
   * 
   * @return the container
   */
  public Container getContainer() {
    return container;
  }

  /**
   * Gets the created on.
   * 
   * @return the created on
   */
  public Long getCreatedOn() {
    return createdOn;
  }

  /**
   * Gets the deleted on.
   * 
   * @return the deleted on
   */
  public Date getDeletedOn() {
    return deletedOn;
  }

  /**
   * Gets the filename.
   * 
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguage getLanguage() {
    return language;
  }

  /**
   * Gets the last revision.
   * 
   * @return the last revision
   */
  public Revision getLastRevision() {
    return lastRevision;
  }

  /**
   * Gets the license.
   * 
   * @return the license
   */
  public License getLicense() {
    return license;
  }

  /**
   * Gets the mime type.
   * 
   * @return the mime type
   */
  public BasicMimeType getMimeType() {
    return mimeType;
  }

  /**
   * Gets the modified on.
   * 
   * @return the modified on
   */
  public Long getModifiedOn() {
    return modifiedOn;
  }

  /**
   * Gets the owner.
   * 
   * @param group
   *          the group
   * @return the owner
   */
  public Group getOwner(final Group group) {
    return container.getOwner();
  }

  /**
   * Gets the published on.
   * 
   * @return the published on
   */
  public Date getPublishedOn() {
    return publishedOn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasStateToken#getStateToken()
   */
  @Override
  @Transient
  public StateToken getStateToken() {
    return getContainer().getStateToken().copy().setDocumentL(getId());
  }

  /**
   * Gets the state token encoded.
   * 
   * @return the state token encoded
   */
  @Transient
  public String getStateTokenEncoded() {
    return getStateToken().getEncoded();
  }

  /**
   * Gets the status.
   * 
   * @return the status
   */
  public ContentStatus getStatus() {
    return status;
  }

  /**
   * Gets the title.
   * 
   * @return the title
   */
  public String getTitle() {
    return lastRevision.getTitle();
  }

  /**
   * Gets the translations.
   * 
   * @return the translations
   */
  public List<ContentTranslation> getTranslations() {
    return translations;
  }

  /**
   * Gets the type id.
   * 
   * @return the type id
   */
  public String getTypeId() {
    return typeId;
  }

  /**
   * Gets the version.
   * 
   * @return the version
   */
  public int getVersion() {
    return version;
  }

  /**
   * Gets the wave id.
   * 
   * @return the wave id
   */
  public String getWaveId() {
    return waveId;
  }

  /**
   * Checks for access list.
   * 
   * @return true, if successful
   */
  @Transient
  public boolean hasAccessList() {
    return accessLists != null;
  }

  /**
   * Checks if is wave.
   * 
   * @return true, if is wave
   */
  @Transient
  public boolean isWave() {
    return (typeId.equals(DocsToolConstants.TYPE_DOCUMENT))
        || typeId.equals(TasksToolConstants.TYPE_TASK)
        || typeId.equals(ListsToolConstants.TYPE_POST)
        || typeId.equals(WikiToolConstants.TYPE_WIKIPAGE)
        || (typeId.equals(BlogsToolConstants.TYPE_POST)
            || typeId.equals(BartersToolConstants.TYPE_BARTER) || typeId.equals(EventsToolConstants.TYPE_MEETING));
  }

  /**
   * Removes the author.
   * 
   * @param user
   *          the user
   */
  public void removeAuthor(final User user) {
    this.authors.remove(user);
  }

  /**
   * Sets the access lists.
   * 
   * @param accessLists
   *          the new access lists
   */
  public void setAccessLists(final AccessLists accessLists) {
    this.accessLists = accessLists;
  }

  /**
   * Sets the authors.
   * 
   * @param authors
   *          the new authors
   */
  public void setAuthors(final List<User> authors) {
    this.authors = authors;
  }

  /**
   * Sets the container.
   * 
   * @param container
   *          the new container
   */
  public void setContainer(final Container container) {
    this.container = container;
  }

  /**
   * Sets the created on.
   * 
   * @param createdOn
   *          the new created on
   */
  public void setCreatedOn(final Long createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Sets the deleted on.
   * 
   * @param date
   *          the new deleted on
   */
  public void setDeletedOn(final Date date) {
    this.deletedOn = date;
  }

  /**
   * Sets the filename.
   * 
   * @param filename
   *          the new filename
   */
  public void setFilename(final String filename) {
    this.filename = filename;
  }

  // @PMD:REVIEWED:ShortVariable: by vjrj on 21/05/09 15:28
  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  /**
   * Sets the last revision.
   * 
   * @param revision
   *          the new last revision
   */
  public void setLastRevision(final Revision revision) {
    this.lastRevision = revision;
  }

  /**
   * Sets the license.
   * 
   * @param license
   *          the new license
   */
  public void setLicense(final License license) {
    this.license = license;
  }

  /**
   * Sets the mime type.
   * 
   * @param mimeType
   *          the new mime type
   */
  public void setMimeType(final BasicMimeType mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Sets the modified on.
   * 
   * @param modifiedOn
   *          the new modified on
   */
  public void setModifiedOn(final Long modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  /**
   * Sets the published on.
   * 
   * @param publishedOn
   *          the new published on
   */
  public void setPublishedOn(final Date publishedOn) {
    this.publishedOn = publishedOn;
  }

  /**
   * Sets the status.
   * 
   * @param status
   *          the new status
   */
  public void setStatus(final ContentStatus status) {
    this.status = status;
  }

  /**
   * Sets the translations.
   * 
   * @param translations
   *          the new translations
   */
  public void setTranslations(final List<ContentTranslation> translations) {
    this.translations = translations;
  }

  /**
   * Sets the type id.
   * 
   * @param typeId
   *          the new type id
   */
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

  /**
   * Sets the version.
   * 
   * @param version
   *          the new version
   */
  public void setVersion(final Integer version) {
    this.version = version;
  }

  /**
   * Sets the wave id.
   * 
   * @param waveId
   *          the new wave id
   */
  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Content[(" + getStateTokenEncoded() + "): " + getTitle() + "]";
  }

}
