package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SocialNetworkResultDTO implements IsSerializable {

    SocialNetworkDTO groupMembers;

    ParticipationDataDTO userParticipation;

    public SocialNetworkResultDTO() {
        this(null, null);
    }

    public SocialNetworkResultDTO(final SocialNetworkDTO groupMembers, final ParticipationDataDTO userParticipation) {
        this.groupMembers = groupMembers;
        this.userParticipation = userParticipation;
    }

    public SocialNetworkDTO getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(final SocialNetworkDTO groupMembers) {
        this.groupMembers = groupMembers;
    }

    public ParticipationDataDTO getUserParticipation() {
        return userParticipation;
    }

    public void setUserParticipation(final ParticipationDataDTO userParticipation) {
        this.userParticipation = userParticipation;
    }

}
