/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.server.domain.ExtMediaDescrip;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.tool.ToolSimple;
import org.ourproject.kune.platf.server.users.UserInfo;

public class InitData {

    private String siteUrl;
    private List<License> licenses;
    private List<I18nLanguage> languages;
    private List<I18nCountry> countries;
    private String[] timezones;
    private UserInfo userInfo;
    private String chatHttpBase;
    private String siteDomain;
    private String chatDomain;
    private String chatRoomHost;
    private License defaultLicense;
    private String currentCCversion;
    private String defaultWsTheme;
    private String[] wsThemes;
    private String siteLogoUrl;
    private String galleryPermittedExtensions;
    private String maxFileSizeInMb;
    private int imgResizewidth;
    private int imgThumbsize;
    private int imgCropsize;
    private int imgIconsize;
    private ArrayList<ToolSimple> userTools;
    private ArrayList<ToolSimple> groupTools;
    private List<ExtMediaDescrip> extMediaDescrips;

    private String flvEmbedObject;

    private String oggEmbedObject;

    private String mp3EmbedObject;

    private String aviEmbedObject;

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

    public ArrayList<ToolSimple> getGroupTools() {
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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public ArrayList<ToolSimple> getUserTools() {
        return userTools;
    }

    public String[] getWsThemes() {
        return wsThemes;
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

    public void setFlvEmbedObject(final String flvEmbedObject) {
        this.flvEmbedObject = flvEmbedObject;
    }

    public void setGalleryPermittedExtensions(final String galleryPermittedExtensions) {
        this.galleryPermittedExtensions = galleryPermittedExtensions;
    }

    public void setGroupTools(final ArrayList<ToolSimple> groupTools) {
        this.groupTools = groupTools;
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

    public void setUserInfo(final UserInfo currentUserInfo) {
        this.userInfo = currentUserInfo;
    }

    public void setUserTools(final ArrayList<ToolSimple> userTools) {
        this.userTools = userTools;
    }

    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

}
