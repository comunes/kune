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
package org.ourproject.kune.platf.client.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InitDataDTO implements IsSerializable {
    private UserInfoDTO userInfo;
    private ArrayList<LicenseDTO> licenses;
    private ArrayList<I18nLanguageSimpleDTO> languages;
    private ArrayList<I18nCountryDTO> countries;
    private String[] timezones;
    private String chatHttpBase;
    private String siteDomain;
    private String chatDomain;
    private String chatRoomHost;
    private String defaultWsTheme;
    private LicenseDTO defaultLicense;
    private String currentCCversion;
    private String[] wsThemes;
    private String siteLogoUrl;
    private String galleryPermittedExtensions;
    private String maxFileSizeInMb;
    private int imgResizewidth;
    private int imgThumbsize;
    private int imgCropsize;
    private int imgIconsize;
    private ArrayList<ToolSimpleDTO> userTools;
    private ArrayList<ToolSimpleDTO> groupTools;

    public String getChatDomain() {
        return chatDomain;
    }

    public String getChatHttpBase() {
        return chatHttpBase;
    }

    public String getChatRoomHost() {
        return chatRoomHost;
    }

    public ArrayList<I18nCountryDTO> getCountries() {
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

    public String getGalleryPermittedExtensions() {
        return galleryPermittedExtensions;
    }

    public ArrayList<ToolSimpleDTO> getGroupTools() {
        return groupTools;
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

    public ArrayList<I18nLanguageSimpleDTO> getLanguages() {
        return languages;
    }

    public ArrayList<LicenseDTO> getLicenses() {
        return licenses;
    }

    public String getMaxFileSizeInMb() {
        return maxFileSizeInMb;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public String getSiteLogoUrl() {
        return siteLogoUrl;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public ArrayList<ToolSimpleDTO> getUserTools() {
        return userTools;
    }

    public String[] getWsThemes() {
        return wsThemes;
    }

    public boolean hasUser() {
        return getUserInfo() != null;
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

    public void setCountries(final ArrayList<I18nCountryDTO> countries) {
        this.countries = countries;
    }

    public void setCurrentCCversion(String currentCCversion) {
        this.currentCCversion = currentCCversion;
    }

    public void setDefaultLicense(LicenseDTO defaultLicense) {
        this.defaultLicense = defaultLicense;
    }

    public void setDefaultWsTheme(final String defaultWsTheme) {
        this.defaultWsTheme = defaultWsTheme;
    }

    public void setGalleryPermittedExtensions(final String galleryPermittedExtensions) {
        this.galleryPermittedExtensions = galleryPermittedExtensions;
    }

    public void setGroupTools(ArrayList<ToolSimpleDTO> groupTools) {
        this.groupTools = groupTools;
    }

    public void setImgCropsize(int imgCropsize) {
        this.imgCropsize = imgCropsize;
    }

    public void setImgIconsize(int imgIconsize) {
        this.imgIconsize = imgIconsize;
    }

    public void setImgResizewidth(int imgResizewidth) {
        this.imgResizewidth = imgResizewidth;
    }

    public void setImgThumbsize(int imgThumbsize) {
        this.imgThumbsize = imgThumbsize;
    }

    public void setLanguages(final ArrayList<I18nLanguageSimpleDTO> languages) {
        this.languages = languages;
    }

    public void setLicenses(final ArrayList<LicenseDTO> licenses) {
        this.licenses = licenses;
    }

    public void setMaxFileSizeInMb(final String maxFileSizeInMb) {
        this.maxFileSizeInMb = maxFileSizeInMb;
    }

    public void setSiteDomain(final String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public void setSiteLogoUrl(final String siteLogoUrl) {
        this.siteLogoUrl = siteLogoUrl;
    }

    public void setTimezones(final String[] timezones) {
        this.timezones = timezones;
    }

    public void setUserInfo(final UserInfoDTO currentUser) {
        this.userInfo = currentUser;
    }

    public void setUserTools(ArrayList<ToolSimpleDTO> userTools) {
        this.userTools = userTools;
    }

    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

}
