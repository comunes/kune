package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.calclab.suco.client.signal.Slot;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteSignOutLinkPresenter implements SiteSignOutLink {

    private SiteSignOutLinkView view;
    private final Session session;
    private final UserServiceAsync userService;

    public SiteSignOutLinkPresenter(final Session session, final UserServiceAsync userService,
	    final KuneErrorHandler errorHandler) {
	this.session = session;
	this.userService = userService;
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO parameter) {
		view.setVisible(true);
	    }
	});
	session.onUserSignOut(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
		view.setVisible(false);
	    }
	});
	errorHandler.onSessionExpired(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
		clientUIsignOut();
	    }
	});
    }

    public void doSignOut() {
	final AsyncCallback<Object> callback = new AsyncCallback<Object>() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
		try {
		    throw caught;
		} catch (final SessionExpiredException e) {
		    clientUIsignOut();
		} catch (final UserMustBeLoggedException e) {
		    clientUIsignOut();
		} catch (final Throwable e) {
		    GWT.log("Other kind of exception in doLogout", null);
		    throw new RuntimeException();
		}
	    }

	    public void onSuccess(final Object arg0) {
		Site.hideProgress();
		clientUIsignOut();
	    }

	};
	userService.logout(session.getUserHash(), callback);
    }

    public View getView() {
	return view;
    }

    public void init(final SiteSignOutLinkView view) {
	this.view = view;
	view.setVisible(false);
    }

    private void clientUIsignOut() {
	// FIXME: Remove cookie doesn't works in all browsers, know
	// issue:
	// http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/ded86778ee56690/515dc513c7d085eb?lnk=st&q=remove+cookie#515dc513c7d085eb
	// http://code.google.com/p/google-web-toolkit/issues/detail?id=1735&q=removeCookie
	Cookies.removeCookie(Site.USERHASH);
	// Workaround:
	Cookies.setCookie(Site.USERHASH, null, new Date(0), null, "/", false);
	session.setUserHash(null);
	session.setCurrentUserInfo(null);
    }

}
