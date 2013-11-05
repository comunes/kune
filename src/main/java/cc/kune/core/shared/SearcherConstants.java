/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.shared;

public final class SearcherConstants {

  public final static String FROM_LANGUAGE_PARAM = "from";
  // public final static String CONTENT_DATA_PROXY_URL =
  // "/ws/json/ContentJSONService/search";
  // public final static String CONTENT_TEMPLATE_TEXT_PREFIX =
  // "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img src=\"";
  // public final static String CONTENT_TEMPLATE_TEXT_SUFFIX =
  // "\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>";
  public final static String GROUP_DATA_PROXY_URL = "/ws/json/GroupJSONService/search";
  // public final static String I18N_JSON_SERVICE =
  // "/ws/json/I18nTranslationJSONService/search";
  public final static String GROUP_PARAM = "group";

  public final static String LIMIT_PARAM = "limit";
  public final static String MIMETYPE_PARAM = "mimetype";
  public final static String MIMETYPE2_PARAM = "mimetype2";
  public final static String QUERY_PARAM = "query";
  public final static String START_PARAM = "start";
  public final static String TO_LANGUAGE_PARAM = "to";

  public final static String USER_DATA_PROXY_URL = "/ws/json/UserJSONService/search";
  // public final static String I18N_JSON_SERVICE_TRANSLATED =
  // "/ws/json/I18nTranslationJSONService/searchtranslated";
  // public final static String USER_DATA_PROXY_URL =
  // "/ws/json/UserJSONService/search";
  public static final String WILDCARD = "*";

  private SearcherConstants() {
  }
}
