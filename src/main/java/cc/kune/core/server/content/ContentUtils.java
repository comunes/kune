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
 \*/
package cc.kune.core.server.content;

import cc.kune.core.client.errors.ContentNotFoundException;

public final class ContentUtils {

  // @PMD:REVIEWED:ShortVariable: by vjrj on 21/05/09 14:05
  public static Long parseId(final String id) throws ContentNotFoundException {
    try {
      return Long.valueOf(id);
    } catch (final NumberFormatException e) {
      // @PMD:REVIEWED:PreserveStackTrace: by vjrj on 21/05/09 14:05
      throw new ContentNotFoundException();
    }
  }

  private ContentUtils() {
  }
}
