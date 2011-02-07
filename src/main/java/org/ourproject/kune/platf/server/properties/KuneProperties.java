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
 */
package org.ourproject.kune.platf.server.properties;

public interface KuneProperties {
    String SITE_URL = "kune.siteurl";
    String SITE_DOMAIN = "kune.site.domain";
    String SITE_NAME = "kune.default.site.name";
    String SITE_SHORTNAME = "kune.default.site.shortName";
    String SITE_DEF_LICENSE = "kune.default.license";

    String SITE_ADMIN_NAME = "kune.admin.name";
    String SITE_ADMIN_SHORTNAME = "kune.admin.shortName";
    String SITE_ADMIN_EMAIL = "kune.admin.email";
    String SITE_ADMIN_PASSWD = "kune.admin.password";
    String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";
    String CURRENT_CC_VERSION = "kune.currentccversion";
    String CHAT_HTTP_BASE = "kune.chat.httpbase";
    String CHAT_DOMAIN = "kune.chat.domain";
    String CHAT_ROOM_HOST = "kune.chat.roomHost";
    String WS_THEMES_DEF = "kune.wsthemes.default";
    String WS_THEMES = "kune.wsthemes";
    String SITE_LOGO_URL = "kune.sitelogourl";
    String UPLOAD_GALLERY_PERMITTED_EXTS = "kune.upload.gallerypermittedextensions";
    String UPLOAD_MAX_FILE_SIZE = "kune.upload.maxfilesizeinmegas";
    String UPLOAD_LOCATION = "kune.upload.location";
    String IMAGES_RESIZEWIDTH = "kune.images.resizewidth";
    String IMAGES_THUMBSIZE = "kune.images.thumbsize";
    String IMAGES_CROPSIZE = "kune.images.cropsize";
    String IMAGES_ICONSIZE = "kune.images.iconsize";
    String FLV_EMBEDED_OBJECT = "kune.media.flvembededobject";
    String OGG_EMBEDED_OBJECT = "kune.media.oggembededobject";
    String MP3_EMBEDED_OBJECT = "kune.media.mp3embededobject";
    String AVI_EMBEDED_OBJECT = "kune.media.aviembededobject";

    String get(String key);

    String get(String key, String defaultValue);
}
