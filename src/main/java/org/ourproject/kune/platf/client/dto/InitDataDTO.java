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
    private ArrayList<LicenseDTO> licenses;
    private ArrayList<I18nLanguageSimpleDTO> languages;
    private ArrayList<I18nCountryDTO> countries;
    private String[] timezones;
    UserInfoDTO userInfo;
    private String chatHttpBase;
    private String siteDomain;
    private String chatDomain;
    private String chatRoomHost;
    private String defaultWsTheme;
    private String[] wsThemes;

    public ArrayList<LicenseDTO> getLicenses() {
        return licenses;
    }

    public void setLicenses(final ArrayList<LicenseDTO> licenses) {
        this.licenses = licenses;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(final UserInfoDTO currentUser) {
        this.userInfo = currentUser;
    }

    public String getChatHttpBase() {
        return chatHttpBase;
    }

    public void setChatHttpBase(final String chatHttpBase) {
        this.chatHttpBase = chatHttpBase;
    }

    public String getChatDomain() {
        return chatDomain;
    }

    public void setChatDomain(final String chatDomain) {
        this.chatDomain = chatDomain;
    }

    public String getChatRoomHost() {
        return chatRoomHost;
    }

    public void setChatRoomHost(final String chatRoomHost) {
        this.chatRoomHost = chatRoomHost;
    }

    public boolean hasUser() {
        return getUserInfo() != null;
    }

    public String getDefaultWsTheme() {
        return defaultWsTheme;
    }

    public void setDefaultWsTheme(final String defaultWsTheme) {
        this.defaultWsTheme = defaultWsTheme;
    }

    public String[] getWsThemes() {
        return wsThemes;
    }

    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

    public ArrayList<I18nLanguageSimpleDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(final ArrayList<I18nLanguageSimpleDTO> languages) {
        this.languages = languages;
    }

    public ArrayList<I18nCountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(final ArrayList<I18nCountryDTO> countries) {
        this.countries = countries;
    }

    public void setTimezones(final String[] timezones) {
        this.timezones = timezones;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(final String siteDomain) {
        this.siteDomain = siteDomain;
    }
}
