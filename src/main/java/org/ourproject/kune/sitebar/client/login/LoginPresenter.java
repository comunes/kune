package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter implements Login {
    private LoginView view;
    private final LoginListener listener;
    private boolean loginButtonEnabled;

    public LoginPresenter(final LoginListener listener) {
	this.listener = listener;
	this.loginButtonEnabled = false;
    }

    public void init(final LoginView loginview) {
	this.view = loginview;
	reset();
    }

    private void reset() {
	view.setEnabledLoginButton(false);
	view.clearData();
    }

    public void doCancel() {
	reset();
	listener.onLoginCancelled();
    }

    public void onDataChanged(final String nick, final String pass) {
	if (nick.length() > 0 && pass.length() > 0) {
	    if (!loginButtonEnabled) {
		loginButtonEnabled = true;
		view.setEnabledLoginButton(true);
	    }
	} else {
	    if (loginButtonEnabled) {
		loginButtonEnabled = false;
		view.setEnabledLoginButton(false);
	    }
	}
    }

    public void doLogin(final String nick, final String pass) {
	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	siteBarService.login(nick, pass, new AsyncCallback() {

	    public void onFailure(final Throwable arg0) {
		// TODO Auto-generated method stub
	    }

	    public void onSuccess(final Object response) {
		String hash = (String) response;
		listener.userLoggedIn(nick, hash);
		// TODO: Establecer sesión de este usuario
	    }
	});
    }

    public View getView() {
	return view;
    }

}
