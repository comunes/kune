package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginFormPresenter implements LoginForm {
    LoginFormView view;
    final LoginListener listener;

    // private boolean loginButtonEnabled;

    public LoginFormPresenter(final LoginListener listener) {
	this.listener = listener;
	// //this.loginButtonEnabled = false;
    }

    public void init(final LoginFormView loginview) {
	this.view = loginview;
	reset();
    }

    public void doCancel() {
	reset();
	listener.onLoginCancelled();
    }

    public void doLogin() {
	final String nickOrEmail = view.getNickOrEmail();
	final String passwd = view.getPassword();

	SiteBarServiceAsync siteBarService = SiteBarService.App.getInstance();
	siteBarService.login(nickOrEmail, passwd, new AsyncCallback() {

	    public void onFailure(final Throwable arg0) {
		// i18n: Error in authentication
		Site.important("Error in authentication");
	    }

	    public void onSuccess(final Object response) {
		String hash = (String) response;
		listener.userLoggedIn(nickOrEmail, hash);
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
