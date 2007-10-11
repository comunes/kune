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

package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.users.UserInfo;

public class InitData {
    private List<License> ccLicenses;
    private List<License> notCCLicenses;
    private UserInfo userInfo;
    private String chatHttpBase;
    private String chatDomain;
    private String chatRoomHost;

    public UserInfo getUserInfo() {
	return userInfo;
    }

    public void setUserInfo(final UserInfo currentUserInfo) {
	this.userInfo = currentUserInfo;
    }

    public void setCCLicenses(final List<License> ccLicenses) {
	this.ccLicenses = ccLicenses;
    }

    public List<License> getCCLicenses() {
	return ccLicenses;
    }

    public void setNotCCLicenses(final List<License> notCCLicenses) {
	this.notCCLicenses = notCCLicenses;
    }

    public List<License> getNotCCLicenses() {
	return notCCLicenses;
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

}
