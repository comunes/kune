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

package cc.kune.core.server.manager.file;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

/**
 * The Class CacheUtils is used to set the cache headers in some servlets (avatars, etc)
 *
 */
public class CacheUtils {

  /**
   * Sets the cache.
   *
   * @param resp the resp
   * @param age the age in seconds
   */
  public static void setCache(final HttpServletResponse resp, final long age) {
    Date now = new Date();
    resp.setDateHeader("Date", now.getTime());
    resp.setDateHeader("Expires", now.getTime() + 1000L * age);
    resp.setHeader("Cache-Control", "public, s-maxage=" + age);
  }

  /**
   * Sets the cache to 1 day.
   *
   * @param resp
   */
  public static void setCache1Day(final HttpServletResponse resp) {
    setCache(resp, 86400L);
  }
}
