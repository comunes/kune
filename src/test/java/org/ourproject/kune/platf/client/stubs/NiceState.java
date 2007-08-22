package org.ourproject.kune.platf.client.stubs;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.state.Session;

public class NiceState extends Session {

    public NiceState() {
	super("userHash");
	setGroup(new GroupDTO("groupShortName", "group name", "group description", null, GroupDTO.TYPE_ORGANIZATION));
    }

}
