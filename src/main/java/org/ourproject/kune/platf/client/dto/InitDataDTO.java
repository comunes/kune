/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InitDataDTO implements IsSerializable {
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    private ArrayList ccLicenses;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    private ArrayList notCCLicenses;

    UserInfoDTO userInfo;
    private String chatHttpBase;
    private String chatDomain;
    private String chatRoomHost;
    private String defaultWsTheme;
    private String[] wsThemes;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO>
     */
    private ArrayList languages;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.I18nCountryDTO>
     */
    private ArrayList countries;

    public ArrayList getCCLicenses() {
        return ccLicenses;
    }

    public void setCCLicenses(final ArrayList ccLicenses) {
        this.ccLicenses = ccLicenses;
    }

    public ArrayList getNotCCLicenses() {
        return notCCLicenses;
    }

    public void setNotCCLicenses(final ArrayList notLicenses) {
        this.notCCLicenses = notLicenses;
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

    public ArrayList getLanguages() {
        return languages;
    }

    public void setLanguages(final ArrayList languages) {
        this.languages = languages;
    }

    public ArrayList getCountries() {
        return countries;
    }

    public void setCountries(final ArrayList countries) {
        this.countries = countries;
    }

}
