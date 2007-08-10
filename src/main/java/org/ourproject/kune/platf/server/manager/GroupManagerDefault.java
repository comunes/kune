package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Group;

import com.google.inject.Inject;

public class GroupManagerDefault implements GroupManager {

    private final Group finder;

    @Inject
    public GroupManagerDefault(final Group finder) {
	this.finder = finder;
    }

    public Group get(final String shortName) {
	return finder.findByShortName(shortName);
    }

}
