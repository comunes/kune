
package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;

public interface FinderService {

    Content getContent(StateToken token, Group defaultGroup) throws SerializableException;

    Content getContent(Long contentId) throws SerializableException;

    Container getFolder(Long folderId) throws SerializableException;

    Rate getRate(User user, Content content);

    Double getRateAvg(Content content);

    Long getRateByUsers(Content content);

}
