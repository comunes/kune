package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParticipationDataDTO implements IsSerializable {

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LinkDTO>
     */
    private List groupsIsAdmin;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LinkDTO>
     */
    private List groupsIsCollab;

    public ParticipationDataDTO() {
	this(null, null);
    }

    public ParticipationDataDTO(final List groupsIsAdmin, final List groupsIsCollab) {
	this.groupsIsAdmin = groupsIsAdmin;
	this.groupsIsCollab = groupsIsCollab;
    }

    public List getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

    public List getGroupsIsCollab() {
	return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List groupsIsCollab) {
	this.groupsIsCollab = groupsIsCollab;
    }

}
