/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.platf.server.properties;

public interface KuneProperties {
    public String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";
    public String SITE_DOMAIN = "kune.site.domain";
    public String CHAT_HTTP_BASE = "kune.chat.httpbase";
    public String CHAT_DOMAIN = "kune.chat.domain";
    public String CHAT_ROOM_HOST = "kune.chat.roomHost";
    public String WS_THEMES_DEF = "kune.wsthemes.default";
    public String WS_THEMES = "kune.wsthemes";
    public String SITE_LOGO_URL = "kune.sitelogourl";
    public String UPLOAD_GALLERY_PERMITTED_EXTS = "kune.upload.gallerypermittedextensions";
    public String UPLOAD_MAX_FILE_SIZE = "kune.upload.maxfilesizeinmegas";
    public String UPLOAD_LOCATION = "kune.upload.location";

    public String get(String key);

    public String get(String key, String defaultValue);
}
