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
package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.users.UserInfo;

public class InitData {
    private List<License> licenses;
    private List<I18nLanguage> languages;
    private List<I18nCountry> countries;
    private String[] timezones;
    private UserInfo userInfo;
    private String chatHttpBase;
    private String siteDomain;
    private String chatDomain;
    private String chatRoomHost;
    private String defaultWsTheme;
    private String[] wsThemes;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(final UserInfo currentUserInfo) {
        this.userInfo = currentUserInfo;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(final List<License> licenses) {
        this.licenses = licenses;
    }

    public void setChatHttpBase(final String chatHttpBase) {
        this.chatHttpBase = chatHttpBase;
    }

    public String getChatHttpBase() {
        return chatHttpBase;
    }

    public void setChatDomain(final String chatDomain) {
        this.chatDomain = chatDomain;
    }

    public String getChatDomain() {
        return chatDomain;
    }

    public void setChatRoomHost(final String chatRoomHost) {
        this.chatRoomHost = chatRoomHost;
    }

    public String getChatRoomHost() {
        return chatRoomHost;
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

    public List<I18nLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(final List<I18nLanguage> languages) {
        this.languages = languages;
    }

    public List<I18nCountry> getCountries() {
        return countries;
    }

    public void setCountries(final List<I18nCountry> countries) {
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
