package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessList;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public interface AccessListManager {

    AccessList get(User user, Content content);

}
