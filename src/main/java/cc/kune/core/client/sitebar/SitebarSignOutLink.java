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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.errors.UIException;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.errors.SessionExpiredEvent;
import cc.kune.core.client.errors.SessionExpiredEvent.SessionExpiredHandler;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.notify.spiner.ProgressShowEvent;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.wave.client.WaveClientSimpleAuthenticator;

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
        private final CookiesManager cookiesManager;
        private final EventBus eventBus;
        private final Session session;
        private final Provider<UserServiceAsync> userService;
        private final WaveClientSimpleAuthenticator waveAuth;

        @Inject
        public SitebarSignOutAction(final EventBus eventBus, final I18nTranslationService i18n,
                final BeforeSignOut beforeSignOut, final Provider<UserServiceAsync> userService, final Session session,
                final CookiesManager cookiesManager, final WaveClientSimpleAuthenticator waveAuth) {
            super();
            this.eventBus = eventBus;
            this.userService = userService;
            this.session = session;
            this.cookiesManager = cookiesManager;
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
                                clientUIsignOut();
                            }

                        });
                    }
                });
            } else {
                eventBus.fireEvent(new ProgressHideEvent());
            }
        }

        public void clientUIsignOut() {
            cookiesManager.removeCookie();
            session.setUserHash(null);
            session.setCurrentUserInfo(null);
        }

        private void onLogoutFail(final Throwable caught) {
            eventBus.fireEvent(new ProgressHideEvent());
            if (caught instanceof SessionExpiredException) {
                clientUIsignOut();
            } else if (caught instanceof UserMustBeLoggedException) {
                clientUIsignOut();
            } else {
                throw new UIException("Other kind of exception in doLogout", caught);
            }
        }

    }
    public static final String SITE_SIGN_OUT = "k-ssolp-lb";

    @Inject
    public SitebarSignOutLink(final SitebarSignOutAction action, final EventBus eventBus,
            final ErrorHandler errorHandler, final Session session) {
        super(action);
        setId(SITE_SIGN_OUT);
        setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
        setVisible(session.isLogged());
        setStyles("k-no-backimage, k-btn-sitebar, k-fl");
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                SitebarSignOutLink.this.setVisible(true);
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                SitebarSignOutLink.this.setVisible(false);
            }
        });
        eventBus.addHandler(SessionExpiredEvent.getType(), new SessionExpiredHandler() {
            @Override
            public void onSessionExpired(final SessionExpiredEvent event) {
                action.clientUIsignOut();
            }
        });
    }
}
