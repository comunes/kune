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
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.newgroup.NewGroupListener;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarPresenter implements SiteBar, LoginListener, NewGroupListener {

    private SiteBarView view;
    private final SiteBarListener listener;
    private boolean isLogged;

    public SiteBarPresenter(final SiteBarListener listener) {
	this.listener = listener;
    }

    public void init(final SiteBarView view) {
	this.view = view;
	view.setLogoutLinkVisible(false);
    }

    public void doLogin() {
	Site.showProgressProcessing();
	view.showLoginDialog();
	Site.hideProgress();
    }

    public void doNewGroup() {
	if (isLogged) {
	    Site.showProgressProcessing();
	    view.showNewGroupDialog();
	    Site.hideProgress();
	} else {
	    // i18n
	    Site.info("You must be logged to create a group");
	}
    }

    public void doSearch(final String string) {
	if (string == null) {
	    Window.alert("Type something to search!");
	} else {
	    Window.alert("In development!");
	}
    }

    public void doLogout() {
	Site.showProgressProcessing();
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	siteBarService.logout(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		Site.hideProgress();
	    }

	    public void onSuccess(final Object arg0) {
		Site.hideProgress();
		isLogged = false;
		view.restoreLoginLink();
		view.resetOptionsSubmenu();
		view.setLogoutLinkVisible(false);
		listener.onUserLoggedOut();
	    }
	});
    }

    public void reloadUserInfo(final String userHash) {
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
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

    public void userLoggedIn(final UserInfoDTO user) {
	view.hideLoginDialog();
	listener.onUserLoggedIn(user);
    }

    public void showLoggedUser(final UserInfoDTO user) {
	if (user == null) {
	    view.restoreLoginLink();
	    view.setLogoutLinkVisible(false);
	} else {
	    isLogged = true;
	    view.showLoggedUserName(user.getName(), user.getHomePage());
	    view.setLogoutLinkVisible(true);
	    view.setGroupsIsMember(user.getGroupsIsAdmin(), user.getGroupsIsEditor());
	}
    }

    public void onLoginCancelled() {
	view.hideLoginDialog();
    }

    public void onNewGroupCreated(final StateToken homePage) {
	view.hideNewGroupDialog();
	changeState(homePage);
    }

    public void onNewGroupCancel() {
	view.hideNewGroupDialog();
    }

    public void hideProgress() {
	view.hideProgress();
    }

    public void showProgress(final String text) {
	view.showProgress(text);
    }

    public View getView() {
	return view;
    }

    protected void onSearchLostFocus(final String search) {
	if (search.length() == 0) {
	    view.setDefaultTextSearch();
	}
    }

    protected void onSearchFocus() {
	view.clearSearchText();
    }

    public void changeState(final StateToken token) {
	listener.onChangeState(token);
    }

}
