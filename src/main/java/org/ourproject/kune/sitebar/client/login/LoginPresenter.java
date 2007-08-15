package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter implements Login {
    LoginView view;
    final LoginListener listener;

    // private boolean loginButtonEnabled;

    public LoginPresenter(final LoginListener listener) {
	this.listener = listener;
	// //this.loginButtonEnabled = false;
    }

    public void init(final LoginView loginview) {
	this.view = loginview;
	reset();
    }

    public void doCancel() {
	reset();
	listener.onLoginCancelled();
    }

    public void doLogin() {
	final String nick = view.getUsername();
	final String pass = view.getPassword();

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

    private void reset() {
	view.clearData();
    }

}
