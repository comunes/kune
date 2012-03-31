/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared.dto;

import java.util.List;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class StateAbstractDTO implements IsSerializable {
  private List<String> enabledTools;
  private GroupDTO group;
  private ContainerDTO rootContainer;
  private SocialNetworkDataDTO socialNetworkData;
  private StateToken stateToken;
  private String title;
  private String toolName;

  public StateAbstractDTO() {
  }

  public List<String> getEnabledTools() {
    return enabledTools;
  }

  public GroupDTO getGroup() {
    return group;
  }

  public SocialNetworkDTO getGroupMembers() {
    return socialNetworkData.getGroupMembers();
  }

  public AccessRights getGroupRights() {
    return socialNetworkData.getGroupRights();
  }

  public ParticipationDataDTO getParticipation() {
    return socialNetworkData.getUserParticipation();
  }

  public ContainerDTO getRootContainer() {
    return rootContainer;
  }

  public SocialNetworkDataDTO getSocialNetworkData() {
    return socialNetworkData;
  }

  public StateToken getStateToken() {
    return stateToken;
  }

  public String getTitle() {
    return title;
  }

  public String getToolName() {
    return toolName;
  }

  public UserBuddiesDataDTO getUserBuddies() {
    return socialNetworkData.getUserBuddies();
  }

  public void setEnabledTools(final List<String> enabledTools) {
    this.enabledTools = enabledTools;
  }

  public void setGroup(final GroupDTO group) {
    this.group = group;
  }

  public void setRootContainer(final ContainerDTO rootContainer) {
    this.rootContainer = rootContainer;
  }

  public void setSocialNetworkData(final SocialNetworkDataDTO socialNetworkData) {
    this.socialNetworkData = socialNetworkData;
  }

  public void setStateToken(final StateToken stateToken) {
    this.stateToken = stateToken;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public void setToolName(final String toolName) {
    this.toolName = toolName;
  }

  @Override
  public String toString() {
    return "StateDTO[" + getStateToken() + "]";
  }

}
