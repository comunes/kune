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

public abstract class StateAbstractDTO implements IsSerializable {

    private List<String> enabledTools;
    private GroupDTO group;
    private SocialNetworkDTO groupMembers;
    private AccessRightsDTO groupRights;
    private ParticipationDataDTO participation;
    private UserBuddiesDataDTO userBuddies;
    private StateToken stateToken;
    private String title;

    public StateAbstractDTO() {
    }

    public List<String> getEnabledTools() {
        return enabledTools;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public SocialNetworkDTO getGroupMembers() {
        return groupMembers;
    }

    public AccessRightsDTO getGroupRights() {
        return groupRights;
    }

    public ParticipationDataDTO getParticipation() {
        return participation;
    }

    public StateToken getStateToken() {
        return stateToken;
    }

    public String getTitle() {
        return title;
    }

    public UserBuddiesDataDTO getUserBuddies() {
        return userBuddies;
    }

    public void setEnabledTools(List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public void setGroupMembers(SocialNetworkDTO groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(AccessRightsDTO groupRights) {
        this.groupRights = groupRights;
    }

    public void setParticipation(ParticipationDataDTO participation) {
        this.participation = participation;
    }

    public void setStateToken(StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserBuddies(UserBuddiesDataDTO userBuddies) {
        this.userBuddies = userBuddies;
    }

    @Override
    public String toString() {
        return "StateDTO[" + getStateToken() + "]";
    }

}
