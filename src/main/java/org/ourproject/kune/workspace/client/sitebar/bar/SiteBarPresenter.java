/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.sitebar.bar;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class SiteBarPresenter implements SiteBar {

    private SiteBarView view;
    private final SiteBarListener listener;
    private String previousToken;
    private final Session session;

    public SiteBarPresenter(final SiteBarListener listener, final Session session, final I18nTranslationService i18n) {
	this.listener = listener;
	this.session = session;
    }

    public void changeState(final StateToken token) {
	listener.onChangeState(token);
    }

    public void doLogin(final String previousToken) {
	this.previousToken = previousToken;
	Site.showProgressProcessing();
	view.showLoginDialog();
	view.centerLoginDialog();
	Site.hideProgress();
    }

    public void doLogout() {
	// final AsyncCallback<Object> callback = new AsyncCallback<Object>() {
	// public void onFailure(final Throwable caught) {
	// Site.hideProgress();
	// try {
	// throw caught;
	// } catch (final SessionExpiredException e) {
	// clientUILogout();
	// } catch (final UserMustBeLoggedException e) {
	// clientUILogout();
	// } catch (final Throwable e) {
	// GWT.log("Other kind of exception in doLogout", null);
	// throw new RuntimeException();
	// }
	// }
	//
	// public void onSuccess(final Object arg0) {
	// Site.hideProgress();
	// clientUILogout();
	// }
	//
	// private void clientUILogout() {
	// view.restoreLoginLink();
	// view.resetOptionsSubmenu();
	// view.setLogoutLinkVisible(false);
	// listener.onUserLoggedOut();
	// }
	// };
	// UserService.App.getInstance().logout(session.getUserHash(),
	// callback);
    }

    public void doNewGroup(final String previousTokenOrig) {
	// DefaultDispatcher.getInstance().fire(WorkspaceEvents.ONLY_CHECK_USER_SESSION,
	// new AsyncCallbackSimple<Object>() {
	// public void onSuccess(final Object result) {
	// previousToken = previousTokenOrig;
	// if (session.isLogged()) {
	// Site.showProgressProcessing();
	// view.showNewGroupDialog();
	// view.centerNewGroupDialog();
	// } else {
	// returnToPreviousState();
	// Site.info(i18n.t("Sign in or register to create a group"));
	// }
	// }
	// });
    }

    public void doSearch(final String termToSearch) {
	// view.showSearchPanel(termToSearch);
    }

    public View getView() {
	return view;
    }

    public void hideProgress() {
	view.hideProgress();
    }

    public void init(final SiteBarView view) {
	this.view = view;
	view.setLogoutLinkVisible(false);
    }

    public void mask() {
	view.mask();
    }

    public void mask(final String message) {
	view.mask(message);
    }

    public void onHelpInTranslation() {
    }

    public void onLoginCancelled() {
	view.hideLoginDialog();
	returnToPreviousState();
    }

    public void onLoginClose() {
	if (!session.isLogged()) {
	    returnToPreviousState();
	}
    }

    public void onNewGroupCancel() {
	view.hideNewGroupDialog();
	returnToPreviousState();
    }

    public void onNewGroupClose() {
	returnToPreviousState();
    }

    public void onNewGroupCreated(final StateToken homePage) {
	view.hideNewGroupDialog();
	changeState(homePage);
    }

    public void reloadUserInfo(final String userHash) {
	// //final UserServiceAsync siteBarService =
	// UserService.App.getInstance();
	// siteBarService.reloadUserInfo(userHash, new
	// AsyncCallback<UserInfoDTO>() {
	// public void onFailure(final Throwable arg0) {
	// Site.hideProgress();
	// }
	//
	// public void onSuccess(final UserInfoDTO response) {
	// showLoggedUser(response);
	// Site.hideProgress();
	// }
	// });
    }

    public void setState(final StateDTO state) {
	final StateToken token = state.getStateToken();
	if (state.getAccessLists().getViewers().getMode().equals(GroupListDTO.EVERYONE)) {
	    final String publicUrl = token.getPublicUrl();
	    view.setContentGotoPublicUrl(publicUrl);
	    view.setContentPublic(true);
	} else {
	    view.setContentPublic(false);
	}

    }

    public void showAlertMessage(final String message) {
	view.showAlertMessage(message);
    }

    public void showLoggedUser(final UserInfoDTO user) {
	if (user == null) {
	    view.restoreLoginLink();
	    view.setLogoutLinkVisible(false);
	} else {
	    view.showLoggedUserName(user.getShortName(), user.getHomePage());
	    view.setLogoutLinkVisible(true);
	    view.setGroupsIsMember(user.getGroupsIsAdmin(), user.getGroupsIsCollab());
	}
    }

    public void showProgress(final String text) {
	view.showProgress(text);
    }

    public void unMask() {
	view.unMask();
    }

    protected void onSearchFocus() {
	view.setTextSearchBig();
	view.clearSearchText();
    }

    protected void onSearchLostFocus(final String search) {
	if (search.length() == 0) {
	    view.setDefaultTextSearch();
	    view.setTextSearchSmall();
	}
    }

    private void returnToPreviousState() {
	if (this.previousToken != null) {
	    listener.onChangeState(new StateToken(this.previousToken));
	    this.previousToken = null;
	}
    }

}
