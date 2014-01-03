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
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * The Class RosterPresence.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "ofPresence")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RosterPresence {

  /** The offline date. */
  @Column(name = "offlineDate", columnDefinition = "char")
  private String offlineDate;

  /** The username. */
  @Id
  private String username;

  /**
   * Gets the offline date.
   * 
   * @return the offline date
   */
  public Long getOfflineDate() {
    return Long.parseLong(offlineDate.trim());
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
   * Sets the offline date.
   * 
   * @param offlineDate
   *          the new offline date
   */
  public void setOfflineDate(final Long offlineDate) {
    this.offlineDate = offlineDate.toString();
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
}
