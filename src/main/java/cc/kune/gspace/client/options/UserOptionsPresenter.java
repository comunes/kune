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
package cc.kune.gspace.client.options;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class UserOptionsPresenter extends AbstractTabbedDialogPresenter implements UserOptions {

  private final I18nTranslationService i18n;
  private final CoreResources res;
  private final Session session;
  private final StateManager stateManager;
  private final SiteUserOptions userOptions;
  private UserOptionsView view;

  @Inject
  public UserOptionsPresenter(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final CoreResources res, final SiteUserOptions userOptions,
      final UserOptionsView view) {
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.res = res;
    this.userOptions = userOptions;
    session.onUserSignIn(false, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        view.hide();
      }
    });
    init(view);
  }

  private void createActions() {
    final AbstractExtendedAction userPrefsAction = new AbstractExtendedAction() {

      @Override
      public void actionPerformed(final ActionEvent event) {
        if (!session.isInCurrentUserSpace()) {
          stateManager.gotoStateToken(session.getCurrentUser().getStateToken());
        }
        show();
      }
    };
    userPrefsAction.putValue(Action.NAME, i18n.t("Your preferences"));
    userPrefsAction.putValue(Action.SMALL_ICON, res.prefs());
    final MenuItemDescriptor prefsItem = new MenuItemDescriptor(userPrefsAction);
    prefsItem.setPosition(1);
    userOptions.addAction(prefsItem);
  }

  private void init(final UserOptionsView view) {
    super.init(view);
    this.view = view;
    createActions();
  }
}
