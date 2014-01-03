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
package cc.kune.core.server.notifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalUserDestinationProvider.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LocalUserDestinationProvider implements DestinationProvider {

  /** The list. */
  private final List<Addressee> list;

  /**
   * Instantiates a new simple destination provider (used for single
   * notifications like add/remove participant emails).
   * 
   * @param user
   *          the user to send the email or other type of notification
   */
  public LocalUserDestinationProvider(final User user) {
    list = Arrays.asList(Addressee.build(user));
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final LocalUserDestinationProvider other = (LocalUserDestinationProvider) obj;
    if (list == null) {
      if (other.list != null) {
        return false;
      }
    } else if (!list.equals(other.list)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.notifier.DestinationProvider#getDest()
   */
  @Override
  public Collection<Addressee> getDest() {
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((list == null) ? 0 : list.hashCode());
    return result;
  }

}
