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

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfoDTO implements IsSerializable {
    private UserSimpleDTO user;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private String userHash;
    private List<GroupDTO> groupsIsAdmin;
    private List<GroupDTO> groupsIsCollab;
    private boolean showDeletedContent;

    public String getChatName() {
        return chatName;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public I18nCountryDTO getCountry() {
        return user.getCountry();
    }

    public List<GroupDTO> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public List<GroupDTO> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public String getHomePage() {
        return homePage;
    }

    public I18nLanguageDTO getLanguage() {
        return user.getLanguage();
    }

    public String getName() {
        return user.getName();
    }

    public String getShortName() {
        return user.getShortName();
    }

    public boolean getShowDeletedContent() {
        return showDeletedContent;
    }

    public UserSimpleDTO getUser() {
        return user;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    public void setChatPassword(final String password) {
        this.chatPassword = password;

    }

    public void setGroupsIsAdmin(final List<GroupDTO> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public void setGroupsIsCollab(final List<GroupDTO> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    public void setShowDeletedContent(boolean showDeletedContent) {
        this.showDeletedContent = showDeletedContent;
    }

    public void setUser(UserSimpleDTO user) {
        this.user = user;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

}
