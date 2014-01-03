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

package cc.kune.core.server.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.InitData;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.SiteManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.mbean.MBeanRegistry;
import cc.kune.core.server.properties.ChatProperties;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.properties.ReservedWordsRegistry;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SiteManagerDefault implements SiteManager, SiteManagerDefaultMBean {

  private static final Log LOG = LogFactory.getLog(SiteManagerDefault.class);

  /** The chat properties. */
  private final ChatProperties chatProperties;

  /** The country manager. */
  private final I18nCountryManager countryManager;

  /** The data. */
  private InitData data;

  /** The kune properties. */
  private final KuneProperties kuneProperties;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  /** The license manager. */
  private final LicenseManager licenseManager;

  /** The mapper. */
  private final KuneMapper mapper;

  /** The reserved words. */
  private ReservedWordsRegistryDTO reservedWords;

  /** The server tool registry. */
  private final ServerToolRegistry serverToolRegistry;

  /** The site themes. */
  private HashMap<String, GSpaceTheme> siteThemes;

  /** The store untranslated string. */
  private boolean storeUntranslatedString;

  /** The user info service. */
  private final UserInfoService userInfoService;
  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new site rpc.
   * 
   * @param userSessionManager
   *          the user session manager
   * @param userManager
   *          the user manager
   * @param userInfoService
   *          the user info service
   * @param licenseManager
   *          the license manager
   * @param mapper
   *          the mapper
   * @param kuneProperties
   *          the kune properties
   * @param chatProperties
   *          the chat properties
   * @param languageManager
   *          the language manager
   * @param countryManager
   *          the country manager
   * @param serverToolRegistry
   *          the server tool registry
   * @param mbeanRegistry
   *          the mbean registry
   */
  @Inject
  public SiteManagerDefault(final UserSessionManager userSessionManager, final UserManager userManager,
      final UserInfoService userInfoService, final LicenseManager licenseManager,
      final KuneMapper mapper, final KuneProperties kuneProperties, final ChatProperties chatProperties,
      final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
      final ServerToolRegistry serverToolRegistry, final MBeanRegistry mbeanRegistry) {
    this.userSessionManager = userSessionManager;
    this.userInfoService = userInfoService;
    this.licenseManager = licenseManager;
    this.mapper = mapper;
    this.kuneProperties = kuneProperties;
    this.chatProperties = chatProperties;
    this.languageManager = languageManager;
    this.countryManager = countryManager;
    this.serverToolRegistry = serverToolRegistry;
    // By default we don't collect which part of the client is untranslated
    storeUntranslatedString = false;
    mbeanRegistry.registerAsMBean(this, MBEAN_OBJECT_NAME);
  }

  /**
   * Gets the colors.
   * 
   * @param key
   *          the key
   * @return the colors
   */
  private String[] getColors(final String key) {
    return this.kuneProperties.getList(key).toArray(new String[0]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SiteService#getInitData(java.lang.String)
   */
  @Override
  public InitDataDTO getInitData(final String userHash) throws DefaultException {
    if (data == null) {
      loadProperties(kuneProperties);
    }
    final InitDataDTO dataMapped = mapper.map(data, InitDataDTO.class);
    final UserInfo userInfo = userInfoService.buildInfo(userSessionManager.getUser(), userHash);
    LOG.info("Retrieve init data using userHash: " + userHash);
    LOG.info("Session userHash: " + userSessionManager.getHash());
    if (userInfo != null) {
      dataMapped.setUserInfo(mapper.map(userInfo, UserInfoDTO.class));
    }

    dataMapped.setgSpaceThemes(siteThemes);
    dataMapped.setReservedWords(reservedWords);
    dataMapped.setStoreUntranslatedStrings(storeUntranslatedString);
    return dataMapped;
  }

  /**
   * Gets the site themes.
   * 
   * @param themes
   *          the themes
   * @return the site themes
   */
  private HashMap<String, GSpaceTheme> getSiteThemes(final List<String> themes) {
    final HashMap<String, GSpaceTheme> map = new HashMap<String, GSpaceTheme>();
    for (final String theme : themes) {
      map.put(theme, getThemeFromProperties(theme));
    }
    return map;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.rpc.SiteRPCMBean#getStoreUntranslatedString()
   */
  @Override
  public boolean getStoreUntranslatedString() {
    return storeUntranslatedString;
  }

  /**
   * Gets the theme from properties.
   * 
   * @param themeName
   *          the theme name
   * @return the theme from properties
   */
  private GSpaceTheme getThemeFromProperties(final String themeName) {
    final GSpaceTheme theme = new GSpaceTheme(themeName);
    theme.setBackColors(getColors(KuneProperties.WS_THEMES + "." + theme.getName() + ".backgrounds"));
    theme.setColors(getColors(KuneProperties.WS_THEMES + "." + theme.getName() + ".colors"));
    return theme;
  }

  /**
   * Load init data.
   * 
   * @return the inits the data
   */
  private InitData loadInitData() {
    data = new InitData();
    data.setSiteUrl(kuneProperties.get(KuneProperties.SITE_URL));
    data.setLicenses(licenseManager.getAll());
    data.setLanguages(languageManager.getAll());
    data.setFullTranslatedLanguages(languageManager.findByCodes(kuneProperties.getList(KuneProperties.UI_TRANSLATOR_FULL_TRANSLATED_LANGS)));
    data.setCountries(countryManager.getAll());
    data.setTimezones(TimeZone.getAvailableIDs());
    data.setChatHttpBase(chatProperties.getHttpBase());
    data.setChatDomain(chatProperties.getDomain());
    data.setChatRoomHost(chatProperties.getRoomHost());
    data.setDefaultLicense(licenseManager.getDefLicense());
    data.setCurrentCCversion(this.kuneProperties.get(KuneProperties.CURRENT_CC_VERSION));
    data.setDefaultWsTheme(this.kuneProperties.get(KuneProperties.WS_THEMES_DEF));
    data.setSiteLogoUrl(kuneProperties.get(KuneProperties.SITE_LOGO_URL));
    data.setSiteLogoUrlOnOver(kuneProperties.get(KuneProperties.SITE_LOGO_URL_ONOVER));
    data.setGalleryPermittedExtensions(kuneProperties.get(KuneProperties.UPLOAD_GALLERY_PERMITTED_EXTS));
    data.setMaxFileSizeInMb(kuneProperties.get(KuneProperties.UPLOAD_MAX_FILE_SIZE));
    data.setUserTools(serverToolRegistry.getToolsAvailableForUsers());
    data.setGroupTools(serverToolRegistry.getToolsAvailableForGroups());
    data.setImgResizewidth(kuneProperties.getInteger(KuneProperties.IMAGES_RESIZEWIDTH));
    data.setImgThumbsize(kuneProperties.getInteger(KuneProperties.IMAGES_THUMBSIZE));
    data.setImgCropsize(kuneProperties.getInteger(KuneProperties.IMAGES_CROPSIZE));
    data.setImgIconsize(kuneProperties.getInteger(KuneProperties.IMAGES_ICONSIZE));
    data.setFlvEmbedObject(kuneProperties.get(KuneProperties.FLV_EMBEDED_OBJECT));
    data.setMp3EmbedObject(kuneProperties.get(KuneProperties.MP3_EMBEDED_OBJECT));
    data.setOggEmbedObject(kuneProperties.get(KuneProperties.OGG_EMBEDED_OBJECT));
    data.setAviEmbedObject(kuneProperties.get(KuneProperties.AVI_EMBEDED_OBJECT));
    data.setFeedbackEnabled(kuneProperties.getBoolean(KuneProperties.FEEDBACK_ENABLED));
    data.setSiteShortName(kuneProperties.get(KuneProperties.SITE_SHORTNAME));
    data.setSiteCommonName(kuneProperties.get(KuneProperties.SITE_COMMON_NAME));
    data.setTranslatorEnabled(kuneProperties.getBoolean(KuneProperties.UI_TRANSLATOR_ENABLED));
    data.setUseClientContentCache(kuneProperties.getBoolean(KuneProperties.USE_CLIENT_CONTENT_CACHE));
    data.setDefTutorialLanguage(kuneProperties.get(KuneProperties.KUNE_TUTORIALS_DEFLANG));
    data.setTutorialLanguages(kuneProperties.getList(KuneProperties.KUNE_TUTORIALS_LANGS));
    data.setPublicSpaceVisible(kuneProperties.getBoolean(KuneProperties.PUBLIC_SPACE_VISIBLE));
    return data;
  }

  /**
   * Load properties.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  private void loadProperties(final KuneProperties kuneProperties) {
    data = loadInitData();
    siteThemes = getSiteThemes(this.kuneProperties.getList(KuneProperties.WS_THEMES));
    reservedWords = new ReservedWordsRegistryDTO(ReservedWordsRegistry.fromList(kuneProperties));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.rpc.SiteRPCMBean#setStoreUntranslatedString(boolean)
   */
  @Override
  public void setStoreUntranslatedString(final boolean storeUntranslatedString) {
    this.storeUntranslatedString = storeUntranslatedString;
  }

}
