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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import cc.kune.domain.utils.DataFieldBridge;

// TODO: Auto-generated Javadoc
/**
 * The Class Revision.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "revisions")
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Revision {
  // http://www.hibernate.org/112.html
  /** The body. */
  @Lob
  @Column(length = 2147483647)
  @Field(index = Index.YES, store = Store.NO)
  @FieldBridge(impl = DataFieldBridge.class)
  char[] body;

  /** The content. */
  @ContainedIn
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn
  private Content content;

  /** The created on. */
  @org.hibernate.annotations.Index(name = "createdOn")
  @Basic(optional = false)
  private Long createdOn;

  /** The editor. */
  @OneToOne
  private User editor;

  /** The id. */
  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  /** The previous. */
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Revision previous;

  /** The title. */
  @Field(index = Index.YES, store = Store.NO)
  String title;

  /** The version. */
  @org.hibernate.annotations.Index(name = "version")
  @Version
  private int version;

  /**
   * Instantiates a new revision.
   */
  public Revision() {
    this(null);
  }

  /**
   * Instantiates a new revision.
   * 
   * @param content
   *          the content
   */
  public Revision(final Content content) {
    this.content = content;
    createdOn = System.currentTimeMillis();
  }

  /**
   * Gets the body.
   * 
   * @return the body
   */
  public char[] getBody() {
    return body;
  }

  /**
   * Gets the content.
   * 
   * @return the content
   */
  public Content getContent() {
    return content;
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
   * Gets the editor.
   * 
   * @return the editor
   */
  public User getEditor() {
    return editor;
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
   * Gets the previous.
   * 
   * @return the previous
   */
  public Revision getPrevious() {
    return previous;
  }

  /**
   * Gets the title.
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
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
   * Checks if is last.
   * 
   * @return true, if is last
   */
  @Transient
  public boolean isLast() {
    return content.getLastRevision().equals(this);
  }

  /**
   * Sets the body.
   * 
   * @param body
   *          the new body
   */
  public void setBody(final String body) {
    this.body = body.toCharArray();
  }

  /**
   * Sets the content.
   * 
   * @param content
   *          the new content
   */
  public void setContent(final Content content) {
    this.content = content;
  }

  /**
   * Sets the created on.
   * 
   * @param modifiedOn
   *          the new created on
   */
  public void setCreatedOn(final Long modifiedOn) {
    this.createdOn = modifiedOn;
  }

  /**
   * Sets the editor.
   * 
   * @param editor
   *          the new editor
   */
  public void setEditor(final User editor) {
    this.editor = editor;
  }

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
   * Sets the previous.
   * 
   * @param previous
   *          the new previous
   */
  public void setPrevious(final Revision previous) {
    this.previous = previous;
  }

  /**
   * Sets the title.
   * 
   * @param title
   *          the new title
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Sets the version.
   * 
   * @param version
   *          the new version
   */
  public void setVersion(final int version) {
    this.version = version;
  }

}
