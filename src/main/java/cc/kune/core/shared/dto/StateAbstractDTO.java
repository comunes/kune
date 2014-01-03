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

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class StateAbstractDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class StateAbstractDTO implements IsSerializable {

  /** The enabled tools. */
  private List<String> enabledTools;

  /** The group. */
  private GroupDTO group;

  /** The root container. */
  private ContainerDTO rootContainer;

  /** The social network data. */
  private SocialNetworkDataDTO socialNetworkData;

  /** The state token. */
  private StateToken stateToken;

  /** The title. */
  private String title;

  /** The tool name. */
  private String toolName;

  /**
   * Instantiates a new state abstract dto.
   */
  public StateAbstractDTO() {
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
   * Gets the group.
   * 
   * @return the group
   */
  public GroupDTO getGroup() {
    return group;
  }

  /**
   * Gets the group members.
   * 
   * @return the group members
   */
  public SocialNetworkDTO getGroupMembers() {
    return socialNetworkData.getGroupMembers();
  }

  /**
   * Gets the group rights.
   * 
   * @return the group rights
   */
  public AccessRights getGroupRights() {
    return socialNetworkData.getGroupRights();
  }

  /**
   * Gets the participation.
   * 
   * @return the participation
   */
  public ParticipationDataDTO getParticipation() {
    return socialNetworkData.getUserParticipation();
  }

  /**
   * Gets the root container.
   * 
   * @return the root container
   */
  public ContainerDTO getRootContainer() {
    return rootContainer;
  }

  /**
   * Gets the social network data.
   * 
   * @return the social network data
   */
  public SocialNetworkDataDTO getSocialNetworkData() {
    return socialNetworkData;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  public StateToken getStateToken() {
    return stateToken;
  }

  /**
   * Gets the title.
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the tool name.
   * 
   * @return the tool name
   */
  public String getToolName() {
    return toolName;
  }

  /**
   * Gets the user buddies.
   * 
   * @return the user buddies
   */
  public UserBuddiesDataDTO getUserBuddies() {
    return socialNetworkData.getUserBuddies();
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
   * Sets the group.
   * 
   * @param group
   *          the new group
   */
  public void setGroup(final GroupDTO group) {
    this.group = group;
  }

  /**
   * Sets the root container.
   * 
   * @param rootContainer
   *          the new root container
   */
  public void setRootContainer(final ContainerDTO rootContainer) {
    this.rootContainer = rootContainer;
  }

  /**
   * Sets the social network data.
   * 
   * @param socialNetworkData
   *          the new social network data
   */
  public void setSocialNetworkData(final SocialNetworkDataDTO socialNetworkData) {
    this.socialNetworkData = socialNetworkData;
  }

  /**
   * Sets the state token.
   * 
   * @param stateToken
   *          the new state token
   */
  public void setStateToken(final StateToken stateToken) {
    this.stateToken = stateToken;
  }

  /**
   * Sets the title.
   * 
   * @param title
   *          the new title
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Sets the tool name.
   * 
   * @param toolName
   *          the new tool name
   */
  public void setToolName(final String toolName) {
    this.toolName = toolName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "StateDTO[" + getStateToken() + "]";
  }

}
