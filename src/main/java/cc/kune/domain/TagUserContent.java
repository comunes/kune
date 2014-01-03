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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class TagUserContent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "tag_user_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TagUserContent implements HasId {

  /** The content. */
  @IndexedEmbedded
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Content content;

  /** The created on. */
  @org.hibernate.annotations.Index(name = "createdOn")
  @Basic(optional = false)
  private final Long createdOn;

  /** The id. */
  @Id
  @GeneratedValue
  @DocumentId
  private Long id;

  /** The tag. */
  @IndexedEmbedded
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Tag tag;

  /** The user. */
  @IndexedEmbedded
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  /**
   * Instantiates a new tag user content.
   */
  public TagUserContent() {
    this(null, null, null);
  }

  /**
   * Instantiates a new tag user content.
   * 
   * @param tag
   *          the tag
   * @param user
   *          the user
   * @param content
   *          the content
   */
  public TagUserContent(final Tag tag, final User user, final Content content) {
    this.tag = tag;
    this.user = user;
    this.content = content;
    this.createdOn = System.currentTimeMillis();
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#getId()
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the tag.
   * 
   * @return the tag
   */
  public Tag getTag() {
    return tag;
  }

  /**
   * Gets the user.
   * 
   * @return the user
   */
  public User getUser() {
    return user;
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#setId(java.lang.Long)
   */
  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the tag.
   * 
   * @param tag
   *          the new tag
   */
  public void setTag(final Tag tag) {
    this.tag = tag;
  }

  /**
   * Sets the user.
   * 
   * @param user
   *          the new user
   */
  public void setUser(final User user) {
    this.user = user;
  }
}
