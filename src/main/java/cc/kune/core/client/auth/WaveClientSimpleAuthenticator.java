package cc.kune.core.client.auth;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class WaveClientSimpleAuthenticator {

    private final WsArmor wsArmor;

    @Inject
    public WaveClientSimpleAuthenticator(final WsArmor wsArmor) {
        this.wsArmor = wsArmor;
    }

    public void doLogin(final String userWithoutDomain, final String passwd, final AsyncCallback<Void> callback) {
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
                    callback.onFailure(exception);
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    callback.onSuccess(null);
                }
            });
        } catch (final RequestException e) {
            Log.error(e.getStackTrace().toString());
        }
    }

    public void doLogout(final AsyncCallback<Void> callback) {
        // Original: <a href=\"/auth/signout?r=/\">"
        final RequestBuilder request = new RequestBuilder(RequestBuilder.GET, "/auth/signout");
        try {
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            final StringBuffer params = new StringBuffer();
            request.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onError(final Request request, final Throwable exception) {
                    NotifyUser.error(exception.getStackTrace().toString(), true);
                    callback.onFailure(exception);
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    callback.onSuccess(null);
                }
            });
        } catch (final RequestException e) {
            Log.error(e.getStackTrace().toString());
        }
    }

    public String getCookieTokenValue() {
        return Cookies.getCookie("JSESSIONID");
    }
}
