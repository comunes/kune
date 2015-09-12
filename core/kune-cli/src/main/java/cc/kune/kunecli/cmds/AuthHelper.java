/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.kunecli.cmds;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.kunecli.KuneCliMain;

@Singleton
public class AuthHelper {

  @Inject
  UserServiceAsync service;

  public void loginAndContinue(final String user, final String pass,
      final AsyncCallback<String> callback) {
    final BasicCookieStore cookieStore = new org.apache.http.impl.client.BasicCookieStore();
    Unirest.setHttpClient(
        org.apache.http.impl.client.HttpClients.custom().setDefaultCookieStore(cookieStore).build());

    try {
      Unirest.post(KuneCliMain.SERVER_PREFFIX + "/auth/signin").header("Content-Type",
          "application/x-www-form-urlencoded").field("address", user).field("password", pass).field(
              "signIn", "Sign in").asJson();
      String authCookie = null;

      for (final Cookie cookie : cookieStore.getCookies()) {
        if (cookie.getName().equals("JSESSIONID")) {
          authCookie = cookie.getValue();
          break;
        }
      }

      final String userhash = authCookie;
      service.login(user, pass, userhash, new AsyncCallback<UserInfoDTO>() {
        private void continueLogin(final String userhash) {
          callback.onSuccess(userhash);
        }

        @Override
        public void onFailure(final Throwable caught) {
          // TODO Auto-generated method stub
          if (caught instanceof java.io.EOFException) {
            // Ok, this is normal becaus UserInfoDTO is not
            // java.io.Serializable (is GWT IsSerializable).
            continueLogin(userhash);
          } else {
            callback.onFailure(caught);
          }
        }

        @Override
        public void onSuccess(final UserInfoDTO result) {
          continueLogin(userhash);
        }
      });
    } catch (final UnirestException e) {
      callback.onFailure(e);
    }
  }
}
