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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.gspace.client.options.UserOptionsPresenter.UserOptionsView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserOptionsPresenter extends
    AbstractTabbedDialogPresenter<UserOptionsView, UserOptionsPresenter.UserOptionsProxy> implements
    UserOptions {

  @ProxyCodeSplit
  public interface UserOptionsProxy extends Proxy<UserOptionsPresenter> {
  }

  public interface UserOptionsView extends EntityOptionsView {
  }

  private final I18nTranslationService i18n;
  private final IconicResources res;
  private final Session session;
  private final StateManager stateManager;
  private final SiteUserOptions userOptions;

  @Inject
  public UserOptionsPresenter(final EventBus eventBus, final UserOptionsProxy proxy,
      final Session session, final StateManager stateManager, final I18nTranslationService i18n,
      final IconicResources res, final SiteUserOptions userOptions, final UserOptionsView view) {
    super(eventBus, view, proxy);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.res = res;
    this.userOptions = userOptions;
    session.onUserSignIn(false, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        getView().hide();
      }
    });
  }

  @Override
  public UserOptionsView getView() {
    return (UserOptionsView) super.getView();
  }

  @Override
  protected void onBind() {
    final AbstractExtendedAction userPrefsAction = new AbstractExtendedAction() {

      @Override
      public void actionPerformed(final ActionEvent event) {
        show();
      }
    };
    session.onUserSignOut(false, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().hide();
      }
    });
    userPrefsAction.putValue(Action.NAME, i18n.t("Your preferences"));
    userPrefsAction.putValue(Action.SMALL_ICON, res.prefs());
    final MenuItemDescriptor prefsItem = new MenuItemDescriptor(userPrefsAction);
    prefsItem.setPosition(1);
    userOptions.addAction(prefsItem);
  }

  @Override
  public void show() {
    if (!session.isInCurrentUserSpace()) {
      stateManager.gotoStateToken(session.getCurrentUser().getStateToken());
    }
    super.show();
  }
}
