package org.ourproject.kune.sitebar.client.ui;

import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarPresenter implements SiteBarListener, LoginListener {

    private SiteBarView view;

    public SiteBarPresenter() {
    }

    public void doLogin() {
        view.showLoginDialog();
    }

    public void doNewGroup() {
    }

    public void doSearch(String string) {
        if (string == null)
            Window.alert("Type something to search!");
        else
            Window.alert("In development!");
    }

    public void init(SiteBarView view) {
        this.view = view;
        view.setLogoutLinkVisible(false);
    }

    public void doLogout() {
        SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
        siteBarService.logout(new AsyncCallback() {

            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
            }

            public void onSuccess(Object arg0) {
                view.setLogoutLinkVisible(false);
                view.restoreLoginLink();
            }
        });

    }

    public void userLoggedIn(String nick, String hash) {
        view.showLoggedUserName(nick);
        view.hideLoginDialog();
        view.setLogoutLinkVisible(true);
    }

    public void onLoginCancelled() {
        view.hideLoginDialog();
    }

}
