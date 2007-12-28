package org.ourproject.kune.platf.server.auth;

import javax.persistence.NoResultException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.access.AccessType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthorizatedMethodInterceptor implements MethodInterceptor {

    @Inject
    Provider<UserSession> userSessionProvider;
    @Inject
    Provider<GroupManager> groupManagerProvider;
    @Inject
    Provider<AccessService> accessServiceProvider;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        String groupShortName = (String) arguments[1];

        UserSession userSession = userSessionProvider.get();
        GroupManager groupManager = groupManagerProvider.get();
        AccessService accessService = accessServiceProvider.get();

        Authorizated authoAnnotation = invocation.getStaticPart().getAnnotation(Authorizated.class);
        AccessType accessType = authoAnnotation.accessTypeRequired();
        boolean checkContent = authoAnnotation.checkContent();

        User user = userSession.getUser();
        Group group = Group.NO_GROUP;
        try {
            group = groupManager.findByShortName(groupShortName);
        } catch (NoResultException e) {
            // continue, and check later
        }

        if (checkContent) {
            String contentIdS = (String) arguments[2];
            Long contentId = parseId(contentIdS);
            Content content = accessService.accessToContent(contentId, user, accessType);
            if (!content.getFolder().getOwner().equals(group)) {
                throw new AccessViolationException();
            }
        }

        if (!correctMember(user, group, accessType)) {
            throw new AccessViolationException();
        }

        Object result = invocation.proceed();
        return result;
    }

    private boolean correctMember(final User user, final Group group, final AccessType memberType)
            throws AccessViolationException {
        boolean correctMember = false;

        if (group == Group.NO_GROUP) {
            throw new AccessViolationException();
        }
        SocialNetwork sn = group.getSocialNetwork();
        Group userGroup;

        if (user == null) {
            userGroup = Group.NO_GROUP;
        } else {
            userGroup = user.getUserGroup();
        }

        if (memberType.equals(AccessType.ADMIN)) {
            correctMember = sn.isAdmin(userGroup);
        } else if (memberType.equals(AccessType.EDIT)) {
            correctMember = sn.isCollab(userGroup) || sn.isAdmin(userGroup);
        } else if (memberType.equals(AccessType.READ)) {
            correctMember = sn.isViewer(userGroup) || sn.isCollab(userGroup) || sn.isAdmin(userGroup);
        }
        return correctMember;
    }

    private Long parseId(final String documentId) throws ContentNotFoundException {
        try {
            return new Long(documentId);
        } catch (final NumberFormatException e) {
            throw new ContentNotFoundException();
        }
    }
}
