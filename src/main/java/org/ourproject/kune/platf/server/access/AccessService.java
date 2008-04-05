
package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface AccessService {

    Access getAccess(User user, StateToken token, Group defaultGroup, AccessType accessType)
            throws SerializableException;

    Access getFolderAccess(Group group, Long folderId, User user, AccessType accessType) throws SerializableException;

    Content accessToContent(Long contentId, User user, AccessType accessType) throws SerializableException;

}
