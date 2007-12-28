/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.newgroup.NewGroupListener;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.rpc.UserService;
import org.ourproject.kune.sitebar.client.rpc.UserServiceAsync;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarPresenter implements SiteBar, LoginListener, NewGroupListener {

    private SiteBarView view;
    private final SiteBarListener listener;
    private boolean isLogged;
    private String previousToken;

    public SiteBarPresenter(final SiteBarListener listener) {
        this.listener = listener;
    }

    public void init(final SiteBarView view) {
        this.view = view;
        view.setLogoutLinkVisible(false);
    }

    public void doLogin(final String previousToken) {
        this.previousToken = previousToken;
        Site.showProgressProcessing();
        view.showLoginDialog();
        view.centerLoginDialog();
        Site.hideProgress();
    }

    public void doNewGroup(final String previousToken) {
        this.previousToken = previousToken;
        if (isLogged) {
            Site.showProgressProcessing();
            view.showNewGroupDialog();
            view.centerNewGroupDialog();
        } else {
            returnToPreviousState();
            Site.info(Kune.I18N.t("Sign in or register to create a group"));
        }
    }

    public void doSearch(final String termToSearch) {
        Site.showProgressProcessing();
        view.showSearchPanel(termToSearch);
    }

    public void doLogout() {
        Site.showProgressProcessing();

        AsyncCallback callback = new AsyncCallback() {
            public void onFailure(final Throwable caught) {
                Site.hideProgress();
                try {
                    throw caught;
                } catch (final UserMustBeLoggedException e) {
                    logout();
                } catch (final Throwable e) {
                    GWT.log("Other kind of exception in doLogout", null);
                    throw new RuntimeException();
                }
            }

            public void onSuccess(final Object arg0) {
                Site.hideProgress();
                logout();
            }

            private void logout() {
                isLogged = false;
                view.restoreLoginLink();
                view.resetOptionsSubmenu();
                view.setLogoutLinkVisible(false);
                listener.onUserLoggedOut();
            }
        };

        DefaultDispatcher.getInstance().fire(WorkspaceEvents.USER_LOGOUT, callback, null);
    }

    public void reloadUserInfo(final String userHash) {
        UserServiceAsync siteBarService = UserService.App.getInstance();
        siteBarService.reloadUserInfo(userHash, new AsyncCallback() {
            public void onFailure(final Throwable arg0) {
                Site.hideProgress();
            }

            public void onSuccess(final Object response) {
                showLoggedUser((UserInfoDTO) response);
                Site.hideProgress();
            }
        });
    }

    public void userLoggedIn(final UserInfoDTO userInfoDTO) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.USER_LOGGED_IN, userInfoDTO, null);
        view.hideLoginDialog();
        returnToPreviousState();
    }

    public void showLoggedUser(final UserInfoDTO user) {
        if (user == null) {
            view.restoreLoginLink();
            view.setLogoutLinkVisible(false);
        } else {
            isLogged = true;
            view.showLoggedUserName(user.getShortName(), user.getHomePage());
            view.setLogoutLinkVisible(true);
            view.setGroupsIsMember(user.getGroupsIsAdmin(), user.getGroupsIsCollab());
        }
    }

    public void onLoginCancelled() {
        view.hideLoginDialog();
        returnToPreviousState();
    }

    public void onLoginClose() {
        if (!isLogged) {
            returnToPreviousState();
        }
    }

    public void onNewGroupCreated(final StateToken homePage) {
        view.hideNewGroupDialog();
        changeState(homePage);
    }

    public void onNewGroupCancel() {
        view.hideNewGroupDialog();
        returnToPreviousState();
    }

    public void onNewGroupClose() {
        returnToPreviousState();
    }

    public void hideProgress() {
        view.hideProgress();
    }

    public void showProgress(final String text) {
        view.showProgress(text);
    }

    public void showAlertMessage(final String message) {
        view.showAlertMessage(message);
    }

    public View getView() {
        return view;
    }

    public void changeState(final StateToken token) {
        listener.onChangeState(token);
    }

    protected void onSearchLostFocus(final String search) {
        if (search.length() == 0) {
            view.setDefaultTextSearch();
            view.setTextSearchSmall();
        }
    }

    protected void onSearchFocus() {
        view.setTextSearchBig();
        view.clearSearchText();
    }

    private void returnToPreviousState() {
        if (this.previousToken != null) {
            listener.onChangeState(new StateToken(this.previousToken));
            this.previousToken = null;
        }
    }

    public void onHelpInTranslation() {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.SHOW_TRANSLATOR, null, null);
    }

}
