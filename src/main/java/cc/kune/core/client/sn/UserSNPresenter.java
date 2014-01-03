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

// TODO: Auto-generated Javadoc
/**
 * The Class UserSNPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSNPresenter extends AbstractSNPresenter<UserSNView, UserSNProxy> {

  /**
   * The Interface UserSNProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface UserSNProxy extends Proxy<UserSNPresenter> {
  }

  /**
   * The Interface UserSNView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface UserSNView extends View {

    /**
     * Adds the buddie.
     * 
     * @param user
     *          the user
     * @param avatarUrl
     *          the avatar url
     * @param tooltip
     *          the tooltip
     * @param tooltipTitle
     *          the tooltip title
     * @param menu
     *          the menu
     * @param dragable
     *          the dragable
     */
    void addBuddie(UserSimpleDTO user, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    /**
     * Adds the participation.
     * 
     * @param group
     *          the group
     * @param avatarUrl
     *          the avatar url
     * @param tooltip
     *          the tooltip
     * @param tooltipTitle
     *          the tooltip title
     * @param menu
     *          the menu
     * @param dragable
     *          the dragable
     */
    void addParticipation(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    /**
     * Adds the text to buddie list.
     * 
     * @param text
     *          the text
     */
    void addTextToBuddieList(String text);

    /**
     * Clear.
     */
    void clear();

    /**
     * Gets the bottom toolbar.
     * 
     * @return the bottom toolbar
     */
    IsActionExtensible getBottomToolbar();

    /**
     * Sets the buddies count.
     * 
     * @param count
     *          the new buddies count
     */
    void setBuddiesCount(int count);

    /**
     * Sets the buddies visible.
     * 
     * @param visible
     *          the visible
     * @param areMany
     *          the are many
     */
    void setBuddiesVisible(boolean visible, boolean areMany);

    /**
     * Sets the no buddies.
     */
    void setNoBuddies();

    /**
     * Sets the participation count.
     * 
     * @param count
     *          the new participation count
     */
    void setParticipationCount(int count);

    /**
     * Sets the participation visible.
     * 
     * @param visible
     *          the visible
     * @param areMany
     *          the are many
     */
    void setParticipationVisible(boolean visible, boolean areMany);

    /**
     * Sets the visible.
     * 
     * @param visible
     *          the new visible
     */
    void setVisible(boolean visible);

    /**
     * Show buddies.
     */
    void showBuddies();

    /**
     * Show buddies not public.
     */
    void showBuddiesNotPublic();
  }

  /** The conf actions registry. */
  private final UserSNConfActions confActionsRegistry;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The user menu items registry. */
  private final UserSNMenuItemsRegistry userMenuItemsRegistry;

  /**
   * Instantiates a new user sn presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param downloadProvider
   *          the download provider
   * @param userMenuItemsRegistry
   *          the user menu items registry
   * @param confActionsRegistry
   *          the conf actions registry
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#getView()
   */
  @Override
  public UserSNView getView() {
    return (UserSNView) super.getView();
  }

  /**
   * On state changed.
   * 
   * @param state
   *          the state
   */
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

  /**
   * Refresh actions.
   */
  public void refreshActions() {
    refreshActionsImpl();
  }

  /**
   * Refresh actions impl.
   */
  private void refreshActionsImpl() {
    getView().getBottomToolbar().clear();
    getView().getBottomToolbar().addAll(confActionsRegistry);
  }

  /**
   * Refresh on sign in sign out.
   * 
   * @param session
   *          the session
   */
  private void refreshOnSignInSignOut(final Session session) {
    final StateAbstractDTO currentState = session.getCurrentState();
    if (currentState != null) {
      UserSNPresenter.this.onStateChanged(currentState);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /**
   * Sets the buddies state.
   * 
   * @param state
   *          the state
   * @return the int
   */
  private int setBuddiesState(final StateAbstractDTO state) {
    final UserBuddiesDataDTO userBuddiesData = state.getUserBuddies();
    final List<UserSimpleDTO> buddies = userBuddiesData.getBuddies();
    // setBuddiesState
    for (final UserSimpleDTO user : buddies) {
      final String avatarUrl = downloadProvider.get().getUserAvatar(user);
      getView().addBuddie(user, avatarUrl, user.getCompoundName(), "",
          createMenuItems(user, userMenuItemsRegistry, user.getCompoundName()),
          state.getGroupRights().isAdministrable());
    }
    final boolean hasLocalBuddies = buddies.size() > 0;
    final int numExtBuddies = userBuddiesData.getOtherExtBuddies();
    final int buddieNumber = buddies.size() + numExtBuddies;
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
    getView().setBuddiesCount(buddieNumber);
    return buddieNumber;
  }

  /**
   * Sets the participation state.
   * 
   * @param state
   *          the state
   * @return the int
   */
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
    getView().setParticipationCount(totalGroups);
    getView().setParticipationVisible(totalGroups > 0, areMany(totalGroups));
    return totalGroups;
  }
}
