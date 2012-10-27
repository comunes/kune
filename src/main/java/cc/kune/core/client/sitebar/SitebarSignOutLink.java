/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.WaveClientSimpleAuthenticator;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SitebarSignOutLink extends ButtonDescriptor {

  public static class BeforeSignOut extends BeforeActionCollection {
    private static final long serialVersionUID = 2326033703822323868L;
  }

  public static class SitebarSignOutAction extends AbstractExtendedAction {

    private final BeforeSignOut beforeSignOut;
    private final EventBus eventBus;
    private final Session session;
    private final Provider<UserServiceAsync> userService;
    private final WaveClientSimpleAuthenticator waveAuth;

    @Inject
    public SitebarSignOutAction(final EventBus eventBus, final I18nTranslationService i18n,
        final BeforeSignOut beforeSignOut, final Provider<UserServiceAsync> userService,
        final Session session, final WaveClientSimpleAuthenticator waveAuth) {
      super();
      this.eventBus = eventBus;
      this.userService = userService;
      this.session = session;
      this.beforeSignOut = beforeSignOut;
      this.waveAuth = waveAuth;
      putValue(Action.NAME, i18n.t("Sign out"));
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      eventBus.fireEvent(new ProgressShowEvent());
      if (beforeSignOut.checkBeforeAction()) {
        waveAuth.doLogout(new AsyncCallback<Void>() {
          @Override
          public void onFailure(final Throwable caught) {
            onLogoutFail(caught);
          }

          @Override
          public void onSuccess(final Void result) {
            userService.get().logout(session.getUserHash(), new AsyncCallback<Void>() {
              @Override
              public void onFailure(final Throwable caught) {
                onLogoutFail(caught);
              }

              @Override
              public void onSuccess(final Void arg0) {
                eventBus.fireEvent(new ProgressHideEvent());
                session.signOut();
              }

            });
          }
        });
      } else {
        eventBus.fireEvent(new ProgressHideEvent());
      }
    }

    private void onLogoutFail(final Throwable caught) {
      eventBus.fireEvent(new ProgressHideEvent());
      if (caught instanceof SessionExpiredException) {
        session.signOut();
      } else if (caught instanceof UserMustBeLoggedException) {
        session.signOut();
      } else {
        throw new UIException("Other kind of exception in doLogout", caught);
      }
    }

  }
  public static final String SITE_SIGN_OUT = "k-ssolp-lb";

  @Inject
  public SitebarSignOutLink(final SitebarSignOutAction action, final EventBus eventBus,
      final ErrorHandler errorHandler, final Session session, final SitebarActions sitebarActions) {
    super(action);
    setId(SITE_SIGN_OUT);
    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        SitebarActions.RIGHT_TOOLBAR);
    setParent(SitebarActions.RIGHT_TOOLBAR);
    setVisible(session.isLogged());
    setStyles(SitebarActions.COMMON_LINK_STYLE);
    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        final boolean logged = event.isLogged();
        SitebarSignOutLink.this.setVisible(logged);
        separator.setVisible(logged);
      }
    });
  }
}
