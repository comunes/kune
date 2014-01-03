/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import cc.kune.core.client.events.StackErrorEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveClientSimpleAuthenticator.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveClientSimpleAuthenticator {

  /** The event bus. */
  private final EventBus eventBus;

  /**
   * Instantiates a new wave client simple authenticator.
   * 
   * @param eventBus
   *          the event bus
   */
  @Inject
  public WaveClientSimpleAuthenticator(final EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Do login.
   * 
   * @param userWithoutDomain
   *          the user without domain
   * @param passwd
   *          the passwd
   * @param callback
   *          the callback
   */
  public void doLogin(final String userWithoutDomain, final String passwd,
      final AsyncCallback<Void> callback) {
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
          StackErrorEvent.fire(eventBus, exception);
          callback.onFailure(exception);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          callback.onSuccess(null);
        }
      });
    } catch (final RequestException e) {
      StackErrorEvent.fire(eventBus, e);
    }
  }

  /**
   * Do logout.
   * 
   * @param callback
   *          the callback
   */
  public void doLogout(final AsyncCallback<Void> callback) {
    // Original: <a href=\"/auth/signout?r=/\">"
    final RequestBuilder request = new RequestBuilder(RequestBuilder.GET, "/auth/signout");
    try {
      request.setHeader("Content-Type", "application/x-www-form-urlencoded");
      final StringBuffer params = new StringBuffer();
      request.sendRequest(params.toString(), new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable exception) {
          StackErrorEvent.fire(eventBus, exception);
          callback.onFailure(exception);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          callback.onSuccess(null);
        }
      });
    } catch (final RequestException e) {
      StackErrorEvent.fire(eventBus, e);
    }
  }

  /**
   * Gets the cookie token value.
   * 
   * @return the cookie token value
   */
  public String getCookieTokenValue() {
    return Cookies.getCookie("JSESSIONID");
  }
}
