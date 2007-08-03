package org.ourproject.kune.platf.client.stubs;

import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public class NiceState extends State {

    public NiceState() {
	super("userHash");
	setGroup(new GroupDTO("group name", "groupShortName"));
    }


}
