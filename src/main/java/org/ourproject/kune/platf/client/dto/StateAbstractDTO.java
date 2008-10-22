package org.ourproject.kune.platf.client.dto;

import java.util.List;

public abstract class StateAbstractDTO {

    private AccessListsDTO accessLists;
    private List<String> enabledTools;
    private GroupDTO group;
    private SocialNetworkDTO groupMembers;
    private ParticipationDataDTO participation;
    private UserBuddiesDataDTO userBuddies;
    private StateToken stateToken;
    private String title;
    private AccessRightsDTO groupRights;

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public List<String> getEnabledTools() {
        return enabledTools;
    }

    public GroupDTO getGroup() {
        return this.group;
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

    public void setAccessLists(final AccessListsDTO accessLists) {
        this.accessLists = accessLists;
    }

    public void setEnabledTools(List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroup(final GroupDTO group) {
        this.group = group;
    }

    public void setGroupMembers(final SocialNetworkDTO groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(final AccessRightsDTO groupRights) {
        this.groupRights = groupRights;
    }

    public void setParticipation(final ParticipationDataDTO participation) {
        this.participation = participation;
    }

    public void setStateToken(final StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUserBuddies(UserBuddiesDataDTO userBuddies) {
        this.userBuddies = userBuddies;
    }

}
