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
package org.ourproject.kune.platf.server.users;

import java.util.List;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;

public class UserInfo {
    private User user;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private List<Group> groupsIsAdmin;
    private List<Group> groupsIsCollab;
    private String userHash;
    private boolean showDeletedContent;

    public String getChatName() {
        return chatName;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public I18nCountry getCountry() {
        return user.getCountry();
    }

    public List<Group> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public List<Group> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public String getHomePage() {
        return homePage;
    }

    public I18nLanguage getLanguage() {
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

    public User getUser() {
        return user;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    public void setChatPassword(final String chatPassword) {
        this.chatPassword = chatPassword;
    }

    public void setGroupsIsAdmin(final List<Group> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public void setGroupsIsCollab(final List<Group> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    public void setShowDeletedContent(boolean showDeletedContent) {
        this.showDeletedContent = showDeletedContent;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

}
