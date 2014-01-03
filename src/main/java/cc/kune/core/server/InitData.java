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
package cc.kune.core.server;

import java.util.HashMap;
import java.util.List;

import cc.kune.core.server.tool.ToolSimple;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.License;

// TODO: Auto-generated Javadoc
/**
 * The Class InitData.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InitData {

  /** The avi embed object. */
  private String aviEmbedObject;

  /** The chat domain. */
  private String chatDomain;

  /** The chat http base. */
  private String chatHttpBase;

  /** The chat room host. */
  private String chatRoomHost;

  /** The countries. */
  private List<I18nCountry> countries;

  /** The current c cversion. */
  private String currentCCversion;

  /** The default license. */
  private License defaultLicense;

  /** The default ws theme. */
  private String defaultWsTheme;

  /** The def tutorial language. */
  private String defTutorialLanguage;

  /** The feedback enabled. */
  private boolean feedbackEnabled;

  /** The flv embed object. */
  private String flvEmbedObject;

  /** The full translated languages. */
  private List<I18nLanguage> fullTranslatedLanguages;

  /** The gallery permitted extensions. */
  private String galleryPermittedExtensions;

  /** The group tools. */
  private List<ToolSimple> groupTools;

  /** The g space themes. */
  private HashMap<String, GSpaceTheme> gSpaceThemes;

  /** The img cropsize. */
  private int imgCropsize;

  /** The img iconsize. */
  private int imgIconsize;

  /** The img resizewidth. */
  private int imgResizewidth;

  /** The img thumbsize. */
  private int imgThumbsize;

  /** The languages. */
  private List<I18nLanguage> languages;

  /** The licenses. */
  private List<License> licenses;

  /** The max file size in mb. */
  private String maxFileSizeInMb;

  /** The mp3 embed object. */
  private String mp3EmbedObject;

  /** The ogg embed object. */
  private String oggEmbedObject;

  /** The public space visible. */
  private boolean publicSpaceVisible;

  /** The reserved words. */
  private ReservedWordsRegistryDTO reservedWords;

  /** The site common name. */
  private String siteCommonName;

  /** The site logo url. */
  private String siteLogoUrl;

  /** The site logo url on over. */
  private String siteLogoUrlOnOver;

  /** The site short name. */
  private String siteShortName;

  /** The site url. */
  private String siteUrl;

  /** The store untranslated strings. */
  private boolean storeUntranslatedStrings;

  /** The timezones. */
  private String[] timezones;

  /** The translator enabled. */
  private boolean translatorEnabled;

  /** The tutorial languages. */
  private List<String> tutorialLanguages;

  /** The use client content cache. */
  private boolean useClientContentCache;

  /** The user info. */
  private UserInfo userInfo;

  /** The user tools. */
  private List<ToolSimple> userTools;

  /**
   * Gets the avi embed object.
   * 
   * @return the avi embed object
   */
  public String getAviEmbedObject() {
    return aviEmbedObject;
  }

  /**
   * Gets the chat domain.
   * 
   * @return the chat domain
   */
  public String getChatDomain() {
    return chatDomain;
  }

  /**
   * Gets the chat http base.
   * 
   * @return the chat http base
   */
  public String getChatHttpBase() {
    return chatHttpBase;
  }

  /**
   * Gets the chat room host.
   * 
   * @return the chat room host
   */
  public String getChatRoomHost() {
    return chatRoomHost;
  }

  /**
   * Gets the countries.
   * 
   * @return the countries
   */
  public List<I18nCountry> getCountries() {
    return countries;
  }

  /**
   * Gets the current c cversion.
   * 
   * @return the current c cversion
   */
  public String getCurrentCCversion() {
    return currentCCversion;
  }

  /**
   * Gets the default license.
   * 
   * @return the default license
   */
  public License getDefaultLicense() {
    return defaultLicense;
  }

  /**
   * Gets the default ws theme.
   * 
   * @return the default ws theme
   */
  public String getDefaultWsTheme() {
    return defaultWsTheme;
  }

  /**
   * Gets the def tutorial language.
   * 
   * @return the def tutorial language
   */
  public String getDefTutorialLanguage() {
    return defTutorialLanguage;
  }

  /**
   * Gets the flv embed object.
   * 
   * @return the flv embed object
   */
  public String getFlvEmbedObject() {
    return flvEmbedObject;
  }

  /**
   * Gets the full translated languages.
   * 
   * @return the full translated languages
   */
  public List<I18nLanguage> getFullTranslatedLanguages() {
    return fullTranslatedLanguages;
  }

  /**
   * Gets the gallery permitted extensions.
   * 
   * @return the gallery permitted extensions
   */
  public String getGalleryPermittedExtensions() {
    return galleryPermittedExtensions;
  }

  /**
   * Gets the group tools.
   * 
   * @return the group tools
   */
  public List<ToolSimple> getGroupTools() {
    return groupTools;
  }

  /**
   * Gets the g space themes.
   * 
   * @return the g space themes
   */
  public HashMap<String, GSpaceTheme> getgSpaceThemes() {
    return gSpaceThemes;
  }

  /**
   * Gets the img cropsize.
   * 
   * @return the img cropsize
   */
  public int getImgCropsize() {
    return imgCropsize;
  }

  /**
   * Gets the img iconsize.
   * 
   * @return the img iconsize
   */
  public int getImgIconsize() {
    return imgIconsize;
  }

  /**
   * Gets the img resizewidth.
   * 
   * @return the img resizewidth
   */
  public int getImgResizewidth() {
    return imgResizewidth;
  }

  /**
   * Gets the img thumbsize.
   * 
   * @return the img thumbsize
   */
  public int getImgThumbsize() {
    return imgThumbsize;
  }

  /**
   * Gets the languages.
   * 
   * @return the languages
   */
  public List<I18nLanguage> getLanguages() {
    return languages;
  }

  /**
   * Gets the licenses.
   * 
   * @return the licenses
   */
  public List<License> getLicenses() {
    return licenses;
  }

  /**
   * Gets the max file size in mb.
   * 
   * @return the max file size in mb
   */
  public String getMaxFileSizeInMb() {
    return maxFileSizeInMb;
  }

  /**
   * Gets the mp3 embed object.
   * 
   * @return the mp3 embed object
   */
  public String getMp3EmbedObject() {
    return mp3EmbedObject;
  }

  /**
   * Gets the ogg embed object.
   * 
   * @return the ogg embed object
   */
  public String getOggEmbedObject() {
    return oggEmbedObject;
  }

  /**
   * Gets the reserved words.
   * 
   * @return the reserved words
   */
  public ReservedWordsRegistryDTO getReservedWords() {
    return this.reservedWords;
  }

  /**
   * Gets the site common name.
   * 
   * @return the site common name
   */
  public String getSiteCommonName() {
    return siteCommonName;
  }

  /**
   * Gets the site logo url.
   * 
   * @return the site logo url
   */
  public String getSiteLogoUrl() {
    return siteLogoUrl;
  }

  /**
   * Gets the site logo url on over.
   * 
   * @return the site logo url on over
   */
  public String getSiteLogoUrlOnOver() {
    return siteLogoUrlOnOver;
  }

  /**
   * Gets the site short name.
   * 
   * @return the site short name
   */
  public String getSiteShortName() {
    return siteShortName;
  }

  /**
   * Gets the site url.
   * 
   * @return the site url
   */
  public String getSiteUrl() {
    return siteUrl;
  }

  /**
   * Gets the store untranslated strings.
   * 
   * @return the store untranslated strings
   */
  public boolean getStoreUntranslatedStrings() {
    return storeUntranslatedStrings;
  }

  /**
   * Gets the timezones.
   * 
   * @return the timezones
   */
  public String[] getTimezones() {
    return timezones;
  }

  /**
   * Gets the tutorial languages.
   * 
   * @return the tutorial languages
   */
  public List<String> getTutorialLanguages() {
    return tutorialLanguages;
  }

  public boolean getUseClientContentCache() {
    return useClientContentCache;
  }

  /**
   * Gets the user info.
   * 
   * @return the user info
   */
  public UserInfo getUserInfo() {
    return userInfo;
  }

  /**
   * Gets the user tools.
   * 
   * @return the user tools
   */
  public List<ToolSimple> getUserTools() {
    return userTools;
  }

  /**
   * Checks if is feedback enabled.
   * 
   * @return true, if is feedback enabled
   */
  public boolean isFeedbackEnabled() {
    return feedbackEnabled;
  }

  /**
   * Checks if is public space visible.
   * 
   * @return true, if is public space visible
   */
  public boolean isPublicSpaceVisible() {
    return publicSpaceVisible;
  }

  /**
   * Checks if is translator enabled.
   * 
   * @return true, if is translator enabled
   */
  public boolean isTranslatorEnabled() {
    return translatorEnabled;
  }

  /**
   * Sets the avi embed object.
   * 
   * @param aviEmbedObject
   *          the new avi embed object
   */
  public void setAviEmbedObject(final String aviEmbedObject) {
    this.aviEmbedObject = aviEmbedObject;
  }

  /**
   * Sets the chat domain.
   * 
   * @param chatDomain
   *          the new chat domain
   */
  public void setChatDomain(final String chatDomain) {
    this.chatDomain = chatDomain;
  }

  /**
   * Sets the chat http base.
   * 
   * @param chatHttpBase
   *          the new chat http base
   */
  public void setChatHttpBase(final String chatHttpBase) {
    this.chatHttpBase = chatHttpBase;
  }

  /**
   * Sets the chat room host.
   * 
   * @param chatRoomHost
   *          the new chat room host
   */
  public void setChatRoomHost(final String chatRoomHost) {
    this.chatRoomHost = chatRoomHost;
  }

  /**
   * Sets the countries.
   * 
   * @param countries
   *          the new countries
   */
  public void setCountries(final List<I18nCountry> countries) {
    this.countries = countries;
  }

  /**
   * Sets the current c cversion.
   * 
   * @param currentCCversion
   *          the new current c cversion
   */
  public void setCurrentCCversion(final String currentCCversion) {
    this.currentCCversion = currentCCversion;
  }

  /**
   * Sets the default license.
   * 
   * @param defaultLicense
   *          the new default license
   */
  public void setDefaultLicense(final License defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  /**
   * Sets the default ws theme.
   * 
   * @param defaultWsTheme
   *          the new default ws theme
   */
  public void setDefaultWsTheme(final String defaultWsTheme) {
    this.defaultWsTheme = defaultWsTheme;
  }

  /**
   * Sets the def tutorial language.
   * 
   * @param defTutorialLanguage
   *          the new def tutorial language
   */
  public void setDefTutorialLanguage(final String defTutorialLanguage) {
    this.defTutorialLanguage = defTutorialLanguage;
  }

  /**
   * Sets the feedback enabled.
   * 
   * @param feedbackEnabled
   *          the new feedback enabled
   */
  public void setFeedbackEnabled(final boolean feedbackEnabled) {
    this.feedbackEnabled = feedbackEnabled;
  }

  /**
   * Sets the flv embed object.
   * 
   * @param flvEmbedObject
   *          the new flv embed object
   */
  public void setFlvEmbedObject(final String flvEmbedObject) {
    this.flvEmbedObject = flvEmbedObject;
  }

  /**
   * Sets the full translated languages.
   * 
   * @param fullTranslatedLanguages
   *          the new full translated languages
   */
  public void setFullTranslatedLanguages(final List<I18nLanguage> fullTranslatedLanguages) {
    this.fullTranslatedLanguages = fullTranslatedLanguages;
  }

  /**
   * Sets the gallery permitted extensions.
   * 
   * @param galleryPermittedExtensions
   *          the new gallery permitted extensions
   */
  public void setGalleryPermittedExtensions(final String galleryPermittedExtensions) {
    this.galleryPermittedExtensions = galleryPermittedExtensions;
  }

  /**
   * Sets the group tools.
   * 
   * @param groupTools
   *          the new group tools
   */
  public void setGroupTools(final List<ToolSimple> groupTools) {
    this.groupTools = groupTools;
  }

  /**
   * Setg space themes.
   * 
   * @param gSpaceThemes
   *          the g space themes
   */
  public void setgSpaceThemes(final HashMap<String, GSpaceTheme> gSpaceThemes) {
    this.gSpaceThemes = gSpaceThemes;
  }

  /**
   * Sets the img cropsize.
   * 
   * @param imgCropsize
   *          the new img cropsize
   */
  public void setImgCropsize(final int imgCropsize) {
    this.imgCropsize = imgCropsize;
  }

  /**
   * Sets the img iconsize.
   * 
   * @param imgIconsize
   *          the new img iconsize
   */
  public void setImgIconsize(final int imgIconsize) {
    this.imgIconsize = imgIconsize;
  }

  /**
   * Sets the img resizewidth.
   * 
   * @param imgResizewidth
   *          the new img resizewidth
   */
  public void setImgResizewidth(final int imgResizewidth) {
    this.imgResizewidth = imgResizewidth;
  }

  /**
   * Sets the img thumbsize.
   * 
   * @param imgThumbsize
   *          the new img thumbsize
   */
  public void setImgThumbsize(final int imgThumbsize) {
    this.imgThumbsize = imgThumbsize;
  }

  /**
   * Sets the languages.
   * 
   * @param languages
   *          the new languages
   */
  public void setLanguages(final List<I18nLanguage> languages) {
    this.languages = languages;
  }

  /**
   * Sets the licenses.
   * 
   * @param licenses
   *          the new licenses
   */
  public void setLicenses(final List<License> licenses) {
    this.licenses = licenses;
  }

  /**
   * Sets the max file size in mb.
   * 
   * @param maxFileSizeInMb
   *          the new max file size in mb
   */
  public void setMaxFileSizeInMb(final String maxFileSizeInMb) {
    this.maxFileSizeInMb = maxFileSizeInMb;
  }

  /**
   * Sets the mp3 embed object.
   * 
   * @param mp3EmbedObject
   *          the new mp3 embed object
   */
  public void setMp3EmbedObject(final String mp3EmbedObject) {
    this.mp3EmbedObject = mp3EmbedObject;
  }

  /**
   * Sets the ogg embed object.
   * 
   * @param oggEmbedObject
   *          the new ogg embed object
   */
  public void setOggEmbedObject(final String oggEmbedObject) {
    this.oggEmbedObject = oggEmbedObject;
  }

  /**
   * Sets the public space visible.
   * 
   * @param publicSpaceVisible
   *          the new public space visible
   */
  public void setPublicSpaceVisible(final boolean publicSpaceVisible) {
    this.publicSpaceVisible = publicSpaceVisible;
  }

  /**
   * Sets the reserved words.
   * 
   * @param reservedWords
   *          the new reserved words
   */
  public void setReservedWords(final ReservedWordsRegistryDTO reservedWords) {
    this.reservedWords = reservedWords;
  }

  /**
   * Sets the site common name.
   * 
   * @param siteCommonName
   *          the new site common name
   */
  public void setSiteCommonName(final String siteCommonName) {
    this.siteCommonName = siteCommonName;
  }

  /**
   * Sets the site logo url.
   * 
   * @param siteLogoUrl
   *          the new site logo url
   */
  public void setSiteLogoUrl(final String siteLogoUrl) {
    this.siteLogoUrl = siteLogoUrl;
  }

  /**
   * Sets the site logo url on over.
   * 
   * @param siteLogoUrlOnOver
   *          the new site logo url on over
   */
  public void setSiteLogoUrlOnOver(final String siteLogoUrlOnOver) {
    this.siteLogoUrlOnOver = siteLogoUrlOnOver;
  }

  /**
   * Sets the site short name.
   * 
   * @param siteShortName
   *          the new site short name
   */
  public void setSiteShortName(final String siteShortName) {
    this.siteShortName = siteShortName;
  }

  /**
   * Sets the site url.
   * 
   * @param siteUrl
   *          the new site url
   */
  public void setSiteUrl(final String siteUrl) {
    this.siteUrl = siteUrl;
  }

  /**
   * Sets the store untranslated strings.
   * 
   * @param storeUntranslatedStrings
   *          the new store untranslated strings
   */
  public void setStoreUntranslatedStrings(final boolean storeUntranslatedStrings) {
    this.storeUntranslatedStrings = storeUntranslatedStrings;
  }

  /**
   * Sets the timezones.
   * 
   * @param timezones
   *          the new timezones
   */
  public void setTimezones(final String[] timezones) {
    this.timezones = timezones;
  }

  /**
   * Sets the translator enabled.
   * 
   * @param translatorEnabled
   *          the new translator enabled
   */
  public void setTranslatorEnabled(final boolean translatorEnabled) {
    this.translatorEnabled = translatorEnabled;
  }

  /**
   * Sets the tutorial languages.
   * 
   * @param tutorialLanguages
   *          the new tutorial languages
   */
  public void setTutorialLanguages(final List<String> tutorialLanguages) {
    this.tutorialLanguages = tutorialLanguages;
  }

  /**
   * Sets the use client content cache.
   * 
   * @param useClientContentCache
   *          the new use client content cache
   */
  public void setUseClientContentCache(final boolean useClientContentCache) {
    this.useClientContentCache = useClientContentCache;
  }

  /**
   * Sets the user info.
   * 
   * @param currentUserInfo
   *          the new user info
   */
  public void setUserInfo(final UserInfo currentUserInfo) {
    this.userInfo = currentUserInfo;
  }

  /**
   * Sets the user tools.
   * 
   * @param userTools
   *          the new user tools
   */
  public void setUserTools(final List<ToolSimple> userTools) {
    this.userTools = userTools;
  }

  /**
   * Use client content cache.
   * 
   * @return true, if successful
   */
  public boolean useClientContentCache() {
    return getUseClientContentCache();
  }
}
