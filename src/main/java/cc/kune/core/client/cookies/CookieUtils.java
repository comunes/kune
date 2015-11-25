/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.client.cookies;

import java.util.Date;

import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.shared.SessionConstants;

public class CookieUtils {

  /**
   * Inspired in: {@link http
   * ://developers.livechatinc.com/blog/setting-cookies-to
   * -subdomains-in-javascript/}
   **/
  public static String getDomain() {
    final String hostname = WindowUtils.getHostName();

    // noDot, so hostname is "locahost" or similar
    final boolean noDot = hostname.contains(".");

    // If hostname is a domain.something, set the cookie to .domain.something
    // allowing subdomains
    return noDot || hostname.matches(TextUtils.IPADDRESS_PATTERN) ? hostname : "." + hostname;
  }

  public static Date inDays(final int days) {
    return new Date(System.currentTimeMillis() + SessionConstants.A_DAY * days);
  }

  public static Date expireInYears() {
    return inDays(365 * 10);
  }

}