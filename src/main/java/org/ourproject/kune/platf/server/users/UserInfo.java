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

import org.ourproject.kune.platf.server.domain.CustomProperties;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;

public class UserInfo {
    private String shortName;
    private String name;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private List<Group> groupsIsAdmin;
    private List<Group> groupsIsCollab;
    private String userHash;
    private I18nLanguage language;
    private I18nCountry country;
    private CustomProperties customProperties;

    public String getChatName() {
	return chatName;
    }

    public String getChatPassword() {
	return chatPassword;
    }

    public I18nCountry getCountry() {
	return country;
    }

    public CustomProperties getCustomProperties() {
	return customProperties;
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
	return language;
    }

    public String getName() {
	return name;
    }

    public String getShortName() {
	return shortName;
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

    public void setCountry(final I18nCountry country) {
	this.country = country;
    }

    public void setCustomProperties(final CustomProperties customProperties) {
	this.customProperties = customProperties;
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

    public void setLanguage(final I18nLanguage language) {
	this.language = language;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

    public void setUserHash(final String userHash) {
	this.userHash = userHash;
    }

}
