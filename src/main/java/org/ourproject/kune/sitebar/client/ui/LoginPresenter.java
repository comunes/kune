package org.ourproject.kune.sitebar.client.ui;

public class LoginPresenter implements LoginListener {

    private LoginView view;
    private SiteBarView sitebar;
    private boolean loginEnabled;

    public void init(LoginView loginview, SiteBarView sitebar) {
        this.view = loginview;
        this.sitebar = sitebar;
        this.loginEnabled = false;
    }

    public void doCancel() {
        sitebar.hideLoginDialog();
    }

    public void onDataChanged(String nick, String pass) {
        if (nick.length() > 0 && pass.length() > 0)
            if (!loginEnabled)
                view.setEnabledLoginButton(true);
        else
            if (loginEnabled)
                view.setEnabledLoginButton(false);
    }

    public void doLogin(String nick, String pass) {

    }

}
