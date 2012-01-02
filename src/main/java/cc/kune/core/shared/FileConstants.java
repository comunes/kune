/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared;

public final class FileConstants {

  public final static String ASITE_PREFIX = "/ws/";
  public final static String AVATARDOWNLOADSERVLET = ASITE_PREFIX + "servlets/UserLogoDownloadManager";
  public final static String BACKDOWNLOADSERVLET = ASITE_PREFIX
      + "servlets/EntityBackgroundDownloadManager";
  public final static String DOWNLOAD = "download";
  public static final String DOWNLOADSERVLET = ASITE_PREFIX + "servlets/FileDownloadManager";
  public final static String FILENAME = "filename";
  public final static String GROUP_LOGO_FIELD = "k-glogov-ff";
  public static final String GROUP_NO_AVATAR_IMAGE = "others/defgroup.gif";

  public final static String HASH = "hash";
  public final static String IMGSIZE = "imgsize";
  public final static int LOGO_DEF_HEIGHT = 60;
  public final static int LOGO_DEF_WIDTH = 468;
  public final static int LOGO_MIN_HEIGHT = 28;
  public final static int LOGO_MIN_WIDTH = 468;
  public static final String LOGODOWNLOADSERVLET = ASITE_PREFIX + "servlets/EntityLogoDownloadManager";
  public final static String PERSON_NO_AVATAR_IMAGE = "others/unknown.jpg";
  public final static String TOKEN = "token";
  public final static String TYPE_ID = "typeid";
  public final static String USER_LOGO_FIELD = "k-ulogov-ff";
  public final static String USERNAME = "username";
  public final static String WORLD_AVATAR_IMAGE = ASITE_PREFIX + "images/world-90.gif";

  public FileConstants() {
  }
}
