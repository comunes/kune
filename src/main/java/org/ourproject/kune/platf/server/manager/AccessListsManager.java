package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;

public interface AccessListsManager {

    AccessLists get(ContentDescriptor contentDescriptor);

}
