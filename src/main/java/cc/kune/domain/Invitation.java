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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class Invitation.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invitation implements HasId {

  /** The date. */
  @Basic
  private Long date;

  /** The from user. */
  @ManyToOne
  private User fromUser;

  /** The hash. */
  @Column(unique = true, nullable = false)
  private String hash;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The invited to token. */
  private String invitedToToken;

  /** The notif type. */
  @Enumerated(EnumType.STRING)
  private NotificationType notifType;

  /** The to. */
  @Column(name = "invitTo")
  private String to;

  /** The type. */
  @Enumerated(EnumType.STRING)
  private InvitationType type;

  /** The used. */
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private Boolean used;

  /**
   * Instantiates a new invitation.
   */
  public Invitation() {
    this(null, null, null, null, null, null);
  }

  /**
   * Instantiates a new invitation.
   * 
   * @param from
   *          the from
   * @param hash
   *          the hash
   * @param invitedToToken
   *          the invited to token
   * @param notifType
   *          the notif type
   * @param to
   *          the to
   * @param type
   *          the type
   */
  public Invitation(final User from, final String hash, final String invitedToToken,
      final NotificationType notifType, final String to, final InvitationType type) {
    this.date = System.currentTimeMillis();
    this.fromUser = from;
    this.hash = hash;
    this.invitedToToken = invitedToToken;
    this.notifType = notifType;
    this.to = to;
    this.type = type;
    this.used = false;
  }

  /**
   * Gets the date.
   * 
   * @return the date
   */
  public Long getDate() {
    return date;
  }

  /**
   * Gets the from.
   * 
   * @return the from
   */
  public User getFrom() {
    return fromUser;
  }

  /**
   * Gets the from user.
   * 
   * @return the from user
   */
  public User getFromUser() {
    return fromUser;
  }

  /**
   * Gets the hash.
   * 
   * @return the hash
   */
  public String getHash() {
    return hash;
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
   * Gets the invited to token.
   * 
   * @return the invited to token
   */
  public StateToken getInvitedToToken() {
    return new StateToken(invitedToToken);
  }

  /**
   * Gets the notif type.
   * 
   * @return the notif type
   */
  public NotificationType getNotifType() {
    return notifType;
  }

  /**
   * Gets the to.
   * 
   * @return the to
   */
  public String getTo() {
    return to;
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public InvitationType getType() {
    return type;
  }

  /**
   * Gets the used.
   * 
   * @return the used
   */
  public Boolean getUsed() {
    return used;
  }

  /**
   * Sets the date.
   * 
   * @param date
   *          the new date
   */
  public void setDate(final Long date) {
    this.date = date;
  }

  /**
   * Sets the from.
   * 
   * @param from
   *          the new from
   */
  public void setFrom(final User from) {
    this.fromUser = from;
  }

  /**
   * Sets the hash.
   * 
   * @param hash
   *          the new hash
   */
  public void setHash(final String hash) {
    this.hash = hash;
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
   * Sets the invited to token.
   * 
   * @param invitedToToken
   *          the new invited to token
   */
  public void setInvitedToToken(final String invitedToToken) {
    this.invitedToToken = invitedToToken;
  }

  /**
   * Sets the notif type.
   * 
   * @param notifType
   *          the new notif type
   */
  public void setNotifType(final NotificationType notifType) {
    this.notifType = notifType;
  }

  /**
   * Sets the to.
   * 
   * @param to
   *          the new to
   */
  public void setTo(final String to) {
    this.to = to;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          the new type
   */
  public void setType(final InvitationType type) {
    this.type = type;
  }

  /**
   * Sets the used.
   * 
   * @param used
   *          the new used
   */
  public void setUsed(final Boolean used) {
    this.used = used;
  }

}
