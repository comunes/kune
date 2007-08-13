package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;

public class AccessListsManagerDefault implements AccessListsManager {

    public AccessLists get(final ContentDescriptor contentDescriptor) {
	AccessLists accessLists = contentDescriptor.getAccessLists();
	if (accessLists == null) {
	    accessLists = contentDescriptor.getFolder().getOwner().getSocialNetwork().getDefaultAccessLists();
	}
	return accessLists;
    }
}
