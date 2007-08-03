package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.group.NewGroupListener;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarPresenter implements SiteBar, SiteBarListener, LoginListener, NewGroupListener {

    private SiteBarView view;

    public SiteBarPresenter() {
    }

    public void init(SiteBarView view) {
        this.view = view;
        view.setLogoutLinkVisible(false);
    }

    public void doLogin() {
        view.showLoginDialog();
    }

    public void doNewGroup() {
        view.showNewGroupDialog();
    }

    public void doSearch(String string) {
        if (string == null)
            Window.alert("Type something to search!");
        else
            Window.alert("In development!");
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

    public void onNewGroupCreated() {
        view.hideNewGroupDialog();
    }

    public void onNewGroupCancel() {
        view.hideNewGroupDialog();
    }

    public void setProgressText(String text) {
        view.setProgressText(text);
    }

    public void showProgress(boolean show) {
        view.showProgress(show);
    }

}
