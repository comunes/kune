package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.View;
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
		// TODO Auto-generated method stub
	    }

	    public void onSuccess(final Object arg0) {
		view.setLogoutLinkVisible(false);
		view.restoreLoginLink();
	    }
	});
    }

    public void userLoggedIn(final String userOrEmail, final String hash) {
	view.showLoggedUserName(userOrEmail);
	view.hideLoginDialog();
	view.setLogoutLinkVisible(true);
	listener.onUserLoggedIn();
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

}
