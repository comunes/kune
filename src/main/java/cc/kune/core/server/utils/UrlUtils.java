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
package cc.kune.core.server.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UrlUtils {

  private static final Log LOG = LogFactory.getLog(UrlUtils.class);

  public static String noCacheStringSuffix() {
    final String noCache = "&nocache=" + new Date().getTime();
    return noCache;
  }

  public static URL of(final String urlString) {
    URL url = null;
    try {
      url = new URL(urlString);
      return url;
    } catch (final MalformedURLException e) {
      LOG.error("Error creating url with" + urlString);
      e.printStackTrace();
    }
    return url;
  }

}
