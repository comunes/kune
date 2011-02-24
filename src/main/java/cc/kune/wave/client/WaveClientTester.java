package cc.kune.wave.client;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.inject.Inject;

public class WaveClientTester {

    private final WsArmor wsarmor;

    @Inject
    public WaveClientTester(final WsArmor wsarmor, final Session session) {
        this.wsarmor = wsarmor;
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                doLogin(event.getUserInfo().getChatName(), event.getUserInfo().getChatPassword());
            }
        });

        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void doLogin(final String userWithoutDomain, final String passwd) {
        final RequestBuilder request = new RequestBuilder(RequestBuilder.POST, "/auth/signin");
        final StringBuffer params = new StringBuffer();
        params.append("address=");
        params.append(URL.encodeQueryString(userWithoutDomain));
        params.append("&password=");
        params.append(URL.encodeQueryString(passwd));
        params.append("&signIn=");
        params.append(URL.encodeQueryString("Sign in"));
        try {
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            request.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onError(final Request request, final Throwable exception) {
                    NotifyUser.error(exception.getStackTrace().toString(), true);
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    final String url = WindowUtils.getLocation().getHost() + "/";
                    Log.debug("Wave client url: " + url);
                    // wsarmor.setFrameUrlForTesting(url);
                    wsarmor.setFrameUrlForTesting("/wiiiabbb");
                }
            });
        } catch (final RequestException e) {
            NotifyUser.error(e.getStackTrace().toString(), true);
        }
    }
}
