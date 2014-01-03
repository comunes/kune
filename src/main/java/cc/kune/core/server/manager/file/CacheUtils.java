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

package cc.kune.core.server.manager.file;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class CacheUtils is used to set the cache headers in some servlets
 * (avatars, etc).
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CacheUtils {

  /**
   * Sets the cache.
   * 
   * @param resp
   *          the resp
   * @param age
   *          the age in seconds
   */
  public static void setCache(final HttpServletResponse resp, final long age) {
    final Date now = new Date();
    resp.setDateHeader("Date", now.getTime());
    resp.setDateHeader("Expires", now.getTime() + 1000L * age);
    resp.setHeader("Cache-Control", "public, s-maxage=" + age);
  }

  /**
   * Sets the cache to 1 day.
   * 
   * @param resp
   *          the new cache1 day
   */
  public static void setCache1Day(final HttpServletResponse resp) {
    setCache(resp, 86400L);
  }
}
