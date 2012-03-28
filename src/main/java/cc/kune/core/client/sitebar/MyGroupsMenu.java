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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuShowAction;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.i18n.I18n;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.SitebarGroupsLink.SitebarNewGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class MyGroupsMenu extends MenuDescriptor {
  public static final String MENU_ID = "k-sitebar-my-group";
  private final Provider<ClientFileDownloadUtils> downloadProvider;
  private final GotoGroupAction gotoGroupAction;
  private final SitebarNewGroupAction newGroupAction;
  private final Session session;
  private final SitebarActions siteOptions;

  @Inject
  public MyGroupsMenu(final Provider<ClientFileDownloadUtils> downloadProvider, final CoreResources res,
      final Session session, final GotoGroupAction gotoGroupAction,
      final SitebarNewGroupAction newGroupAction, final SitebarActions siteOptions,
      final GlobalShortcutRegister global, final MenuShowAction menuShowAction, final EventBus eventBus) {
    super(menuShowAction);
    this.session = session;
    this.newGroupAction = newGroupAction;
    this.siteOptions = siteOptions;
    menuShowAction.setMenu(this);
    setId(MENU_ID);
    setParent(siteOptions.getRightToolbar());
    setStyles("k-no-backimage, k-btn-sitebar");
    this.downloadProvider = downloadProvider;
    this.gotoGroupAction = gotoGroupAction;
    withText(I18n.t("Your groups"));
    withToolTip(I18n.t("See your groups or create a new one"));
    withIcon(res.arrowdownsitebar());
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

  private void addPartipationToMenu(final GroupDTO group) {
    final MenuItemDescriptor participant = new MenuItemDescriptor(gotoGroupAction);
    participant.setTarget(group);
    participant.withText(TextUtils.ellipsis(group.getLongName(), 26)).withIcon(
        new Url(downloadProvider.get().getGroupLogo(group))).setParent(this, true);
  }

  private void regenerateMenu(final boolean isLogged) {
    if (isLogged) {
      if (session.userIsJoiningGroups()) {
        MyGroupsMenu.this.clear();
        setVisible(true);
        final UserInfoDTO userInfoDTO = session.getCurrentUserInfo();
        for (final GroupDTO group : userInfoDTO.getGroupsIsAdmin()) {
          addPartipationToMenu(group);
        }
        for (final GroupDTO group : userInfoDTO.getGroupsIsCollab()) {
          addPartipationToMenu(group);
        }
        new MenuSeparatorDescriptor(MyGroupsMenu.this);
        new MenuItemDescriptor(MyGroupsMenu.this, newGroupAction);
        siteOptions.refreshActions();
      } else {
        setVisible(false);
      }
    } else {
      setVisible(false);
    }
  }
}
