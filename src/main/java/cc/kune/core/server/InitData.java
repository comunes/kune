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
package cc.kune.core.server;

import java.util.HashMap;
import java.util.List;

import cc.kune.core.server.tool.ToolSimple;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.domain.ExtMediaDescrip;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.License;

public class InitData {

  private String aviEmbedObject;
  private String chatDomain;
  private String chatHttpBase;
  private String chatRoomHost;
  private List<I18nCountry> countries;
  private String currentCCversion;
  private License defaultLicense;
  private String defaultWsTheme;
  private List<ExtMediaDescrip> extMediaDescrips;
  private boolean feedbackEnabled;
  private String flvEmbedObject;
  private String galleryPermittedExtensions;
  private List<ToolSimple> groupTools;
  private HashMap<String, GSpaceTheme> gSpaceThemes;
  private int imgCropsize;
  private int imgIconsize;
  private int imgResizewidth;
  private int imgThumbsize;
  private List<I18nLanguage> languages;
  private List<License> licenses;
  private String maxFileSizeInMb;
  private String mp3EmbedObject;
  private String oggEmbedObject;
  private ReservedWordsRegistryDTO reservedWords;
  private String siteCommonName;
  private String siteDomain;
  private String siteLogoUrl;
  private String siteShortName;
  private String siteUrl;
  private String[] timezones;
  private boolean translatorEnabled;
  private boolean useClientContentCache;
  private UserInfo userInfo;
  private List<ToolSimple> userTools;

  public String getAviEmbedObject() {
    return aviEmbedObject;
  }

  public String getChatDomain() {
    return chatDomain;
  }

  public String getChatHttpBase() {
    return chatHttpBase;
  }

  public String getChatRoomHost() {
    return chatRoomHost;
  }

  public List<I18nCountry> getCountries() {
    return countries;
  }

  public String getCurrentCCversion() {
    return currentCCversion;
  }

  public License getDefaultLicense() {
    return defaultLicense;
  }

  public String getDefaultWsTheme() {
    return defaultWsTheme;
  }

  public List<ExtMediaDescrip> getExtMediaDescrips() {
    return extMediaDescrips;
  }

  public String getFlvEmbedObject() {
    return flvEmbedObject;
  }

  public String getGalleryPermittedExtensions() {
    return galleryPermittedExtensions;
  }

  public List<ToolSimple> getGroupTools() {
    return groupTools;
  }

  public HashMap<String, GSpaceTheme> getgSpaceThemes() {
    return gSpaceThemes;
  }

  public int getImgCropsize() {
    return imgCropsize;
  }

  public int getImgIconsize() {
    return imgIconsize;
  }

  public int getImgResizewidth() {
    return imgResizewidth;
  }

  public int getImgThumbsize() {
    return imgThumbsize;
  }

  public List<I18nLanguage> getLanguages() {
    return languages;
  }

  public List<License> getLicenses() {
    return licenses;
  }

  public String getMaxFileSizeInMb() {
    return maxFileSizeInMb;
  }

  public String getMp3EmbedObject() {
    return mp3EmbedObject;
  }

  public String getOggEmbedObject() {
    return oggEmbedObject;
  }

  public ReservedWordsRegistryDTO getReservedWords() {
    return this.reservedWords;
  }

  public String getSiteCommonName() {
    return siteCommonName;
  }

  @Deprecated
  public String getSiteDomain() {
    return siteDomain;
  }

  public String getSiteLogoUrl() {
    return siteLogoUrl;
  }

