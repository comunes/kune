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
package cc.kune.core.client.state;

import cc.kune.common.client.utils.Base64Utils;

public class TokenUtils {

  public static String addRedirect(final String token, final String redirect) {
    return new StringBuffer().append(token).append("(").append(redirect).append(")").toString();
  }

  private static String compose(final String... params) {
    final StringBuffer sb = new StringBuffer();
    for (final String param : params) {
      sb.append(param).append("|");
    }
    return sb.toString().replaceAll("\\|$", "");
  }

  public static String preview(final String token) {
    return addRedirect(SiteTokens.PREVIEW, token);
  }

  public static String subtitle(final String title, final String description, final String redirect) {
    return addRedirect(
        SiteTokens.SUBTITLES,
        compose(Base64Utils.toBase64(title.getBytes()), Base64Utils.toBase64(description.getBytes()),
            redirect));
  }

  public static String tutorial(final String token) {
    return addRedirect(SiteTokens.TUTORIAL, token);
  }
}
