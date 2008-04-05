
package org.ourproject.kune.platf.server.access;

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccessServiceDefault implements AccessService {

    private final FinderService finder;
    private final AccessRightsService accessRightsService;
    private final Group groupFinder;

    @Inject
    public AccessServiceDefault(final FinderService finder, final Group groupFinder) {
        this(finder, new AccessRightsServiceDefault(), groupFinder);
    }

    public AccessServiceDefault(final FinderService finder, final AccessRightsService accessRightsService,
            final Group groupFinder) {
        this.finder = finder;
        this.accessRightsService = accessRightsService;
        this.groupFinder = groupFinder;
    }

    public Access getAccess(final User user, final StateToken token, final Group defaultGroup,
            final AccessType accessType) throws SerializableException {
        checkGroupExistence(token);
        Content descriptor = finder.getContent(token, defaultGroup);
        Access access = new Access(descriptor, descriptor.getContainer());
        addContentRights(access, user);
        addFolderRights(access, user);
        addGroupRights(access, user);
        if (!isValid(accessType, access.getContentRights()) || !isValid(accessType, access.getFolderRights())) {
            throw new AccessViolationException();
        }
        return access;
    }

    private void checkGroupExistence(final StateToken token) throws ContentNotFoundException {
        if (token.hasGroup()) {
            try {
                String tokenGroup = token.getGroup();
                groupFinder.findByShortName(tokenGroup);
            } catch (NoResultException e) {
                throw new ContentNotFoundException();
            }
        }
    }

    public Content accessToContent(final Long contentId, final User user, final AccessType accessType)
            throws SerializableException {
        Content descriptor = finder.getContent(contentId);
        Access access = new Access(descriptor, null);
        addContentRights(access, user);
        return check(access, access.getContentRights(), accessType).getContent();
    }

    public Access getFolderAccess(final Group group, final Long folderId, final User user, final AccessType accessType)
            throws SerializableException {
        Container folder = finder.getFolder(folderId);
        if (!folder.getOwner().equals(group)) {
            throw new AccessViolationException();
        }
        Access access = new Access(null, folder);
        addFolderRights(access, user);
        return check(access, access.getFolderRights(), accessType);
    }

    private Access check(final Access access, final AccessRights rights, final AccessType accessType)
            throws AccessViolationException {
        if (!isValid(accessType, rights)) {
            throw new AccessViolationException();
        }
        return access;
    }

    private boolean isValid(final AccessType accessType, final AccessRights rights) {
        switch (accessType) {
        case READ:
            return rights.isVisible();
        case EDIT:
            return rights.isEditable();
        case ADMIN:
            return rights.isAdministrable();
        default:
            return false;
        }
    }

    private void addGroupRights(final Access access, final User user) {
        if (!access.hasGroupRights()) {
            access.setGroupRights(accessRightsService.get(user, access.getGroupAccessLists()));
        }
    }

    private void addFolderRights(final Access access, final User user) {
        if (!access.hasFolderRights()) {
            access.setFolderRights(accessRightsService.get(user, access.getFolderAccessLists()));
        }
    }

    private void addContentRights(final Access access, final User user) {
        if (!access.hasContentRights()) {
            access.setContentRights(accessRightsService.get(user, access.getContentAccessLists()));
        }
    }

}
