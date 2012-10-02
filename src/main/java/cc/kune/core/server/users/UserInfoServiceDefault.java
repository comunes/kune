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
package cc.kune.core.server.users;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ParticipationData;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserSignInLogFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

  private final GroupManager groupManager;
  private final SocialNetworkManager socialNetworkManager;
  private final UserSignInLogFinder userSignInLogFinder;

  @Inject
  public UserInfoServiceDefault(final SocialNetworkManager socialNetwork,
      final GroupManager groupManager, UserSignInLogFinder userSignInLogFinder) {
    this.socialNetworkManager = socialNetwork;
    this.groupManager = groupManager;
    this.userSignInLogFinder = userSignInLogFinder;
  }

  @Override
  public UserInfo buildInfo(final User user, final String userHash) throws DefaultException {
    UserInfo userInfo = null;
    if (User.isKnownUser(user)) {
      userInfo = new UserInfo();

      userInfo.setUser(user);
      userInfo.setChatName(user.getShortName());
      userInfo.setUserHash(userHash);
      // FIXME: save this in user properties
      userInfo.setShowDeletedContent(false);

      final Group userGroup = user.getUserGroup();

      userInfo.setGroupsIsParticipating(socialNetworkManager.findParticipationAggregated(user, userGroup));
      final ParticipationData participation = socialNetworkManager.findParticipation(user, userGroup);
      userInfo.setGroupsIsAdmin(participation.getGroupsIsAdmin());
      userInfo.setGroupsIsCollab(participation.getGroupsIsCollab());
      userInfo.setEnabledTools(groupManager.findEnabledTools(userGroup.getId()));
      userInfo.setSignInCount(userSignInLogFinder.countByUser(user));
      final Content defaultContent = userGroup.getDefaultContent();
      userInfo.setUserGroup(userGroup);
      if (defaultContent != null) {
        userInfo.setHomePage(defaultContent.getStateToken().toString());
      }
    }
    return userInfo;
  }
}
