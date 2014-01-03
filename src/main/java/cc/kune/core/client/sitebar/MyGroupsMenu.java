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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuShowAction;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.SitebarNewGroupLink.SitebarNewGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupLastVisitedContentAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class MyGroupsMenu.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class MyGroupsMenu extends MenuDescriptor {

  /** The Constant MENU_ID. */
  public static final String MENU_ID = "k-sitebar-my-group";

  /** The Constant NEW_GROUP_MENUITEM_ID. */
  public static final String NEW_GROUP_MENUITEM_ID = "k-sitebar-my-group-newmenuitem";

  /** The download provider. */
  private final Provider<ClientFileDownloadUtils> downloadProvider;

  /** The goto group provider. */
  private final Provider<GotoGroupLastVisitedContentAction> gotoGroupProvider;

  /** The new group action. */
  private final SitebarNewGroupAction newGroupAction;

  /** The session. */
  private final Session session;

  /** The sitebar new group link. */
  private final SitebarNewGroupLink sitebarNewGroupLink;

  /** The site options. */
  private final SitebarActions siteOptions;

  /** The user service. */
  private final UserServiceAsync userService;

  /**
   * Instantiates a new my groups menu.
   * 
   * @param downloadProvider
   *          the download provider
   * @param res
   *          the res
   * @param session
   *          the session
   * @param gotoGroupProvider
   *          the goto group provider
   * @param newGroupAction
   *          the new group action
   * @param siteOptions
   *          the site options
   * @param global
   *          the global
   * @param menuShowAction
   *          the menu show action
   * @param eventBus
   *          the event bus
   * @param userService
   *          the user service
   * @param sitebarNewGroupLink
   *          the sitebar new group link
   */
  @Inject
  public MyGroupsMenu(final Provider<ClientFileDownloadUtils> downloadProvider, final CoreResources res,
      final Session session, final Provider<GotoGroupLastVisitedContentAction> gotoGroupProvider,
      final SitebarNewGroupAction newGroupAction, final SitebarActions siteOptions,
      final GlobalShortcutRegister global, final MenuShowAction menuShowAction, final EventBus eventBus,
      final UserServiceAsync userService, final SitebarNewGroupLink sitebarNewGroupLink) {
    super(menuShowAction);
    this.session = session;
    this.gotoGroupProvider = gotoGroupProvider;
    this.newGroupAction = newGroupAction;
    this.siteOptions = siteOptions;
    this.userService = userService;
    this.sitebarNewGroupLink = sitebarNewGroupLink;
    this.downloadProvider = downloadProvider;
    menuShowAction.setMenu(this);
    setId(MENU_ID);
    setParent(SitebarActions.LEFT_TOOLBAR);
    setPosition(0);
    setStyles(ActionStyles.SITEBAR_STYLE);
    withText(I18n.t("Your groups"));
    withToolTip(I18n.t("See your groups or create a new one"));
    withIcon(res.arrowdownsitebarSmall());
    withShortcut("Alt+M", global);
    eventBus.addHandler(MyGroupsChangedEvent.getType(),
        new MyGroupsChangedEvent.MyGroupsChangedHandler() {
          @Override
          public void onMyGroupsChanged(final MyGroupsChangedEvent event) {
            regenerateMenu(session.isLogged());
          }
        });

    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        regenerateMenu(event.isLogged());
      }
    });

  }

  /**
   * Adds the partipation to menu.
   * 
   * @param group
   *          the group
   */
  private void addPartipationToMenu(final GroupDTO group) {
    final GotoGroupLastVisitedContentAction action = gotoGroupProvider.get();
    action.setGroup(group);
    final MenuItemDescriptor participant = new MenuItemDescriptor(action);
    participant.withText(TextUtils.ellipsis(group.getLongName(), 26)).withIcon(
        new Url(downloadProvider.get().getGroupLogo(group))).setParent(this, true);
  }

  /**
   * Regenerate menu.
   * 
   * @param isLogged
   *          the is logged
   */
  private void regenerateMenu(final boolean isLogged) {
    if (isLogged) {
      // We request again the data about this user
      userService.reloadUserInfo(session.getUserHash(), new AsyncCallbackSimple<UserInfoDTO>() {
        @Override
        public void onSuccess(final UserInfoDTO userInfo) {
          session.refreshCurrentUserInfo(userInfo);
          sitebarNewGroupLink.recalculate(!isLogged);
          if (session.userIsJoiningGroups()) {
            MyGroupsMenu.this.clear();
            setVisible(true);
            final UserInfoDTO userInfoDTO = session.getCurrentUserInfo();
            for (final GroupDTO group : userInfoDTO.getGroupsIsParticipating()) {
              addPartipationToMenu(group);
            }
            new MenuSeparatorDescriptor(MyGroupsMenu.this);
            new MenuItemDescriptor(MyGroupsMenu.this, newGroupAction).withId(NEW_GROUP_MENUITEM_ID);
            siteOptions.refreshActions();
          } else {
            setVisible(false);
          }
        }
      });
    } else {
      sitebarNewGroupLink.recalculate(!isLogged);
      setVisible(false);
    }
  }
}
