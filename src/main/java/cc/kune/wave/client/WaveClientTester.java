package cc.kune.wave.client;

import cc.kune.common.client.noti.NotifyUser;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public class WaveClientTester {

    public WaveClientTester() {
    }

    public void doLogin(final String userWithoutDomain, final String passwd) {
        final RequestBuilder request = new RequestBuilder(RequestBuilder.POST, "/auth/signin/");
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
                    NotifyUser.info(response.getText());
                }
            });
        } catch (final RequestException e) {
            NotifyUser.error(e.getStackTrace().toString(), true);
        }
    }
}
