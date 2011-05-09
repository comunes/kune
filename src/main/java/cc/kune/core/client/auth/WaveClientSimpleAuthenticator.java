/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.client.auth;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.gspace.client.GSpaceArmor;

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

    private final GSpaceArmor wsArmor;

    @Inject
    public WaveClientSimpleAuthenticator(final GSpaceArmor wsArmor) {
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
