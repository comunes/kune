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
package cc.kune.core.client;

import cc.kune.core.client.auth.AnonUsersManager;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sitebar.AboutKuneDialog;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.state.HistoryTokenCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.sub.SubtitlesManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class CoreParts {

  @Inject
  public CoreParts(final Session session, final Provider<GroupSNPresenter> groupMembersPresenter,
      final Provider<UserSNPresenter> buddiesAndParticipationPresenter,
      final Provider<GroupSNConfActions> groupMembersConfActions,
      final Provider<UserSNConfActions> userSNConfActions, final Provider<AnonUsersManager> anonUsers,
      final Provider<SiteUserOptionsPresenter> userOptions,
      final Provider<SpaceSelectorPresenter> spaceSelector, final SiteTokenListeners tokenListener,
      final Provider<SignIn> signIn, final Provider<Register> register,
      final Provider<AboutKuneDialog> aboutKuneDialog, final Provider<NewGroup> newGroup,
      final Provider<SubtitlesManager> subProvider, final EventBus eventBus) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        groupMembersConfActions.get();
        userSNConfActions.get();
        groupMembersPresenter.get();
        buddiesAndParticipationPresenter.get();
        userOptions.get();
        anonUsers.get();
        spaceSelector.get();
      }
    });
    tokenListener.put(SiteTokens.SIGNIN, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        signIn.get().showSignInDialog();
      }
    });
    tokenListener.put(SiteTokens.ABOUTKUNE, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        // FIXME, something to come back
        aboutKuneDialog.get().showCentered();
      }
    });
    tokenListener.put(SiteTokens.REGISTER, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        register.get().doRegister();
      }
    });
    tokenListener.put(SiteTokens.NEWGROUP, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        newGroup.get().doNewGroup();
      }
    });
    tokenListener.put(SiteTokens.SUBTITLES, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        subProvider.get().show(token);
      }
    });
    tokenListener.put(SiteTokens.HOME, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        SpaceSelectEvent.fire(eventBus, Space.homeSpace);
      }
    });
    tokenListener.put(SiteTokens.WAVEINBOX, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        SpaceSelectEvent.fire(eventBus, Space.userSpace);
      }
    });
  }
}
