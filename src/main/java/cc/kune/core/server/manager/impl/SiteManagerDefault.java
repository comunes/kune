/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.markdownj.MarkdownProcessor;
import org.waveprotocol.box.server.waveserver.WaveIndexer;
import org.waveprotocol.box.server.waveserver.WaveServerException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.InitData;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.manager.GroupManager;
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
import cc.kune.core.shared.dto.MotdDTO;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.domain.I18nLanguage;

@Singleton
public class SiteManagerDefault implements SiteManager, SiteManagerDefaultMBean {

  private static final Log LOG = LogFactory.getLog(SiteManagerDefault.class);

  /** The chat properties. */
  private final ChatProperties chatProperties;

  /** The country manager. */
  private final I18nCountryManager countryManager;

  /** The data. */
  private InitData data;

  private final GroupManager groupManager;

  private final I18nTranslationServiceMultiLang i18n;

  /** The kune properties. */
  private final KuneProperties kuneProperties;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  /** The license manager. */
  private final LicenseManager licenseManager;

  /** The mapper. */
  private final KuneMapper mapper;

  private MotdDTO motd;

  private HashMap<I18nLanguage, MotdDTO> motdTranslated;

  /** The reserved words. */
  private ReservedWordsRegistryDTO reservedWords;
  /** The server tool registry. */
  private final ServerToolRegistry serverToolRegistry;

  /** The site themes. */
  private HashMap<String, GSpaceTheme> siteThemes;

  /** The store untranslated string. */
  private boolean storeUntranslatedStrings;

  /** The user info service. */
  private final UserInfoService userInfoService;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  private MarkdownProcessor markProcessor;

  private SiteManagers siteManagers;

  private WaveIndexer waveIndexer;

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
  public SiteManagerDefault(final UserSessionManager userSessionManager,
      final UserInfoService userInfoService, final LicenseManager licenseManager,
      final KuneMapper mapper, final KuneProperties kuneProperties, final ChatProperties chatProperties,
      final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
      final ServerToolRegistry serverToolRegistry, final MBeanRegistry mbeanRegistry,
      final GroupManager groupManager, final I18nTranslationServiceMultiLang i18n, SiteManagers siteManagers,
      WaveIndexer waveIndexer
      ) {
    this.userSessionManager = userSessionManager;
    this.userInfoService = userInfoService;
    this.licenseManager = licenseManager;
    this.mapper = mapper;
    this.kuneProperties = kuneProperties;
    this.chatProperties = chatProperties;
    this.languageManager = languageManager;
    this.countryManager = countryManager;
    this.serverToolRegistry = serverToolRegistry;
    this.groupManager = groupManager;
    this.i18n = i18n;
    this.siteManagers = siteManagers;
    this.waveIndexer = waveIndexer;
    // By default we don't collect which part of the client is untranslated
    storeUntranslatedStrings = false;
    mbeanRegistry.registerAsMBean(this, MBEAN_OBJECT_NAME);
    markProcessor = new MarkdownProcessor();
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
    dataMapped.setgSpaceThemes(siteThemes);
    dataMapped.setReservedWords(reservedWords);
    dataMapped.setStoreUntranslatedStrings(storeUntranslatedStrings);
    if (userInfo != null) {
      dataMapped.setUserInfo(mapper.map(userInfo, UserInfoDTO.class));
      dataMapped.setMotd(translate(motd, userInfo.getLanguage()));
    } else {
      dataMapped.setMotd(translate(motd, languageManager.getDefaultLanguage()));
    }
    return dataMapped;
  }

