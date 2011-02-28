package cc.kune.wave.client;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

public class WaveClientSimpleAuthenticator {

    private final WsArmor wsArmor;

    @Inject
    public WaveClientSimpleAuthenticator(final WsArmor wsArmor) {
        this.wsArmor = wsArmor;
    }

    public void doLogin(final String userWithoutDomain, final String passwd, final SimpleCallback callback) {
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
                    callback.onCancel();
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    final String url = WindowUtils.getLocation().getHost() + "/";
                    Log.debug("Wave client url: " + url);
                    callback.onSuccess();
                    // String token =
                    // Cookies.getCookie(JETTY_SESSION_TOKEN_NAME);
                    final ForIsWidget userSpace = wsArmor.getUserSpace();
                    if (userSpace.getWidgetCount() > 0) {
                        userSpace.remove(0);
                    }
                    userSpace.add(new WebClient());
                }
            });
        } catch (final RequestException e) {
            NotifyUser.error(e.getStackTrace().toString(), true);
        }
    }
}
