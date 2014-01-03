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
 \*/
package cc.kune.core.shared;

// TODO: Auto-generated Javadoc
/**
 * The Class FileConstants.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class FileConstants {

  /** The Constant ASITE_PREFIX. */
  public final static String ASITE_PREFIX = "/ws/";

  /** The Constant BACKDOWNLOADSERVLET. */
  public final static String BACKDOWNLOADSERVLET = ASITE_PREFIX
      + "servlets/EntityBackgroundDownloadManager";

  /** The Constant DOWNLOAD. */
  public final static String DOWNLOAD = "download";

  /** The Constant DOWNLOADSERVLET. */
  public static final String DOWNLOADSERVLET = ASITE_PREFIX + "servlets/FileDownloadManager";

  /** The Constant EVENTSSERVLET. */
  public static final String EVENTSSERVLET = ASITE_PREFIX + "servlets/EventsServlet";

  /** The Constant FILENAME. */
  public final static String FILENAME = "filename";

  /** The Constant GROUP_LOGO_FIELD. */
  public final static String GROUP_LOGO_FIELD = "k-glogov-ff";

  /** The Constant GROUP_NO_AVATAR_IMAGE. */
  public static final String GROUP_NO_AVATAR_IMAGE = "others/defgroup.gif";

  /** The Constant HASH. */
  public final static String HASH = "hash";

  /** The Constant IMGSIZE. */
  public final static String IMGSIZE = "imgsize";

  /** The Constant LOGO_DEF_MAX_SIZE. */
  public final static int LOGO_DEF_MAX_SIZE = 120;

  /** The Constant LOGO_DEF_SIZE. */
  public final static int LOGO_DEF_SIZE = 60;

  /** The Constant LOGO_DEF_WIDTH. */
  @Deprecated
  public final static int LOGO_DEF_WIDTH = 468;

  /** The Constant LOGODOWNLOADSERVLET. */
  public static final String LOGODOWNLOADSERVLET = ASITE_PREFIX + "servlets/EntityLogoDownloadManager";

  /** The Constant NO_RESULT_AVATAR_IMAGE. */
  public static final String NO_RESULT_AVATAR_IMAGE = "others/unknown.jpg";

  /** The Constant ONLY_USERS. */
  public final static String ONLY_USERS = "onlyusers";

  /** The Constant PERSON_NO_AVATAR_IMAGE. */
  public final static String PERSON_NO_AVATAR_IMAGE = "others/defuser.jpg";

  /** The Constant TOKEN. */
  public final static String TOKEN = "token";

  /** The Constant TUTORIALS_PREFIX. */
  public final static String TUTORIALS_PREFIX = "/tutorials/";

  /** The Constant TYPE_ID. */
  public final static String TYPE_ID = "typeid";

  /** The Constant USER_LOGO_FIELD. */
  public final static String USER_LOGO_FIELD = "k-ulogov-ff";

  /** The Constant WORLD_AVATAR_IMAGE. */
  public final static String WORLD_AVATAR_IMAGE = ASITE_PREFIX + "images/world-90.gif";

  /**
   * Instantiates a new file constants.
   */
  public FileConstants() {
  }
}
