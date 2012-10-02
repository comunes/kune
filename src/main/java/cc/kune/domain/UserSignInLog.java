/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

/**
 * The Class UserSignInLog records the user signins for stats purposes
 */
@Entity
@Indexed
@Table(name = "user_signin_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSignInLog implements HasId {

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
  public UserSignInLog(User user, String ipAddress, String userAgent, String hash) {
    this.user = user;
    this.ipAddress = ipAddress;
    long now = System.currentTimeMillis();
    this.signInDate = now;
    this.userAgent = userAgent;
    this.hash = hash;
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
   * Gets the user agent.
   * 
   * @return the user agent
   */
  public String getUserAgent() {
    return userAgent;
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
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The user. */
  @IndexedEmbedded
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private final User user;

  /** The ip address of the client. */
  @Column
  private final String ipAddress;

  /** The sign in date. */
  @Basic
  @Column(nullable = false)
  private final Long signInDate;

  /** The user agent. */
  @Field(index = Index.YES)
  @Column
  private final String userAgent;

  /** The hash. */
  @Field(index = Index.YES)
  @Column
  private final String hash;

}
