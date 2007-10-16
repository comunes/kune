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

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfoDTO implements IsSerializable {
    private String shortName;
    private String name;
    private String chatName;
    private String chatPassword;
    private String homePage;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LinkDTO>
     */
    private List groupsIsAdmin;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LinkDTO>
     */
    private List groupsIsCollab;

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getChatName() {
	return chatName;
    }

    public String getChatPassword() {
	return chatPassword;
    }

    public void setChatPassword(final String password) {
	this.chatPassword = password;

    }

    public void setChatName(final String chatName) {
	this.chatName = chatName;
    }

    public List getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

    public List getGroupsIsCollab() {
	return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List groupsIsCollab) {
	this.groupsIsCollab = groupsIsCollab;
    }

    public String getHomePage() {
	return homePage;
    }

    public void setHomePage(final String homePage) {
	this.homePage = homePage;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

}
