package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParticipationDataDTO implements IsSerializable {

    private List<LinkDTO> groupsIsAdmin;
    private List<LinkDTO> groupsIsCollab;

    public ParticipationDataDTO() {
        this(null, null);
    }

    public ParticipationDataDTO(final List<LinkDTO> groupsIsAdmin, final List<LinkDTO> groupsIsCollab) {
        this.groupsIsAdmin = groupsIsAdmin;
        this.groupsIsCollab = groupsIsCollab;
    }

    public List<LinkDTO> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<LinkDTO> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public List<LinkDTO> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<LinkDTO> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

}
