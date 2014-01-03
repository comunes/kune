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
package cc.kune.core.client.i18n;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nUrlUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nUrlUtils {

  /**
   * Adds the lang param to a url.
   * 
   * @param lang
   *          the lang
   * @param changedUrl
   *          the changed url
   */
  private static void addLangParam(final String lang, final Url changedUrl) {
    changedUrl.add(new UrlParam("locale", lang));
  }

  /**
   * Change lang.
   * 
   * @param url
   *          the url
   * @param lang
   *          the new lang
   * @return the new url
   */
  public static String changeLang(final String url, final String lang) {
    final String[] hashSplitted = url.split("#");
    final String hash = hashSplitted.length > 1 ? hashSplitted[1] : "";
    String query = hashSplitted.length >= 1 ? hashSplitted[0] : (url.equals("#") ? "" : url);
    query = query.startsWith("?") ? query.substring(1) : query;
    final String[] params = query.split("&");
    final Url changedUrl = new Url("");
    if (!query.contains("locale")) {
      addLangParam(lang, changedUrl);
    }
    for (final String param : params) {
      if (TextUtils.notEmpty(param)) {
        final String[] pair = param.split("=");
        if (pair[0].equals("locale")) {
          addLangParam(lang, changedUrl);
        } else {
          changedUrl.add(new UrlParam(pair[0], pair[1]));
        }
      }
    }
    return changedUrl.toString() + (url.contains("#") ? "#" + hash : "");
  }

}
