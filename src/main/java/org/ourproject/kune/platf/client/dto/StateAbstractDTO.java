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
