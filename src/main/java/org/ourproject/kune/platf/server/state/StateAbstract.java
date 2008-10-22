package org.ourproject.kune.platf.server.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.sn.ParticipationData;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;

public abstract class StateAbstract {

    private String title;
    private Group group;
    private AccessLists accessLists;
    private AccessRights groupRights;
    private SocialNetwork groupMembers;
    private List<String> enabledTools;
    private ParticipationData participation;
    private UserBuddiesData userBuddies;
    private StateToken stateToken;

    public AccessLists getAccessLists() {
        return accessLists;
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

    public void setAccessLists(final AccessLists accessLists) {
        this.accessLists = accessLists;
    }

    public void setEnabledTools(List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setGroupMembers(final SocialNetwork groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(final AccessRights groupRights) {
        this.groupRights = groupRights;
    }

    public void setParticipation(final ParticipationData participation) {
        this.participation = participation;
    }

    public void setStateToken(final StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUserBuddies(UserBuddiesData userBuddies) {
        this.userBuddies = userBuddies;
    }

}
