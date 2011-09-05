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
package cc.kune.core.shared.dto;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InitDataDTO implements IsSerializable {

  private String aviEmbedObject;
  private String chatDomain;
  private String chatHttpBase;
  private String chatRoomHost;
  private List<I18nCountryDTO> countries;
  private String currentCCversion;
  private LicenseDTO defaultLicense;
  private String defaultWsTheme;
  private List<ExtMediaDescripDTO> extMediaDescrips;
  private String flvEmbedObject;
  private String galleryPermittedExtensions;
  private List<ToolSimpleDTO> groupTools;
  private HashMap<String, GSpaceTheme> gSpaceThemes = new HashMap<String, GSpaceTheme>();
  private int imgCropsize;
  private int imgIconsize;
  private int imgResizewidth;
  private int imgThumbsize;
  private List<I18nLanguageSimpleDTO> languages;
  private List<LicenseDTO> licenses;
  private String maxFileSizeInMb;
  private String mp3EmbedObject;
  private String oggEmbedObject;
  private ReservedWordsRegistryDTO reservedWords;
  private String siteDomain;
  private String siteLogoUrl;
  private String siteUrl;
  private String[] timezones;
  private UserInfoDTO userInfo;
  private List<ToolSimpleDTO> userTools;

  public InitDataDTO() {
  }

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

  public List<I18nCountryDTO> getCountries() {
    return countries;
  }

  public String getCurrentCCversion() {
    return currentCCversion;
  }

  public LicenseDTO getDefaultLicense() {
    return defaultLicense;
  }

  public String getDefaultWsTheme() {
    return defaultWsTheme;
  }

  public List<ExtMediaDescripDTO> getExtMediaDescrips() {
    return extMediaDescrips;
  }

  public String getFlvEmbedObject() {
    return flvEmbedObject;
  }

  public String getGalleryPermittedExtensions() {
    return galleryPermittedExtensions;
  }

  public List<ToolSimpleDTO> getGroupTools() {
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

  public List<I18nLanguageSimpleDTO> getLanguages() {
    return languages;
  }

  public List<LicenseDTO> getLicenses() {
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

  @Deprecated
  public String getSiteDomain() {
    return siteDomain;
  }

  public String getSiteLogoUrl() {
    return siteLogoUrl;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public String[] getTimezones() {
    return timezones;
  }

  public UserInfoDTO getUserInfo() {
    return userInfo;
  }

  public List<ToolSimpleDTO> getUserTools() {
    return userTools;
  }

  public boolean hasUser() {
    return getUserInfo() != null;
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

  public void setCountries(final List<I18nCountryDTO> countries) {
    this.countries = countries;
  }

  public void setCurrentCCversion(final String currentCCversion) {
    this.currentCCversion = currentCCversion;
  }

  public void setDefaultLicense(final LicenseDTO defaultLicense) {
    this.defaultLicense = defaultLicense;
  }

  public void setDefaultWsTheme(final String defaultWsTheme) {
    this.defaultWsTheme = defaultWsTheme;
  }

  public void setExtMediaDescrips(final List<ExtMediaDescripDTO> extMediaDescrips) {
    this.extMediaDescrips = extMediaDescrips;
  }

  public void setFlvEmbedObject(final String flvEmbedObject) {
    this.flvEmbedObject = flvEmbedObject;
  }

  public void setGalleryPermittedExtensions(final String galleryPermittedExtensions) {
    this.galleryPermittedExtensions = galleryPermittedExtensions;
  }

  public void setGroupTools(final List<ToolSimpleDTO> groupTools) {
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

  public void setLanguages(final List<I18nLanguageSimpleDTO> languages) {
    this.languages = languages;
  }

  public void setLicenses(final List<LicenseDTO> licenses) {
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

  @Deprecated
  public void setSiteDomain(final String siteDomain) {
    this.siteDomain = siteDomain;
  }

  public void setSiteLogoUrl(final String siteLogoUrl) {
    this.siteLogoUrl = siteLogoUrl;
  }

  public void setSiteUrl(final String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public void setTimezones(final String[] timezones) {
    this.timezones = timezones;
  }

  public void setUserInfo(final UserInfoDTO currentUser) {
    this.userInfo = currentUser;
  }

  public void setUserTools(final List<ToolSimpleDTO> userTools) {
    this.userTools = userTools;
  }

}
