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
package org.ourproject.kune.platf.server.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.sn.ParticipationData;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;

public abstract class StateAbstract {

    private List<String> enabledTools;
    private Group group;
    private SocialNetwork groupMembers;
    private AccessRights groupRights;
    private ParticipationData participation;
    private UserBuddiesData userBuddies;
    private StateToken stateToken;
    private String title;

    public StateAbstract() {
    }

    public List<String> getEnabledTools() {
        return enabledTools;
    }

    public Group getGroup() {
        return group;
    }

    public SocialNetwork getGroupMembers() {
        return groupMembers;
    }

    public AccessRights getGroupRights() {
        return groupRights;
    }

    public ParticipationData getParticipation() {
        return participation;
    }

    public StateToken getStateToken() {
        return stateToken;
    }

    public String getTitle() {
        return title;
    }

    public UserBuddiesData getUserBuddies() {
        return userBuddies;
    }

    public void setEnabledTools(List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setGroupMembers(SocialNetwork groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(AccessRights groupRights) {
        this.groupRights = groupRights;
    }

    public void setParticipation(ParticipationData participation) {
        this.participation = participation;
    }

    public void setStateToken(StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserBuddies(UserBuddiesData userBuddies) {
        this.userBuddies = userBuddies;
    }

    @Override
    public String toString() {
        return "State[" + getStateToken() + "]";
    }

}
