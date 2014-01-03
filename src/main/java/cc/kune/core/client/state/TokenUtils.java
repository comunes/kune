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
package cc.kune.core.client.state;

import cc.kune.common.client.utils.Base64Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class TokenUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TokenUtils {

  /**
   * Adds the redirect.
   * 
   * @param token
   *          the token
   * @param redirect
   *          the redirect
   * @return the string
   */
  public static String addRedirect(final String token, final String redirect) {
    return new StringBuffer().append(token).append("(").append(redirect).append(")").toString();
  }

  /**
   * Compose.
   * 
   * @param params
   *          the params
   * @return the string
   */
  private static String compose(final String... params) {
    final StringBuffer sb = new StringBuffer();
    for (final String param : params) {
      sb.append(param).append("|");
    }
    return sb.toString().replaceAll("\\|$", "");
  }

  /**
   * Preview.
   * 
   * @param token
   *          the token
   * @return the string
   */
  public static String preview(final String token) {
    return addRedirect(SiteTokens.PREVIEW, token);
  }

  /**
   * Subtitle.
   * 
   * @param title
   *          the title
   * @param description
   *          the description
   * @param redirect
   *          the redirect
   * @return the string
   */
  public static String subtitle(final String title, final String description, final String redirect) {
    return addRedirect(
        SiteTokens.SUBTITLES,
        compose(Base64Utils.toBase64(title.getBytes()), Base64Utils.toBase64(description.getBytes()),
            redirect));
  }

  /**
   * Tutorial.
   * 
   * @param token
   *          the token
   * @return the string
   */
  public static String tutorial(final String token) {
    return addRedirect(SiteTokens.TUTORIAL, token);
  }

}
