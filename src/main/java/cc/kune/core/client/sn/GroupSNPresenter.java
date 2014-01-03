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

import java.util.Set;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.events.SocialNetworkChangedEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.GroupSNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.GroupSNPendingsMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupSNPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupSNPresenter extends
    AbstractSNPresenter<GroupSNPresenter.GroupSNView, GroupSNPresenter.GroupSNProxy> {

  /**
   * The Interface GroupSNProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface GroupSNProxy extends Proxy<GroupSNPresenter> {
  }

  /**
   * The Interface GroupSNView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface GroupSNView extends View {

    /**
     * Adds the admin.
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
    void addAdmin(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    /**
     * Adds the collab.
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
    void addCollab(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

    /**
     * Adds the pending.
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
    void addPending(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
        GuiActionDescCollection menu, boolean dragable);

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
     * Sets the admins count.
     * 
     * @param count
     *          the new admins count
     */
    void setAdminsCount(int count);

    /**
     * Sets the admins visible.
     * 
     * @param visible
     *          the visible
     * @param big
     *          the big
     */
    void setAdminsVisible(boolean visible, boolean big);

    /**
     * Sets the collabs count.
     * 
     * @param count
     *          the new collabs count
     */
    void setCollabsCount(int count);

    /**
     * Sets the collabs visible.
     * 
     * @param visible
     *          the visible
     * @param big
     *          the big
     */
    void setCollabsVisible(boolean visible, boolean big);

    /**
     * Sets the pendings count.
     * 
     * @param count
     *          the new pendings count
     */
    void setPendingsCount(int count);

    /**
     * Sets the pending visible.
     * 
     * @param visible
     *          the visible
     * @param big
     *          the big
     */
    void setPendingVisible(boolean visible, boolean big);

    /**
     * Sets the visible.
     * 
     * @param visible
     *          the new visible
     */
    void setVisible(boolean visible);

    /**
     * Show member not public.
     */
    void showMemberNotPublic();

    /**
     * Show members.
     */
    void showMembers();

    /**
     * Show orphan.
     */
    void showOrphan();
  }

  /** The actions registry. */
  private final GroupSNConfActions actionsRegistry;

  /** The admins menu items registry. */
  private final GroupSNAdminsMenuItemsRegistry adminsMenuItemsRegistry;

  /** The collabs menu items registry. */
  private final GroupSNCollabsMenuItemsRegistry collabsMenuItemsRegistry;

  /** The pendings menu items registry. */
  private final GroupSNPendingsMenuItemsRegistry pendingsMenuItemsRegistry;

  /**
   * Instantiates a new group sn presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param downloadProvider
   *          the download provider
   * @param adminsMenuItemsRegistry
   *          the admins menu items registry
   * @param collabsMenuItemsRegistry
   *          the collabs menu items registry
   * @param pendingsMenuItemsRegistry
   *          the pendings menu items registry
   * @param actionsRegistry
   *          the actions registry
   */
  @Inject
  public GroupSNPresenter(final EventBus eventBus, final GroupSNView view, final GroupSNProxy proxy,
      final StateManager stateManager, final Session session,
      final Provider<ClientFileDownloadUtils> downloadProvider,
      final GroupSNAdminsMenuItemsRegistry adminsMenuItemsRegistry,
      final GroupSNCollabsMenuItemsRegistry collabsMenuItemsRegistry,
      final GroupSNPendingsMenuItemsRegistry pendingsMenuItemsRegistry,
      final GroupSNConfActions actionsRegistry) {
    super(eventBus, view, proxy, downloadProvider);
    this.adminsMenuItemsRegistry = adminsMenuItemsRegistry;
    this.collabsMenuItemsRegistry = collabsMenuItemsRegistry;
    this.pendingsMenuItemsRegistry = pendingsMenuItemsRegistry;
    this.actionsRegistry = actionsRegistry;
    stateManager.onStateChanged(true, new StateChangedEvent.StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        GroupSNPresenter.this.onStateChanged(event.getState());
      }
    });
    stateManager.onSocialNetworkChanged(true,
        new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {
          @Override
          public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
            GroupSNPresenter.this.onStateChanged(event.getState());
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
    refreshActionsImpl();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#getView()
   */
  @Override
  public GroupSNView getView() {
    return (GroupSNView) super.getView();
  }

  /**
   * On state changed.
   * 
   * @param state
   *          the state
   */
  private void onStateChanged(final StateAbstractDTO state) {
    if (state.getGroup().isPersonal()) {
      getView().setVisible(false);
    } else {
      if (state.getSocialNetworkData().isMembersVisible()) {
        getView().clear();
        setGroupMembers(state.getGroupMembers(), state.getGroupRights());
      } else {
        getView().clear();
        getView().showMemberNotPublic();
        getView().setVisible(true);
      }
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
    getView().getBottomToolbar().addAll(actionsRegistry);
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
      GroupSNPresenter.this.onStateChanged(currentState);
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
   * Sets the group members.
   * 
   * @param socialNetwork
   *          the social network
   * @param rights
   *          the rights
   */
  private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRights rights) {
    final AccessListsDTO accessLists = socialNetwork.getAccessLists();

    final Set<GroupDTO> adminsList = accessLists.getAdmins().getList();
    final Set<GroupDTO> collabList = accessLists.getEditors().getList();
    final Set<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

    final int numAdmins = adminsList.size();
    final int numCollabs = collabList.size();
    final int numPendings = pendingCollabsList.size();

    getView().setAdminsCount(numAdmins);
    getView().setCollabsCount(numCollabs);
    getView().setPendingsCount(numPendings);

    if ((numAdmins + numCollabs) == 0) {
      getView().showOrphan();
    } else {
      final boolean userIsAdmin = rights.isAdministrable();
      final boolean userIsEditor = rights.isEditable();
      final boolean userCanView = rights.isVisible();
      getView().setAdminsVisible(true, areMany(numAdmins));
      if (userCanView) {
        for (final GroupDTO admin : adminsList) {
          final String avatar = getAvatar(admin);
          getView().addAdmin(admin, avatar, admin.getCompoundName(), "",
              createMenuItems(admin, adminsMenuItemsRegistry, admin.getCompoundName()), userIsEditor);
        }
        getView().setCollabsVisible(numCollabs > 0, areMany(numCollabs));
        for (final GroupDTO collab : collabList) {
          final String avatar = getAvatar(collab);
          getView().addCollab(collab, avatar, collab.getCompoundName(), "",
              createMenuItems(collab, collabsMenuItemsRegistry, collab.getCompoundName()), userIsEditor);
        }
        if (userIsAdmin) {
          getView().setPendingVisible(numPendings > 0, areMany(numPendings));
          for (final GroupDTO pendingCollab : pendingCollabsList) {
            final String avatar = getAvatar(pendingCollab);
            getView().addPending(
                pendingCollab,
                avatar,
                pendingCollab.getCompoundName(),
                "",
                createMenuItems(pendingCollab, pendingsMenuItemsRegistry,
                    pendingCollab.getCompoundName()), userIsEditor);
          }
        } else {
          getView().setPendingVisible(false, false);
        }
        getView().showMembers();
      }
    }
    getView().setVisible(true);
  }
}
