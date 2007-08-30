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
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.group.NewGroupListener;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarPresenter implements SiteBar, LoginListener, NewGroupListener {

    private SiteBarView view;
    private final SiteBarListener listener;

    public SiteBarPresenter(final SiteBarListener listener) {
	this.listener = listener;
    }

    public void init(final SiteBarView view) {
	this.view = view;
	view.setLogoutLinkVisible(false);
    }

    public void doLogin() {
	view.showLoginDialog();
    }

    public void doNewGroup() {
	view.showNewGroupDialog();
    }

    public void doSearch(final String string) {
	if (string == null) {
	    Window.alert("Type something to search!");
	} else {
	    Window.alert("In development!");
	}
    }

    public void doLogout() {
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	siteBarService.logout(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
	    }

	    public void onSuccess(final Object arg0) {
		view.setLogoutLinkVisible(false);
		view.restoreLoginLink();
		listener.onUserLoggedOut();
	    }
	});
    }

    public void userLoggedIn(final UserDTO user) {
	view.hideLoginDialog();
	listener.onUserLoggedIn(user);
    }

    public void showLoggedUser(final String userName) {
	if (userName == null) {
	    view.restoreLoginLink();
	    view.setLogoutLinkVisible(false);
	} else {
	    view.showLoggedUserName(userName);
	    view.setLogoutLinkVisible(true);
	}
    }

    public void onLoginCancelled() {
	view.hideLoginDialog();
    }

    public void onNewGroupCreated() {
	view.hideNewGroupDialog();
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

}
