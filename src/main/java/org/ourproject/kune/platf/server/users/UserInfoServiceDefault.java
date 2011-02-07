/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.utils.ParticipationData;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    private final GroupManager groupManager;
    private final SocialNetworkManager socialNetworkManager;

    @Inject
    public UserInfoServiceDefault(final SocialNetworkManager socialNetwork, final GroupManager groupManager) {
        this.socialNetworkManager = socialNetwork;
        this.groupManager = groupManager;
    }

    @Override
    public UserInfo buildInfo(final User user, final String userHash) throws DefaultException {
        UserInfo userInfo = null;
        if (User.isKnownUser(user)) {
            userInfo = new UserInfo();

            userInfo.setUser(user);
            userInfo.setChatName(user.getShortName());
            userInfo.setChatPassword(user.getPassword());
            userInfo.setUserHash(userHash);
            // FIXME: save this in user properties
            userInfo.setShowDeletedContent(false);

            final Group userGroup = user.getUserGroup();

            final ParticipationData participation = socialNetworkManager.findParticipation(user, userGroup);
            userInfo.setGroupsIsAdmin(participation.getGroupsIsAdmin());
            userInfo.setGroupsIsCollab(participation.getGroupsIsCollab());
            userInfo.setEnabledTools(groupManager.findEnabledTools(userGroup.getId()));
            final Content defaultContent = userGroup.getDefaultContent();
            userInfo.setUserGroup(userGroup);
            if (defaultContent != null) {
                userInfo.setHomePage(defaultContent.getStateToken().toString());
            }
        }
        return userInfo;
    }
}
