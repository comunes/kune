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

@Entity
@Indexed
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invitation implements HasId {
  @Basic
  private Long date;
  @ManyToOne
  private User fromUser;
  @Column(unique = true, nullable = false)
  private String hash;
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;
  private String invitedToToken;
  @Enumerated(EnumType.STRING)
  private NotificationType notifType;

  @Column(name = "invitTo")
  private String to;
  @Enumerated(EnumType.STRING)
  private InvitationType type;

  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private Boolean used;

  public Invitation() {
    this(null, null, null, null, null, null);
  }

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

  public Long getDate() {
    return date;
  }

  public User getFrom() {
    return fromUser;
  }

  public User getFromUser() {
    return fromUser;
  }

  public String getHash() {
    return hash;
  }

  @Override
  public Long getId() {
    return id;
  }

  public StateToken getInvitedToToken() {
    return new StateToken(invitedToToken);
  }

  public NotificationType getNotifType() {
    return notifType;
  }

  public String getTo() {
    return to;
  }

  public InvitationType getType() {
    return type;
  }

  public Boolean getUsed() {
    return used;
  }

  public void setDate(final Long date) {
    this.date = date;
  }

  public void setFrom(final User from) {
    this.fromUser = from;
  }

  public void setHash(final String hash) {
    this.hash = hash;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setInvitedToToken(final String invitedToToken) {
    this.invitedToToken = invitedToToken;
  }

  public void setNotifType(final NotificationType notifType) {
    this.notifType = notifType;
  }

  public void setTo(final String to) {
    this.to = to;
  }

  public void setType(final InvitationType type) {
    this.type = type;
  }

  public void setUsed(final Boolean used) {
    this.used = used;
  }

}
