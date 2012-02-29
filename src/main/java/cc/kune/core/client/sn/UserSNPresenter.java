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
package cc.kune.core.client.sn;

import java.util.List;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.SocialNetworkChangedEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sn.UserSNPresenter.UserSNProxy;
import cc.kune.core.client.sn.UserSNPresenter.UserSNView;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.ParticipationDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserBuddiesDataDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class UserSNPresenter extends AbstractSNPresenter<UserSNView, UserSNProxy> {

  @ProxyCodeSplit
  public interface UserSNProxy extends Proxy<UserSNPresenter> {
  }

  public interface UserSNView extends View {

    void addBuddie(UserSimpleDTO user, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    void addParticipation(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    void addTextToBuddieList(String text);

    void clear();

    IsActionExtensible getBottomToolbar();

    void setBuddiesCount(int count);

    void setBuddiesVisible(boolean visible, boolean areMany);

    void setNoBuddies();

    void setParticipationCount(int count);

    void setParticipationVisible(boolean visible, boolean areMany);

    void setVisible(boolean visible);

    void showBuddies();

    void showBuddiesNotPublic();
  }

  private final UserSNConfActions confActionsRegistry;
  private final I18nTranslationService i18n;
  private final UserSNMenuItemsRegistry userMenuItemsRegistry;

  @Inject
  public UserSNPresenter(final EventBus eventBus, final UserSNView view, final UserSNProxy proxy,
      final I18nTranslationService i18n, final StateManager stateManager, final Session session,
      final Provider<ClientFileDownloadUtils> downloadProvider,
      final UserSNMenuItemsRegistry userMenuItemsRegistry, final UserSNConfActions confActionsRegistry) {
    super(eventBus, view, proxy, downloadProvider);
    this.i18n = i18n;
    this.userMenuItemsRegistry = userMenuItemsRegistry;
    this.confActionsRegistry = confActionsRegistry;
    stateManager.onStateChanged(true, new StateChangedEvent.StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        UserSNPresenter.this.onStateChanged(event.getState());
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        refreshOnSignInSignOut(session);
      }
    });
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        refreshOnSignInSignOut(session);
      }
    });
    stateManager.onSocialNetworkChanged(true,
        new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {
          @Override
          public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
            UserSNPresenter.this.onStateChanged(event.getState());
          }
        });
    refreshActionsImpl();
  }

  @Override
  public UserSNView getView() {
    return (UserSNView) super.getView();
  }

  private void onStateChanged(final StateAbstractDTO state) {
    if (state.getGroup().isNotPersonal()) {
      getView().setVisible(false);
    } else {
      getView().clear();
      setParticipationState(state);
      if (state.getSocialNetworkData().isBuddiesVisible()) {
        // In fact now we show the user network or not.
        getView().setBuddiesVisible(true,
            areMany(state.getSocialNetworkData().getUserBuddies().getBuddies().size()));
        setBuddiesState(state);
        // getView().setVisible(buddies + participeIn > 0);
      } else {
        getView().showBuddiesNotPublic();
      }
      getView().setVisible(true);
    }
  }

  public void refreshActions() {
    refreshActionsImpl();
  }

  private void refreshActionsImpl() {
    getView().getBottomToolbar().clear();
    getView().getBottomToolbar().addAll(confActionsRegistry);
  }

  private void refreshOnSignInSignOut(final Session session) {
    final StateAbstractDTO currentState = session.getCurrentState();
    if (currentState != null) {
      UserSNPresenter.this.onStateChanged(currentState);
    }
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  private int setBuddiesState(final StateAbstractDTO state) {
    final UserBuddiesDataDTO userBuddiesData = state.getUserBuddies();
    final List<UserSimpleDTO> buddies = userBuddiesData.getBuddies();
    for (final UserSimpleDTO user : buddies) {
      final String avatarUrl = downloadProvider.get().getUserAvatar(user);
      getView().addBuddie(user, avatarUrl, user.getCompoundName(), "",
          createMenuItems(user, userMenuItemsRegistry, user.getCompoundName()),
          state.getGroupRights().isAdministrable());
    }
    final boolean hasLocalBuddies = buddies.size() > 0;
    final int numExtBuddies = userBuddiesData.getOtherExtBuddies();
    if (numExtBuddies > 0) {
      if (hasLocalBuddies) {
        // i18n: plural
        getView().addTextToBuddieList(
            i18n.t(numExtBuddies == 1 ? "and [%d] external user" : "and [%d] external users",
                numExtBuddies));
      } else {
        getView().addTextToBuddieList(
            i18n.t(numExtBuddies == 1 ? "[%d] external user" : "[%d] external users", numExtBuddies));
      }
    } else {
      if (hasLocalBuddies) {
        // getView().clearOtherUsers();
      } else {
        getView().setNoBuddies();
      }
    }
    return buddies.size() + numExtBuddies;
  }

  private int setParticipationState(final StateAbstractDTO state) {
    final ParticipationDataDTO participation = state.getParticipation();
    final List<GroupDTO> groupsIsAdmin = participation.getGroupsIsAdmin();
    final List<GroupDTO> groupsIsCollab = participation.getGroupsIsCollab();
    final int numAdmins = groupsIsAdmin.size();
    final int numCollaborators = groupsIsCollab.size();
    for (final GroupDTO group : groupsIsAdmin) {
      getView().addParticipation(group, getAvatar(group), group.getCompoundName(), "",
          createMenuItems(group, userMenuItemsRegistry, group.getCompoundName()),
          state.getGroupRights().isAdministrable());
    }
    for (final GroupDTO group : groupsIsCollab) {
      getView().addParticipation(group, getAvatar(group), group.getCompoundName(), "",
          createMenuItems(group, userMenuItemsRegistry, group.getCompoundName()),
          state.getGroupRights().isAdministrable());
    }
    final int totalGroups = numAdmins + numCollaborators;
    // getView().setParticipationCount(totalGroups);
    getView().setParticipationVisible(totalGroups > 0, areMany(totalGroups));
    return totalGroups;
  }
}