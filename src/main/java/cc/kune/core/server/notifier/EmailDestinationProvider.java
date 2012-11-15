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
package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.List;

import cc.kune.domain.I18nLanguage;

public class EmailDestinationProvider implements DestinationProvider {
  public static EmailDestinationProvider build(final I18nLanguage defaultLanguage,
      final String... emails) {
    return new EmailDestinationProvider(defaultLanguage, emails);
  }

  private final List<Addressee> list;

  /**
   * Instantiates a new destination provider (used for single notifications like
   * add/remove participant emails).
   * 
   * @param email
   *          the user to send the email or other type of notification
   * @param lang
   *          the lang of the user
   */
  public EmailDestinationProvider(final I18nLanguage lang, final String... emails) {
    list = Addressee.build(lang, emails);
  }

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
    final EmailDestinationProvider other = (EmailDestinationProvider) obj;
    if (list == null) {
      if (other.list != null) {
        return false;
      }
    } else if (!list.equals(other.list)) {
      return false;
    }
    return true;
  }

  @Override
  public Collection<Addressee> getDest() {
    return list;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((list == null) ? 0 : list.hashCode());
    return result;
  }

}
