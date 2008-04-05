
package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

public interface GroupManager extends Manager<Group, Long> {

    Group findByShortName(String groupName);

    List<Group> findAdminInGroups(Long groupId);

    List<Group> findCollabInGroups(Long groupId);

    Group createGroup(Group group, User user) throws GroupNameInUseException, UserMustBeLoggedException;

    Group createUserGroup(User user) throws GroupNameInUseException, EmailAddressInUseException;

    Group getDefaultGroup();

    void changeWsTheme(User user, Group group, String theme) throws AccessViolationException;

    /**
     * IMPORTANT: returns null if userId is null
     * 
     * @param userId
     * @return
     */
    Group getGroupOfUserWithId(Long userId);

    SearchResult<Group> search(String search);

    SearchResult<Group> search(String search, Integer firstResult, Integer maxResults);

    void reIndex();

}
