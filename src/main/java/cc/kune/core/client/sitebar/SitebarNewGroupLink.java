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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class SitebarNewGroupLink extends ButtonDescriptor {
  public static class SitebarNewGroupAction extends AbstractExtendedAction {

    private final StateManager stateManager;

    @Inject
    public SitebarNewGroupAction(final StateManager stateManager, final I18nTranslationService i18n) {
      super();
      this.stateManager = stateManager;
      withText(i18n.t(CoreMessages.NEW_GROUP_TITLE));
      withToolTip(i18n.t("Create a new group for your initiative or organization "
          + "(NGO, collective, academic group...)"));
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryToken(SiteTokens.NEW_GROUP);
    }
  }

  public static final String NEW_GROUP_BTN_ID = "k-site-newgroup-btn";
  private final Session session;

  @Inject
  public SitebarNewGroupLink(final SitebarNewGroupAction newGroupAction,
      final SitebarActions sitebarActions, final I18nTranslationService i18n, final EventBus eventBus,
      final CoreResources coreResources, final Session session) {
    super(newGroupAction);
    this.session = session;
    withId(NEW_GROUP_BTN_ID).withStyles("k-no-backimage, k-btn-sitebar, k-fl, k-noborder, k-nobackcolor");
    withParent(sitebarActions.getRightToolbar());
    eventBus.addHandler(MyGroupsChangedEvent.getType(),
        new MyGroupsChangedEvent.MyGroupsChangedHandler() {
          @Override
          public void onMyGroupsChanged(final MyGroupsChangedEvent event) {
            recalculate(session.isNotLogged());
          }
        });
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean notLogged = !event.isLogged();
        recalculate(notLogged);
      }
    });

  }

  private void recalculate(final boolean notLogged) {
    if (notLogged) {
      setVisible(true);
    } else {
      setVisible(!session.userIsJoiningGroups());
    }
  }

}
