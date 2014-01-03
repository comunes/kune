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
import javax.persistence.Column;
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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSignInLog records the user signins for stats purposes.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "user_signin_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSignInLog implements HasId {

  /** The hash. */
  @Field(index = Index.YES)
  @Column
  private final String hash;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The ip address of the client. */
  @org.hibernate.annotations.Index(name = "ipAddress")
  @Column
  private final String ipAddress;

  /** The sign in date. */

  @org.hibernate.annotations.Index(name = "signInDate")
  @Basic
  @Column(nullable = false)
  private final Long signInDate;

  /** The user. */
  @IndexedEmbedded
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private final User user;

  /** The user agent. */

  @org.hibernate.annotations.Index(name = "userAgent")
  @Field(index = Index.YES)
  @Column
  private final String userAgent;

  /**
   * Instantiates a new user sign in log.
   * 
   * @param user
   *          the user
   * @param ipAddress
   *          the ip address of the client
   * @param userAgent
   *          the user agent of the client
   * @param hash
   *          the session hash
   */
  public UserSignInLog(final User user, final String ipAddress, final String userAgent, final String hash) {
    this.user = user;
    this.ipAddress = ipAddress;
    final long now = System.currentTimeMillis();
    this.signInDate = now;
    this.userAgent = userAgent;
    this.hash = hash;
  }

  /**
   * Gets the hash.
   * 
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the ip address.
   * 
   * @return the ip address
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Gets the sign in date.
   * 
   * @return the sign in date
   */
  public Long getSignInDate() {
    return signInDate;
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
   * Gets the user agent.
   * 
   * @return the user agent
   */
  public String getUserAgent() {
    return userAgent;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  @Override
  public void setId(final Long id) {
    this.id = id;
  }

}
