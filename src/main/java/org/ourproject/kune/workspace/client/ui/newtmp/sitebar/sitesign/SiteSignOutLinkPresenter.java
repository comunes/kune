package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteSignOutLinkPresenter implements SiteSignOutLink {

    private SiteSignOutLinkView view;
    private final Session session;
    private final Provider<UserServiceAsync> userServiceProvider;

    public SiteSignOutLinkPresenter(final Session session, final Provider<UserServiceAsync> userServiceProvider,
	    final Provider<KuneErrorHandler> kuneErrorHandlerProvider) {
	this.session = session;
	this.userServiceProvider = userServiceProvider;
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO userInfoDTO) {
		view.setVisible(true);
	    }
	});
	session.onUserSignOut(new Slot0() {
	    public void onEvent() {
		view.setVisible(false);
	    }
	});
	kuneErrorHandlerProvider.get().onSessionExpired(new Slot0() {
	    public void onEvent() {
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
	userServiceProvider.get().logout(session.getUserHash(), callback);
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
