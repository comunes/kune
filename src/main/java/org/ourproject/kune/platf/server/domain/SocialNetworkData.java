package org.ourproject.kune.platf.server.domain;

import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.sn.ParticipationData;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;

public class SocialNetworkData {
    private SocialNetworkVisibility socialNetworkVisibility;
    private SocialNetwork groupMembers;
    private ParticipationData userParticipation;
    private UserBuddiesVisibility userBuddiesVisibility;
    private UserBuddiesData userBuddies;
    private AccessRights groupRights;
    private boolean isBuddiesVisible;
    private boolean isMembersVisible;

    public SocialNetworkData() {
        this(null, null, null, null, null, null, false, false);
    }

    public SocialNetworkData(SocialNetworkVisibility socialNetworkVisibility, SocialNetwork groupMembers,
            ParticipationData userParticipation, UserBuddiesVisibility userBuddiesVisibility,
            UserBuddiesData userBuddies, AccessRights groupRights, boolean isBuddiesVisible, boolean isMembersVisible) {
        this.socialNetworkVisibility = socialNetworkVisibility;
        this.groupMembers = groupMembers;
        this.userParticipation = userParticipation;
        this.userBuddiesVisibility = userBuddiesVisibility;
        this.userBuddies = userBuddies;
        this.groupRights = groupRights;
        this.isBuddiesVisible = isBuddiesVisible;
        this.isMembersVisible = isMembersVisible;
    }

    public SocialNetwork getGroupMembers() {
        return groupMembers;
    }

    public AccessRights getGroupRights() {
        return groupRights;
    }

    public boolean getIsBuddiesVisible() {
        return isBuddiesVisible;
    }

    public boolean getIsMembersVisible() {
        return isMembersVisible;
    }

    public SocialNetworkVisibility getSocialNetworkVisibility() {
        return socialNetworkVisibility;
    }

    public UserBuddiesData getUserBuddies() {
        return userBuddies;
    }

    public UserBuddiesVisibility getUserBuddiesVisibility() {
        return userBuddiesVisibility;
    }

    public ParticipationData getUserParticipation() {
        return userParticipation;
    }

    public boolean isBuddiesVisible() {
        return isBuddiesVisible;
    }

    public boolean isMembersVisible() {
        return isMembersVisible;
    }

    public void setBuddiesVisible(boolean isBuddiesVisible) {
        this.isBuddiesVisible = isBuddiesVisible;
    }

    public void setGroupMembers(SocialNetwork groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupRights(AccessRights groupRights) {
        this.groupRights = groupRights;
    }

    public void setIsBuddiesVisible(boolean isBuddiesVisible) {
        setBuddiesVisible(isBuddiesVisible);
    }

    public void setIsMembersVisible(boolean isMembersVisible) {
        setMembersVisible(isMembersVisible);
    }

    public void setMembersVisible(boolean isMembersVisible) {
        this.isMembersVisible = isMembersVisible;
    }

    public void setSocialNetworkVisibility(SocialNetworkVisibility socialNetworkVisibility) {
        this.socialNetworkVisibility = socialNetworkVisibility;
    }

    public void setUserBuddies(UserBuddiesData userBuddies) {
        this.userBuddies = userBuddies;
    }

    public void setUserBuddiesVisibility(UserBuddiesVisibility userBuddiesVisibility) {
        this.userBuddiesVisibility = userBuddiesVisibility;
    }

    public void setUserParticipation(ParticipationData userParticipation) {
        this.userParticipation = userParticipation;
    }

}
