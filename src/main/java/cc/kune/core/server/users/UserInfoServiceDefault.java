/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.rpc.WaveClientServlet;

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
import com.google.inject.name.Named;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInfoServiceDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class UserInfoServiceDefault implements UserInfoService {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(UserInfoServiceDefault.class);

  /** The group manager. */
  private final GroupManager groupManager;

  /** The social network manager. */
  private final SocialNetworkManager socialNetworkManager;

  /** The user sign in log finder. */
  private final UserSignInLogFinder userSignInLogFinder;

  private final WaveClientServlet waveClientServlet;

  private final SessionManager waveSessionManager;

  private final String websocketAddress;

  /**
   * Instantiates a new user info service default.
   * 
   * @param socialNetwork
   *          the social network
   * @param groupManager
   *          the group manager
   * @param userSignInLogFinder
   *          the user sign in log finder
   */
  @Inject
  public UserInfoServiceDefault(final SocialNetworkManager socialNetwork,
      final SessionManager waveSessionManager, final WaveClientServlet waveClientServlet,
      final GroupManager groupManager, final UserSignInLogFinder userSignInLogFinder,
      @Named(CoreSettings.HTTP_WEBSOCKET_PUBLIC_ADDRESS) final String websocketAddress) {
    this.socialNetworkManager = socialNetwork;
    this.waveSessionManager = waveSessionManager;
    this.waveClientServlet = waveClientServlet;
    this.groupManager = groupManager;
    this.userSignInLogFinder = userSignInLogFinder;
    this.websocketAddress = websocketAddress;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.users.UserInfoService#buildInfo(cc.kune.domain.User,
   * java.lang.String)
   */
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
      setWaveSession(userHash, userInfo);
    }
    return userInfo;
  }

  private void setWaveSession(final String userHash, final UserInfo userInfo) {
    try {
      final HttpSession sessionFromToken = waveSessionManager.getSessionFromToken(userHash);
      final JSONObject clientFlags = new JSONObject();
      userInfo.setSessionJSON(waveClientServlet.getSessionJson(sessionFromToken).toString());
      userInfo.setClientFlags(clientFlags.toString());
      userInfo.setWebsocketAddress(websocketAddress);
      userInfo.setUserHash(userHash);
    } catch (final Exception e) {
      LOG.info("Cannot get wave session info for user with hash: " + userHash);
    }
  }
}
