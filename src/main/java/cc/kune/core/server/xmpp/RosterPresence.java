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
package cc.kune.core.server.xmpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ofPresence")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RosterPresence {

  @Column(name = "offlineDate", columnDefinition = "char")
  private String offlineDate;

  @Id
  private String username;

  public Long getOfflineDate() {
    return Long.parseLong(offlineDate.trim());
  }

  public String getUsername() {
    return username;
  }

  public void setOfflineDate(final Long offlineDate) {
    this.offlineDate = offlineDate.toString();
  }

  public void setUsername(final String username) {
    this.username = username;
  }
}
