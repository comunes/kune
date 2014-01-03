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

package cc.kune.chat.client;

import java.util.Date;

import org.waveprotocol.wave.client.common.util.DateUtils;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.UserBuddiesPresenceDataDTO;

import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class LastConnectedManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class LastConnectedManagerImpl implements LastConnectedManager {

  /** The Constant RECENT_MS. */
  private static final int RECENT_MS = 60 * 1000; /* 1 minute */

  /** The data. */
  private UserBuddiesPresenceDataDTO data;

  /**
   * Instantiates a new last connected manager.
   * 
   * @param session
   *          the session
   * @param roster
   *          the roster
   * @param userService
   *          the user service
   */
  @Inject
  public LastConnectedManagerImpl(final Session session, final XmppRoster roster,
      final Provider<UserServiceAsync> userService) {
    roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
      @Override
      public void onRosterItemChanged(final RosterItemChangedEvent event) {
        update(event.getRosterItem().getJID().getShortName(), new Date().getTime());
      }
    });
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        if (event.isLogged()) {
          userService.get().getBuddiesPresence(session.getUserHash(),
              new AsyncCallback<UserBuddiesPresenceDataDTO>() {
                @Override
                public void onFailure(final Throwable caught) {
                  clear();
                }

                @Override
                public void onSuccess(final UserBuddiesPresenceDataDTO result) {
                  data = result;
                }
              });
        } else {
          clear();
        }
      }
    });
  }

  /**
   * Clear.
   */
  private void clear() {
    data = UserBuddiesPresenceDataDTO.NO_BUDDIES;
  }

  /**
   * Gets the last connected info.
   * 
   * @param username
   *          the username to get the last connected info
   * @param standalone
   *          if we should show a message alone, or if this is part of a phrase
   * @return the last connected info
   */
  @Override
  public String get(final String username, final boolean standalone) {
    final Long time = data.get(username);
    if (username == null || time == null || time == 0) {
      return "";
    } else {
      if (isRecent(time)) {
        return I18n.t(standalone ? "Online" : " : online");
      } else {
        return I18n.t(standalone ? "Last seen at [%s]" : ": last seen at [%s]",
            DateUtils.getInstance().formatPastDate(time));
      }
    }
  }

  /**
   * Checks if is recent.
   * 
   * @param time
   *          the time
   * @return true if a duration is less than 1 min.
   */
  private boolean isRecent(final Long time) {
    return (new Date().getTime() - time) < RECENT_MS;
  }

  /**
   * Update.
   * 
   * @param username
   *          the username
   * @param lastConnected
   *          the last connected
   */
  public void update(final String username, final Long lastConnected) {
    data.put(username, lastConnected);
  }
}
