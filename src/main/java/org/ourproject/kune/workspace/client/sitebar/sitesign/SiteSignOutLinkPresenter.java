/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.sitebar.sitesign;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.BeforeActionCollection;
import org.ourproject.kune.platf.client.actions.BeforeActionListener;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteSignOutLinkPresenter implements SiteSignOutLink {

    private SiteSignOutLinkView view;
    private final Session session;
    private final Provider<UserServiceAsync> userServiceProvider;
    private final BeforeActionCollection beforeSignOutCollection;

    public SiteSignOutLinkPresenter(final Session session, final Provider<UserServiceAsync> userServiceProvider,
            final Provider<KuneErrorHandler> kuneErrorHandlerProvider) {
        this.session = session;
        this.userServiceProvider = userServiceProvider;
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO userInfoDTO) {
                view.setVisible(true);
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                view.setVisible(false);
            }
        });
        kuneErrorHandlerProvider.get().onSessionExpired(new Listener0() {
            public void onEvent() {
                clientUIsignOut();
            }
        });
        beforeSignOutCollection = new BeforeActionCollection();
    }

    public void addBeforeSignOut(BeforeActionListener listener) {
        beforeSignOutCollection.add(listener);
    }

    public void doSignOut() {
        if (beforeSignOutCollection.checkBeforeAction()) {
            userServiceProvider.get().logout(session.getUserHash(), new AsyncCallback<Object>() {
                public void onFailure(final Throwable caught) {
                    NotifyUser.hideProgress();
                    try {
                        throw caught;
                    } catch (final SessionExpiredException e) {
                        clientUIsignOut();
                    } catch (final UserMustBeLoggedException e) {
                        clientUIsignOut();
                    } catch (final Throwable e) {
                        GWT.log("Other kind of exception in doLogout", null);
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final Object arg0) {
                    NotifyUser.hideProgress();
                    clientUIsignOut();
                }

            });
        } else {
            NotifyUser.hideProgress();
        }
    }

    public View getView() {
        return view;
    }

    public void init(final SiteSignOutLinkView view) {
        this.view = view;
        view.setVisible(false);
    }

    public void removeBeforeSignOut(BeforeActionListener listener) {
        beforeSignOutCollection.remove(listener);
    }

    private void clientUIsignOut() {
        // FIXME: Remove cookie doesn't works in all browsers, know
        // issue:
        // http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/ded86778ee56690/515dc513c7d085eb?lnk=st&q=remove+cookie#515dc513c7d085eb
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=1735&q=removeCookie
        Cookies.removeCookie(Session.USERHASH);
        // Workaround:
        Cookies.setCookie(Session.USERHASH, null, new Date(0), null, "/", false);
        session.setUserHash(null);
        session.setCurrentUserInfo(null);
    }

}
