package org.ourproject.kune.platf.client.stubs;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.state.ApplicationState;

public class NiceState extends ApplicationState {

    public NiceState() {
	super("userHash");
	setGroup(new GroupDTO("group name", "groupShortName", "group description", null, GroupDTO.TYPE_ORGANIZATION));
    }

}
