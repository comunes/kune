/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.shared.dto;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfoDTO implements IsSerializable {
  private String chatName;
  private List<String> enabledTools;
  private Set<GroupDTO> groupsIsAdmin;
  private Set<GroupDTO> groupsIsCollab;
  private List<GroupDTO> groupsIsParticipating;
  private String homePage;
  private boolean showDeletedContent;
  private Long signInCount;
  private UserSimpleDTO user;
  private GroupDTO userGroup;
  private String userHash;

  public String getChatName() {
    return chatName;
  }

  public I18nCountryDTO getCountry() {
    return user.getCountry();
  }

  public List<String> getEnabledTools() {
    return enabledTools;
  }

  public Set<GroupDTO> getGroupsIsAdmin() {
    return groupsIsAdmin;
  }

  public Set<GroupDTO> getGroupsIsCollab() {
    return groupsIsCollab;
  }

  public List<GroupDTO> getGroupsIsParticipating() {
    return groupsIsParticipating;
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

  public Long getSignInCount() {
    return signInCount;
  }

  public UserSimpleDTO getUser() {
    return user;
  }

  public GroupDTO getUserGroup() {
    return userGroup;
  }

  public String getUserHash() {
    return userHash;
  }

  public void setChatName(final String chatName) {
    this.chatName = chatName;
  }

  public void setEnabledTools(final List<String> enabledTools) {
    this.enabledTools = enabledTools;
  }

  public void setGroupsIsAdmin(final Set<GroupDTO> groupsIsAdmin) {
    this.groupsIsAdmin = groupsIsAdmin;
  }

  public void setGroupsIsCollab(final Set<GroupDTO> groupsIsCollab) {
    this.groupsIsCollab = groupsIsCollab;
  }

  public void setGroupsIsParticipating(final List<GroupDTO> groupsIsParticipating) {
    this.groupsIsParticipating = groupsIsParticipating;
  }

  public void setHomePage(final String homePage) {
    this.homePage = homePage;
  }

  public void setShowDeletedContent(final boolean showDeletedContent) {
    this.showDeletedContent = showDeletedContent;
  }

  public void setSignInCount(final Long signInCount) {
    this.signInCount = signInCount;
  }

  public void setUser(final UserSimpleDTO user) {
    this.user = user;
  }

  public void setUserGroup(final GroupDTO userGroup) {
    this.userGroup = userGroup;
  }

  public void setUserHash(final String userHash) {
    this.userHash = userHash;
  }

}
