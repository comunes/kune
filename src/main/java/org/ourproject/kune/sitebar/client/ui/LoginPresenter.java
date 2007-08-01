package org.ourproject.kune.sitebar.client.ui;


import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter implements LoginListener {

    private LoginView view;
    private SiteBarView sitebar;

    public void init(LoginView loginview, SiteBarView sitebar) {
        this.view = loginview;
        this.sitebar = sitebar;
    }

    public void doCancel() {
        sitebar.hideLoginDialog();
    }

    public void onDataChanged(String nick, String pass) {
        if (nick.length() > 0 && pass.length() > 0)
            if (!view.isEnabledLoginButton())
                view.setEnabledLoginButton(true);
        else
            if (view.isEnabledLoginButton())
                view.setEnabledLoginButton(false);
    }

    public void doLogin(final String nick, String pass) {
        // TODO Auto-generated method stub

        SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
        siteBarService.login(nick, pass, new AsyncCallback () {

            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
            }

            public void onSuccess(Object arg0) {
                sitebar.showLoggedUserName(nick);
                sitebar.hideLoginDialog();
                sitebar.setLogoutLinkVisible(true);
                // TODO: Establecer sesión de este usuario
            }});
    }

}
