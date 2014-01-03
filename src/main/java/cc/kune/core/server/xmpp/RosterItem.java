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
package cc.kune.core.server.xmpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * The Class RosterItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "ofRoster")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RosterItem {

  /** The id. */
  @Id
  @GeneratedValue
  @Column(name = "rosterID")
  private Long id;

  /** The jid. */
  private String jid;

  /** The nick. */
  private String nick;

  /** The sub status. */
  @Column(name = "sub")
  private byte subStatus;

  /** The username. */
  private String username;

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the jid.
   * 
   * @return the jid
   */
  public String getJid() {
    return jid;
  }

  /**
   * Gets the nick.
   * 
   * @return the nick
   */
  public String getNick() {
    return nick;
  }

  /**
   * <p>
   * 0 - None subscribed
   * <p>
   * <p>
   * 1 - The roster owner has a subscription to the roster item's presence.
   * </p>
   * <p>
   * 2 - The roster item has a subscription to the roster owner's presence.
   * </p>
   * <p>
   * 3 - The roster item and owner have a mutual subscription.
   * </p>
   * 
   * @return the sub status
   */
  public byte getSubStatus() {
    return subStatus;
  }

  /**
   * Gets the username.
   * 
   * @return the username
   */
  public String getUsername() {
    return username;
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
   * Sets the jid.
   * 
   * @param jid
   *          the new jid
   */
  public void setJid(final String jid) {
    this.jid = jid;
  }

  /**
   * Sets the nick.
   * 
   * @param nick
   *          the new nick
   */
  public void setNick(final String nick) {
    this.nick = nick;
  }

  /**
   * Sets the sub status.
   * 
   * @param subStatus
   *          the new sub status
   */
  public void setSubStatus(final byte subStatus) {
    this.subStatus = subStatus;
  }

  /**
   * Sets the username.
   * 
   * @param username
   *          the new username
   */
  public void setUsername(final String username) {
    this.username = username;
  }
  // protected RecvType recvStatus;
  //
  // protected Set<String> sharedGroups = new HashSet<String>();
  //
  // protected Set<String> invisibleSharedGroups = new HashSet<String>();
  //
  //
  // protected AskType askStatus;
}
