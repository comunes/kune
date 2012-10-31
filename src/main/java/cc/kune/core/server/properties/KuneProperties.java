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
package cc.kune.core.server.properties;

import java.util.List;

public interface KuneProperties {
  String AVI_EMBEDED_OBJECT = "kune.media.aviembededobject";
  String CHAT_DOMAIN = "kune.chat.domain";
  String CHAT_HTTP_BASE = "kune.chat.httpbase";
  String CHAT_ROOM_HOST = "kune.chat.roomHost";
  String CURRENT_CC_VERSION = "kune.currentccversion";
  String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";
  String FEEDBACK_ENABLED = "kune.feedback.enabled";
  String FEEDBACK_TO = "kune.feedback.to";
  String FLV_EMBEDED_OBJECT = "kune.media.flvembededobject";
  String IMAGES_CROPSIZE = "kune.images.cropsize";
  String IMAGES_ICONSIZE = "kune.images.iconsize";
  String IMAGES_RESIZEWIDTH = "kune.images.resizewidth";
  String IMAGES_THUMBSIZE = "kune.images.thumbsize";
  String KUNE_TUTORIALS_DEFLANG = "kune.tutorial.defaultlang";
  String KUNE_TUTORIALS_LANGS = "kune.tutorial.langs";
  String MP3_EMBEDED_OBJECT = "kune.media.mp3embededobject";
  String OGG_EMBEDED_OBJECT = "kune.media.oggembededobject";
  String PUBLIC_SPACE_VISIBLE = "kune.publispace.toolicon.visible";
  String RESERVED_WORDS = "kune.server.names.reservedwords";
  String SITE_ADMIN_EMAIL = "kune.admin.email";
  String SITE_ADMIN_NAME = "kune.admin.name";
  String SITE_ADMIN_PASSWD = "kune.admin.password";
  String SITE_ADMIN_SHORTNAME = "kune.admin.shortName";
  String SITE_COMMON_NAME = "kune.default.site.commonname";
  String SITE_DB_C3P0_ACQUIRE_INCREMENT = "kune.db.c3p0.acquire_increment";
  String SITE_DB_C3P0_AUTOCOMMITONCLOSE = "kune.db.c3p0.autoCommitOnClose";
  String SITE_DB_C3P0_MAX_SIZE = "kune.db.c3p0.max_size";
  String SITE_DB_C3P0_MAX_STATEMENTS = "kune.db.c3p0.max_statements";
  String SITE_DB_C3P0_MIN_SIZE = "kune.db.c3p0.min_size";
  String SITE_DB_C3P0_TEST_PERIOD = "kune.db.c3p0.idle_test_period";
  String SITE_DB_C3P0_TIMEOUT = "kune.db.c3p0.timeout";
  String SITE_DB_PASSWORD = "kune.db.password";
  String SITE_DB_PERSISTENCE_NAME = "kune.db.persistence.name";
  String SITE_DB_SCHEMA = "kune.db.schema";
  String SITE_DB_URL = "kune.db.url";
  String SITE_DB_USER = "kune.db.user";
  String SITE_DEF_LICENSE = "kune.default.license";
  String SITE_DOMAIN = "kune.site.domain";
  String SITE_EMAIL_TEMPLATE = "kune.site.email.template";
  String SITE_GROUP_AVAILABLE_TOOLS = "kune.tools.groupSiteAvailableTools";
  String SITE_GROUP_REGIST_ENABLED_TOOLS = "kune.tools.groupRegisEnabledTools";
  String SITE_LOGO_URL = "kune.sitelogourl";
  String SITE_LOGO_URL_ONOVER = "kune.sitelogourl.onover";
  String SITE_NAME = "kune.default.site.name";
  String SITE_OPENFIRE_DB_PASSWORD = "kune.openfire.db.password";
  String SITE_OPENFIRE_DB_URL = "kune.openfire.db.url";
  String SITE_OPENFIRE_DB_USER = "kune.openfire.db.user";
  String SITE_OPENFIRE_IGNORE = "kune.openfire.ignore";
  String SITE_SHORTNAME = "kune.default.site.shortName";
  String SITE_SMTP_DEFAULT_FROM = "kune.site.smtp.defaultfrom";
  String SITE_SMTP_HOST = "kune.site.smtp.host";
  String SITE_SMTP_SKIP = "kune.site.smtp.skip";
  String SITE_URL = "kune.siteurl";
  String SITE_USER_AVAILABLE_TOOLS = "kune.tools.userSiteAvailableTools";
  String SITE_USER_REGIST_ENABLED_TOOLS = "kune.tools.userRegisEnabledTools";
  String SITE_WAVE_IMPORT_USERNAME_PAIRS = "kune.wave.import.usernamespairs";
  String UI_TRANSLATOR_ENABLED = "kune.ui-translator.enabled";
  String UI_TRANSLATOR_FULL_TRANSLATED_LANGS = "kune.ui-translator.fullTranslatedLanguages";
  String UPLOAD_DELAY_FOR_TEST = "kune.upload.delayfortest";
  String UPLOAD_GALLERY_PERMITTED_EXTS = "kune.upload.gallerypermittedextensions";
  String UPLOAD_LOCATION = "kune.upload.location";
  String UPLOAD_MAX_FILE_SIZE = "kune.upload.maxfilesizeinmegas";
  String UPLOAD_MAX_FILE_SIZE_IN_KS = "kune.upload.maxfilesizeinks";
  String USE_CLIENT_CONTENT_CACHE = "kune.client.useContentCache";
  String WELCOME_WAVE = "kune.site.welcomewave";
  String WS_THEMES = "kune.wsthemes";
  String WS_THEMES_DEF = "kune.wsthemes.default";

  String get(String key);

  String get(String key, String defaultValue);

  boolean getBoolean(String key);

  Integer getInteger(String key);

  List<String> getList(String key);

  Long getLong(String key);

  boolean has(String key);
}