  public String getSiteShortName() {
    return siteShortName;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public String[] getTimezones() {
    return timezones;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public List<ToolSimple> getUserTools() {
    return userTools;
  }

  public boolean isFeedbackEnabled() {
    return feedbackEnabled;
  }

  public boolean isTranslatorEnabled() {
    return translatorEnabled;
  }

  public void setAviEmbedObject(final String aviEmbedObject) {
    this.aviEmbedObject = aviEmbedObject;
  }

  public void setChatDomain(final String chatDomain) {
    this.chatDomain = chatDomain;
  }

  public void setChatHttpBase(final String chatHttpBase) {
    this.chatHttpBase = chatHttpBase;
  }

  public void setChatRoomHost(final String chatRoomHost) {
    this.chatRoomHost = chatRoomHost;
  }

  public void setCountries(final List<I18nCountry> countries) {
    this.countries = countries;
  }

  public void setCurrentCCversion(final String currentCCversion) {
    this.currentCCversion = currentCCversion;
  }

  public void setDefaultLicense(final License defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  public void setDefaultWsTheme(final String defaultWsTheme) {
    this.defaultWsTheme = defaultWsTheme;
  }

  public void setExtMediaDescrips(final List<ExtMediaDescrip> extMediaDescrips) {
    this.extMediaDescrips = extMediaDescrips;
  }

  public void setFeedbackEnabled(final boolean feedbackEnabled) {
    this.feedbackEnabled = feedbackEnabled;
  }

  public void setFlvEmbedObject(final String flvEmbedObject) {
    this.flvEmbedObject = flvEmbedObject;
  }

  public void setGalleryPermittedExtensions(final String galleryPermittedExtensions) {
    this.galleryPermittedExtensions = galleryPermittedExtensions;
  }

  public void setGroupTools(final List<ToolSimple> groupTools) {
    this.groupTools = groupTools;
  }

  public void setgSpaceThemes(final HashMap<String, GSpaceTheme> gSpaceThemes) {
    this.gSpaceThemes = gSpaceThemes;
  }

  public void setImgCropsize(final int imgCropsize) {
    this.imgCropsize = imgCropsize;
  }

  public void setImgIconsize(final int imgIconsize) {
    this.imgIconsize = imgIconsize;
  }

  public void setImgResizewidth(final int imgResizewidth) {
    this.imgResizewidth = imgResizewidth;
  }

  public void setImgThumbsize(final int imgThumbsize) {
    this.imgThumbsize = imgThumbsize;
  }

  public void setLanguages(final List<I18nLanguage> languages) {
    this.languages = languages;
  }

  public void setLicenses(final List<License> licenses) {
    this.licenses = licenses;
  }

  public void setMaxFileSizeInMb(final String maxFileSizeInMb) {
    this.maxFileSizeInMb = maxFileSizeInMb;
  }

  public void setMp3EmbedObject(final String mp3EmbedObject) {
    this.mp3EmbedObject = mp3EmbedObject;
  }

  public void setOggEmbedObject(final String oggEmbedObject) {
    this.oggEmbedObject = oggEmbedObject;
  }

  public void setReservedWords(final ReservedWordsRegistryDTO reservedWords) {
    this.reservedWords = reservedWords;
  }

  public void setSiteCommonName(final String siteCommonName) {
    this.siteCommonName = siteCommonName;
  }

  @Deprecated
  public void setSiteDomain(final String siteDomain) {
    this.siteDomain = siteDomain;
  }

  public void setSiteLogoUrl(final String siteLogoUrl) {
    this.siteLogoUrl = siteLogoUrl;
  }

  public void setSiteShortName(final String siteShortName) {
    this.siteShortName = siteShortName;
  }

  public void setSiteUrl(final String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public void setTimezones(final String[] timezones) {
    this.timezones = timezones;
  }

  public void setTranslatorEnabled(final boolean translatorEnabled) {
    this.translatorEnabled = translatorEnabled;
  }

  public void setUseClientContentCache(final boolean useClientContentCache) {
    this.useClientContentCache = useClientContentCache;
  }

  public void setUserInfo(final UserInfo currentUserInfo) {
    this.userInfo = currentUserInfo;
  }

  public void setUserTools(final List<ToolSimple> userTools) {
    this.userTools = userTools;
  }

  public boolean useClientContentCache() {
    return useClientContentCache;
  }

}
