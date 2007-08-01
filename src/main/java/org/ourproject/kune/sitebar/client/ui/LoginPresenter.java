package org.ourproject.kune.sitebar.client.ui;


import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter {
    private LoginView view;
    private final LoginListener listener;

    public LoginPresenter(LoginListener listener) {
        this.listener = listener;
    }
    
    public void init(LoginView loginview) {
        this.view = loginview;
    }

    public void doCancel() {
        listener.onLoginCancelled();
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
        SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
        siteBarService.login(nick, pass, new AsyncCallback () {

            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
            }

            public void onSuccess(Object response) {
                String hash = (String) response;
                listener.userLoggedIn(nick, hash);
                // TODO: Establecer sesión de este usuario
            }});
    }

}
