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
 \*/
package org.ourproject.kune.workspace.client.sitebar.sitesign;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.errors.UIException;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteSignOutLinkPresenter implements SiteSignOutLink {

    public class BeforeSignOut extends BeforeActionCollection {
    }
    private final BeforeActionCollection beforeSignOut;
    private final Session session;
    private final Provider<UserServiceAsync> userService;

    private SiteSignOutLinkView view;

    public SiteSignOutLinkPresenter(final Session session, final Provider<UserServiceAsync> userService,
            final Provider<ErrorHandler> errorHandler) {
        this.session = session;
        this.userService = userService;
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            @Override
            public void onEvent(final UserInfoDTO userInfoDTO) {
                view.setVisible(true);
            }
        });
        session.onUserSignOut(new Listener0() {
            @Override
            public void onEvent() {
                view.setVisible(false);
            }
        });
        // errorHandler.get().onSessionExpired(new Listener0() {
        // @Override
        // public void onEvent() {
        // clientUIsignOut();
        // }
        // });
        beforeSignOut = new BeforeActionCollection();
    }

    @Override
    public void addBeforeSignOut(final BeforeActionListener listener) {
        beforeSignOut.add(listener);
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

    public void doSignOut() {
        if (beforeSignOut.checkBeforeAction()) {
            userService.get().logout(session.getUserHash(), new AsyncCallback<Void>() {
                @Override
                public void onFailure(final Throwable caught) {
                    NotifyUser.hideProgress();
                    if (caught instanceof SessionExpiredException) {
                        clientUIsignOut();
                    } else if (caught instanceof UserMustBeLoggedException) {
                        clientUIsignOut();
                    } else {
                        throw new UIException("Other kind of exception in doLogout", caught);
                    }
                }

                @Override
                public void onSuccess(final Void arg0) {
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

    @Override
    public void removeBeforeSignOut(final BeforeActionListener listener) {
        beforeSignOut.remove(listener);
    }

}