  @Override
  public boolean getShowInDevelFeatures() {
    return data.getShowInDevelFeatures();
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
  public boolean getStoreUntranslatedStrings() {
    return storeUntranslatedStrings;
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
    data.setFullTranslatedLanguages(languageManager.findByCodes(
        kuneProperties.getList(KuneProperties.UI_TRANSLATOR_FULL_TRANSLATED_LANGS)));
    data.setCountries(countryManager.getAll());
    data.setTimezones(TimeZone.getAvailableIDs());
    data.setChatHttpBase(chatProperties.getHttpBase());
    data.setChatDomain(chatProperties.getDomain());
    data.setChatRoomHost(chatProperties.getRoomHost());
    data.setDefaultLicense(licenseManager.getDefLicense());
    data.setCurrentCCversion(this.kuneProperties.get(KuneProperties.CURRENT_CC_VERSION));
    data.setDefaultWsTheme(this.kuneProperties.get(KuneProperties.WS_THEMES_DEF));
    data.setGalleryPermittedExtensions(kuneProperties.get(KuneProperties.UPLOAD_GALLERY_PERMITTED_EXTS));
    data.setMaxFileSizeInMb(kuneProperties.get(KuneProperties.UPLOAD_MAX_FILE_SIZE));
    data.setUserTools(serverToolRegistry.getToolsAvailableForUsers());
    data.setGroupTools(serverToolRegistry.getToolsAvailableForGroups());
    data.setImgResizewidth(kuneProperties.getInteger(KuneProperties.IMAGES_RESIZEWIDTH));
    data.setImgThumbsize(kuneProperties.getInteger(KuneProperties.IMAGES_THUMBSIZE));
    data.setImgCropsize(kuneProperties.getInteger(KuneProperties.IMAGES_CROPSIZE));
    data.setImgIconsize(kuneProperties.getInteger(KuneProperties.IMAGES_ICONSIZE));
    data.setKuneEmbedTemplate(kuneProperties.get(KuneProperties.KUNE_DOC_EMBEDED_TEMPLATE));
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
    data.setShowInDevelFeatures(kuneProperties.getBoolean(KuneProperties.SHOW_DEVEL_FEATURES));

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
    motdTranslated = new HashMap<I18nLanguage, MotdDTO>();

    siteThemes = getSiteThemes(this.kuneProperties.getList(KuneProperties.WS_THEMES));
    reservedWords = new ReservedWordsRegistryDTO(ReservedWordsRegistry.fromList(kuneProperties));

    if (kuneProperties.getBoolean(KuneProperties.MOTD_ENABLED)) {
      motd = new MotdDTO();
      motd.setTitle(kuneProperties.get(KuneProperties.MOTD_TITLE));
      motd.setMessage(StringUtils.abbreviate(kuneProperties.get(KuneProperties.MOTD_MESSAGE), 500));
      motd.setMessageBottom(StringUtils.abbreviate(kuneProperties.get(KuneProperties.MOTD_MESSAGE_BOTTOM),500));
      motd.setOkBtnText(kuneProperties.get(KuneProperties.MOTD_OK_BTN_TEXT));
      motd.setOkBtnUrl(kuneProperties.get(KuneProperties.MOTD_OK_BTN_URL));
      motd.setCloseBtnText(kuneProperties.get(KuneProperties.MOTD_CLOSE_BTN_TEXT));
      motd.setCookieName(kuneProperties.get(KuneProperties.MOTD_COOKIE_NAME));
      motd.setShouldRemember(kuneProperties.getInteger(KuneProperties.MOTD_SHOULD_REMEMBER));
    } else {
      motd = null;
    }
  }

  private String markdownToHtml(final String motdMessage) {
    String html = markProcessor.markdown(motdMessage);
    return html.replaceAll(" href=\"http", " target='_blank' href=\"http");
  }

  @Override
  public void reIndexAllEntities() {
    siteManagers.reIndexAllEntities();
  }

  @Override
  public void reloadInitData() {
    loadProperties(kuneProperties);
  }

  @Override
  public void setShowInDevelFeatures(final boolean showInDevelFeatures) {
    data.setShowInDevelFeatures(showInDevelFeatures);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.server.rpc.SiteRPCMBean#setStoreUntranslatedString(boolean)
   */
  @Override
  public void setStoreUntranslatedStrings(final boolean storeUntranslatedStrings) {
    this.storeUntranslatedStrings = storeUntranslatedStrings;
  }

  private MotdDTO translate(final MotdDTO motd, final I18nLanguage lang) {
    if (motd == null) {
      return motd;
    }
    MotdDTO translated = motdTranslated.get(lang);
    if (translated == null) {
      translated = new MotdDTO();
      translated.setTitle(i18n.tWithNT(lang, motd.getTitle(), ""));
      translated.setMessage(markdownToHtml(
          i18n.tWithNT(lang, motd.getMessage(), "Please, maintain the markdown notation")));
      translated.setMessageBottom(markdownToHtml(
          i18n.tWithNT(lang, motd.getMessageBottom(), "Please, maintain the markdown notation")));
      translated.setOkBtnText(i18n.tWithNT(lang, motd.getOkBtnText(), ""));
      translated.setOkBtnUrl(motd.getOkBtnUrl());
      translated.setCloseBtnText(i18n.tWithNT(lang, motd.getCloseBtnText(), ""));
      translated.setCookieName(motd.getCookieName());
      translated.setShouldRemember(motd.getShouldRemember());
      motdTranslated.put(lang, translated);
    }
    return translated;
  }

  @Override
  public void reIndexAllWaves() {
    try {
      waveIndexer.remakeIndex();
    } catch (WaveServerException e) {
      LOG.error("Failed to reindex all waves", e);
    }
  }

}
