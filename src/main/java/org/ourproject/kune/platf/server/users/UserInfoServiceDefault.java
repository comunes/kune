/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.ParticipationData;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    private final SocialNetworkManager socialNetworkManager;

    @Inject
    public UserInfoServiceDefault(final SocialNetworkManager socialNetwork) {
        this.socialNetworkManager = socialNetwork;
    }

    public UserInfo buildInfo(final User user, final String userHash) throws SerializableException {
        UserInfo info = null;
        if (User.isKnownUser(user)) {
            info = new UserInfo();

            info.setShortName(user.getShortName());
            info.setName(user.getName());
            info.setChatName(user.getShortName());
            info.setChatPassword(user.getPassword());

            info.setUserHash(userHash);

            Group userGroup = user.getUserGroup();

            ParticipationData participation = socialNetworkManager.findParticipation(user, userGroup);
            info.setGroupsIsAdmin(participation.getGroupsIsAdmin());
            info.setGroupsIsCollab(participation.getGroupsIsCollab());

            Content defaultContent = userGroup.getDefaultContent();
            if (defaultContent != null) {
                info.setHomePage(defaultContent.getStateToken());
            }
        }
        return info;
    }
}
