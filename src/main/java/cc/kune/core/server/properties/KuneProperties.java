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
package cc.kune.core.server.properties;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface KuneProperties.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface KuneProperties {

  /** The avi embeded object. */
  String AVI_EMBEDED_OBJECT = "kune.media.aviembededobject";

  /** The chat domain. */
  String CHAT_DOMAIN = "kune.chat.domain";

  /** The chat http base. */
  String CHAT_HTTP_BASE = "kune.chat.httpbase";

  /** The chat room host. */
  String CHAT_ROOM_HOST = "kune.chat.roomHost";

  /** The current cc version. */
  String CURRENT_CC_VERSION = "kune.currentccversion";

  /** The default site short name. */
  String DEFAULT_SITE_SHORT_NAME = "kune.default.site.shortName";

  /** The feedback enabled. */
  String FEEDBACK_ENABLED = "kune.feedback.enabled";

  /** The feedback to. */
  String FEEDBACK_TO = "kune.feedback.to";

  /** The flv embeded object. */
  String FLV_EMBEDED_OBJECT = "kune.media.flvembededobject";

  /** The images cropsize. */
  String IMAGES_CROPSIZE = "kune.images.cropsize";

  /** The images iconsize. */
  String IMAGES_ICONSIZE = "kune.images.iconsize";

  /** The images resizewidth. */
  String IMAGES_RESIZEWIDTH = "kune.images.resizewidth";

  /** The images thumbsize. */
  String IMAGES_THUMBSIZE = "kune.images.thumbsize";

  /** The kune tutorials deflang. */
  String KUNE_TUTORIALS_DEFLANG = "kune.tutorial.defaultlang";

  /** The kune tutorials langs. */
  String KUNE_TUTORIALS_LANGS = "kune.tutorial.langs";

  /** The M p3_ embede d_ object. */
  String MP3_EMBEDED_OBJECT = "kune.media.mp3embededobject";

  /** The ogg embeded object. */
  String OGG_EMBEDED_OBJECT = "kune.media.oggembededobject";

  /** The public space visible. */
  String PUBLIC_SPACE_VISIBLE = "kune.publispace.toolicon.visible";

  /** The reserved words. */
  String RESERVED_WORDS = "kune.server.names.reservedwords";

  /** The site admin email. */
  String SITE_ADMIN_EMAIL = "kune.admin.email";

  /** The site admin name. */
  String SITE_ADMIN_NAME = "kune.admin.name";

  /** The site admin passwd. */
  String SITE_ADMIN_PASSWD = "kune.admin.password";

  /** The site admin shortname. */
  String SITE_ADMIN_SHORTNAME = "kune.admin.shortName";

  /** The site common name. */
  String SITE_COMMON_NAME = "kune.default.site.commonname";

  /** The SIT e_ d b_ c3 p0_ acquir e_ increment. */
  String SITE_DB_C3P0_ACQUIRE_INCREMENT = "kune.db.c3p0.acquire_increment";

  /** The SIT e_ d b_ c3 p0_ autocommitonclose. */
  String SITE_DB_C3P0_AUTOCOMMITONCLOSE = "kune.db.c3p0.autoCommitOnClose";

  /** The SIT e_ d b_ c3 p0_ ma x_ size. */
  String SITE_DB_C3P0_MAX_SIZE = "kune.db.c3p0.max_size";

  /** The SIT e_ d b_ c3 p0_ ma x_ statements. */
  String SITE_DB_C3P0_MAX_STATEMENTS = "kune.db.c3p0.max_statements";

  /** The SIT e_ d b_ c3 p0_ mi n_ size. */
  String SITE_DB_C3P0_MIN_SIZE = "kune.db.c3p0.min_size";

  /** The SIT e_ d b_ c3 p0_ tes t_ period. */
  String SITE_DB_C3P0_TEST_PERIOD = "kune.db.c3p0.idle_test_period";

  /** The SIT e_ d b_ c3 p0_ timeout. */
  String SITE_DB_C3P0_TIMEOUT = "kune.db.c3p0.timeout";

  /** The site db password. */
  String SITE_DB_PASSWORD = "kune.db.password";

  /** The site db persistence name. */
  String SITE_DB_PERSISTENCE_NAME = "kune.db.persistence.name";

  /** The site db schema. */
  String SITE_DB_SCHEMA = "kune.db.schema";

  /** The site db url. */
  String SITE_DB_URL = "kune.db.url";

  /** The site db user. */
  String SITE_DB_USER = "kune.db.user";

  /** The site def license. */
  String SITE_DEF_LICENSE = "kune.default.license";

  /** The site domain. */
  String SITE_DOMAIN = "kune.site.domain";

  /** The site email template. */
  String SITE_EMAIL_TEMPLATE = "kune.site.email.template";

  /** The site group available tools. */
  String SITE_GROUP_AVAILABLE_TOOLS = "kune.tools.groupSiteAvailableTools";

  /** The site group regist enabled tools. */
  String SITE_GROUP_REGIST_ENABLED_TOOLS = "kune.tools.groupRegisEnabledTools";

  /** The site logo url. */
  String SITE_LOGO_URL = "kune.sitelogourl";

  /** The site logo url onover. */
  String SITE_LOGO_URL_ONOVER = "kune.sitelogourl.onover";

  /** The site name. */
  String SITE_NAME = "kune.default.site.name";

  /** The site openfire db password. */
  String SITE_OPENFIRE_DB_PASSWORD = "kune.openfire.db.password";

  /** The site openfire db url. */
  String SITE_OPENFIRE_DB_URL = "kune.openfire.db.url";

  /** The site openfire db user. */
  String SITE_OPENFIRE_DB_USER = "kune.openfire.db.user";

  /** The site openfire ignore. */
  String SITE_OPENFIRE_IGNORE = "kune.openfire.ignore";

  /** The site shortname. */
  String SITE_SHORTNAME = "kune.default.site.shortName";

  /** The site smtp default from. */
  String SITE_SMTP_DEFAULT_FROM = "kune.site.smtp.defaultfrom";

  /** The site smtp host. */
  String SITE_SMTP_HOST = "kune.site.smtp.host";

  /** The site smtp skip. */
  String SITE_SMTP_SKIP = "kune.site.smtp.skip";

  /** The site superadmin group. */
  String SITE_SUPERADMIN_GROUP = "kune.superadmin.group.shortname";

  /** The site url. */
  String SITE_URL = "kune.siteurl";

  /** The site user available tools. */
  String SITE_USER_AVAILABLE_TOOLS = "kune.tools.userSiteAvailableTools";

  /** The site user regist enabled tools. */
  String SITE_USER_REGIST_ENABLED_TOOLS = "kune.tools.userRegisEnabledTools";

  /** The site wave import username pairs. */
  String SITE_WAVE_IMPORT_USERNAME_PAIRS = "kune.wave.import.usernamespairs";

  /** The sitemap dir. */
  String SITEMAP_DIR = "kune.sitemap.dir";

  /** The ui translator enabled. */
  String UI_TRANSLATOR_ENABLED = "kune.ui-translator.enabled";

  /** The ui translator full translated langs. */
  String UI_TRANSLATOR_FULL_TRANSLATED_LANGS = "kune.ui-translator.fullTranslatedLanguages";

  /** The ui translator group. */
  String UI_TRANSLATOR_GROUP = "kune.ui-translator.group.shortname";

  /** The upload delay for test. */
  String UPLOAD_DELAY_FOR_TEST = "kune.upload.delayfortest";

  /** The upload gallery permitted exts. */
  String UPLOAD_GALLERY_PERMITTED_EXTS = "kune.upload.gallerypermittedextensions";

  /** The upload location. */
  String UPLOAD_LOCATION = "kune.upload.location";

  /** The upload max file size. */
  String UPLOAD_MAX_FILE_SIZE = "kune.upload.maxfilesizeinmegas";

  /** The upload max file size in ks. */
  String UPLOAD_MAX_FILE_SIZE_IN_KS = "kune.upload.maxfilesizeinks";

  /** The use client content cache. */
  String USE_CLIENT_CONTENT_CACHE = "kune.client.useContentCache";

  /** The welcome wave. */
  String WELCOME_WAVE = "kune.site.welcomewave";

  /** The ws themes. */
  String WS_THEMES = "kune.wsthemes";

  /** The ws themes def. */
  String WS_THEMES_DEF = "kune.wsthemes.default";

  /**
   * Gets the.
   * 
   * @param key
   *          the key
   * @return the string
   */
  String get(String key);

  /**
   * Gets the.
   * 
   * @param key
   *          the key
   * @param defaultValue
   *          the default value
   * @return the string
   */
  String get(String key, String defaultValue);

  /**
   * Gets the boolean.
   * 
   * @param key
   *          the key
   * @return the boolean
   */
  boolean getBoolean(String key);

  /**
   * Gets the integer.
   * 
   * @param key
   *          the key
   * @return the integer
   */
  Integer getInteger(String key);

  /**
   * Gets the list.
   * 
   * @param key
   *          the key
   * @return the list
   */
  List<String> getList(String key);

  /**
   * Gets the long.
   * 
   * @param key
   *          the key
   * @return the long
   */
  Long getLong(String key);

  /**
   * Checks for.
   * 
   * @param key
   *          the key
   * @return true, if successful
   */
  boolean has(String key);
}
