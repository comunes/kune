package org.ourproject.kune.platf.server.domain;

import java.util.List;

public enum GroupListMode {
    NORMAL, NOBODY, EVERYONE;

    public boolean checkIfIncludes(Group group, List<Group> list) {
	switch (this) {
	case NOBODY:
	    return false;
	case EVERYONE:
	    return true;
	default:
	    return list.contains(group);
	}
    }
}
