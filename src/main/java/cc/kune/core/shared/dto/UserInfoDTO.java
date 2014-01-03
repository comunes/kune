/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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

// TODO: Auto-generated Javadoc
/**
 * The Class UserInfoDTO.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserInfoDTO implements IsSerializable {

  /** The chat name. */
  private String chatName;

  /** The client flags. */
  private String clientFlags;

  /** The enabled tools. */
  private List<String> enabledTools;

  /** The groups is admin. */
  private Set<GroupDTO> groupsIsAdmin;

  /** The groups is collab. */
  private Set<GroupDTO> groupsIsCollab;

  /** The groups is participating. */
  private List<GroupDTO> groupsIsParticipating;

  /** The home page. */
  private String homePage;

  /** The session json. */
  private String sessionJSON;

  /** The show deleted content. */
  private boolean showDeletedContent;

  /** The sign in count. */
  private Long signInCount;

  /** The user. */
  private UserSimpleDTO user;

  /** The user group. */
  private GroupDTO userGroup;

  /** The user hash. */
  private String userHash;

  /** The websocket address. */
  private String websocketAddress;

  /**
   * Gets the chat name.
   * 
   * @return the chat name
   */
  public String getChatName() {
    return chatName;
  }

  /**
   * Gets the client flags.
   * 
   * @return the client flags
   */
  public String getClientFlags() {
    return clientFlags;
  }

  /**
   * Gets the country.
   * 
   * @return the country
   */
  public I18nCountryDTO getCountry() {
    return user.getCountry();
  }

  /**
   * Gets the enabled tools.
   * 
   * @return the enabled tools
   */
  public List<String> getEnabledTools() {
    return enabledTools;
  }

  /**
   * Gets the groups is admin.
   * 
   * @return the groups is admin
   */
  public Set<GroupDTO> getGroupsIsAdmin() {
    return groupsIsAdmin;
  }

  /**
   * Gets the groups is collab.
   * 
   * @return the groups is collab
   */
  public Set<GroupDTO> getGroupsIsCollab() {
    return groupsIsCollab;
  }

  /**
   * Gets the groups is participating.
   * 
   * @return the groups is participating
   */
  public List<GroupDTO> getGroupsIsParticipating() {
    return groupsIsParticipating;
  }

  /**
   * Gets the home page.
   * 
   * @return the home page
   */
  public String getHomePage() {
    return homePage;
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguageDTO getLanguage() {
    return user.getLanguage();
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return user.getName();
  }

  /**
   * Gets the session json.
   * 
   * @return the session json
   */
  public String getSessionJSON() {
    return sessionJSON;
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return user.getShortName();
  }

  /**
   * Gets the show deleted content.
   * 
   * @return the show deleted content
   */
  public boolean getShowDeletedContent() {
    return showDeletedContent;
  }

  /**
   * Gets the sign in count.
   * 
   * @return the sign in count
   */
  public Long getSignInCount() {
    return signInCount;
  }

  /**
   * Gets the user.
   * 
   * @return the user
   */
  public UserSimpleDTO getUser() {
    return user;
  }

  /**
   * Gets the user group.
   * 
   * @return the user group
   */
  public GroupDTO getUserGroup() {
    return userGroup;
  }

  /**
   * Gets the user hash.
   * 
   * @return the user hash
   */
  public String getUserHash() {
    return userHash;
  }

  /**
   * Gets the websocket address.
   * 
   * @return the websocket address
   */
  public String getWebsocketAddress() {
    return websocketAddress;
  }

  /**
   * Sets the chat name.
   * 
   * @param chatName
   *          the new chat name
   */
  public void setChatName(final String chatName) {
    this.chatName = chatName;
  }

  /**
   * Sets the client flags.
   * 
   * @param clientFlags
   *          the new client flags
   */
  public void setClientFlags(final String clientFlags) {
    this.clientFlags = clientFlags;
  }

  /**
   * Sets the enabled tools.
   * 
   * @param enabledTools
   *          the new enabled tools
   */
  public void setEnabledTools(final List<String> enabledTools) {
    this.enabledTools = enabledTools;
  }

  /**
   * Sets the groups is admin.
   * 
   * @param groupsIsAdmin
   *          the new groups is admin
   */
  public void setGroupsIsAdmin(final Set<GroupDTO> groupsIsAdmin) {
    this.groupsIsAdmin = groupsIsAdmin;
  }

  /**
   * Sets the groups is collab.
   * 
   * @param groupsIsCollab
   *          the new groups is collab
   */
  public void setGroupsIsCollab(final Set<GroupDTO> groupsIsCollab) {
    this.groupsIsCollab = groupsIsCollab;
  }

  /**
   * Sets the groups is participating.
   * 
   * @param groupsIsParticipating
   *          the new groups is participating
   */
  public void setGroupsIsParticipating(final List<GroupDTO> groupsIsParticipating) {
    this.groupsIsParticipating = groupsIsParticipating;
  }

  /**
   * Sets the home page.
   * 
   * @param homePage
   *          the new home page
   */
  public void setHomePage(final String homePage) {
    this.homePage = homePage;
  }

  /**
   * Sets the session json.
   * 
   * @param sessionJSON
   *          the new session json
   */
  public void setSessionJSON(final String sessionJSON) {
    this.sessionJSON = sessionJSON;
  }

  /**
   * Sets the show deleted content.
   * 
   * @param showDeletedContent
   *          the new show deleted content
   */
  public void setShowDeletedContent(final boolean showDeletedContent) {
    this.showDeletedContent = showDeletedContent;
  }

  /**
   * Sets the sign in count.
   * 
   * @param signInCount
   *          the new sign in count
   */
  public void setSignInCount(final Long signInCount) {
    this.signInCount = signInCount;
  }

  /**
   * Sets the user.
   * 
   * @param user
   *          the new user
   */
  public void setUser(final UserSimpleDTO user) {
    this.user = user;
  }

  /**
   * Sets the user group.
   * 
   * @param userGroup
   *          the new user group
   */
  public void setUserGroup(final GroupDTO userGroup) {
    this.userGroup = userGroup;
  }

  /**
   * Sets the user hash.
   * 
   * @param userHash
   *          the new user hash
   */
  public void setUserHash(final String userHash) {
    this.userHash = userHash;
  }

  /**
   * Sets the websocket address.
   * 
   * @param websocketAddress
   *          the new websocket address
   */
  public void setWebsocketAddress(final String websocketAddress) {
    this.websocketAddress = websocketAddress;
  }

}
