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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.log.Log;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.embed.EmbedConfiguration;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.InitDataDTOJs;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateAbstractDTOJs;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.UserInfoDTOJs;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Class EmbedHelper.
 * 
 */
public class EmbedHelper {

  /**
   * Fix content urls.
   * 
   * We do some html fix in html content because we are in embeded in another
   * site domain, and the server don't know how to do this
   * 
   * @param serverUrl
   *          the server url
   * @param content
   *          the content
   * @return the string
   */
  public static String fixContentUrls(final String serverUrl, final String content) {
    return content.replaceAll(FileConstants.LOGODOWNLOADSERVLET, TextUtils.removeLastSlash(serverUrl)
        + FileConstants.LOGODOWNLOADSERVLET);
  }

  /**
   * Gets the server.
   * 
   * @return the server
   */
  public static String getServer() {
    final String serverConf = EmbedConfiguration.get().getServerUrl();
    return serverConf == null ? GWT.getHostPageBaseURL() : serverConf;
  }

  /**
   * Gets the server with path.
   * 
   * @return the server with path
   */
  public static String getServerWithPath() {
    final String confServer = EmbedConfiguration.get().getServerUrl();
    final String server = confServer != null ? (confServer.endsWith("/") ? confServer + "wse/"
        : confServer + "/wse/") : GWT.getModuleBaseURL();
    return server;
  }

  /**
   * Parses the init data in js
   * 
   * @param init
   *          data the init js
   * @return the inidata DTO
   */
  public static InitDataDTO parse(final InitDataDTOJs initJ) {
    final InitDataDTO init = new InitDataDTO();
    init.setStoreUntranslatedStrings(initJ.getStoreUntranslatedStrings());
    init.setSiteLogoUrl(initJ.getSiteLogoUrl());
    init.setSiteLogoUrlOnOver(initJ.getsiteLogoUrlOnOver());
    return init;
  }

  /**
   * Parses the state in js.
   * 
   * @param stateJs
   *          the state js
   * @return the state abstract dto
   */
  public static StateAbstractDTO parse(final StateAbstractDTOJs stateJs) {
    final StateContentDTO state = new StateContentDTO();
    state.setContent(stateJs.getContent());
    state.setWaveRef(stateJs.getWaveRef());
    state.setTitle(stateJs.getTitle());
    state.setIsParticipant(stateJs.isParticipant());
    // state.setStateToken(new StateToken((StateTokenJs)
    // stateJs.getStateToken()).getEncoded());
    return state;
  }

  /**
   * Parses the userInfo js.
   * 
   * @param userInfo
   *          the user info
   * @return the user info dto
   */
  public static UserInfoDTO parse(final UserInfoDTOJs userInfo) {
    final String userHash = userInfo.getUserHash();
    if (userHash == null || userHash.equals("null")) {
      // sesssion.setHash?
      Log.info("We are NOT logged");
      return null;
    } else {
      final UserInfoDTO info = new UserInfoDTO();
      info.setUserHash(userHash);
      Log.info("We are logged, userhash: " + userHash);
      final String waveSession = userInfo.getSessionJSON();
      info.setSessionJSON(waveSession);
      Log.info("wave session: " + waveSession);
      info.setWebsocketAddress(userInfo.getWebsocketAddress());
      info.setClientFlags(userInfo.getClientFlags());
      return info;
    }
  }

  /**
   * Process json request.
   * 
   * @param url
   *          the url
   * @param callback
   *          the callback
   */
  public static void processJSONRequest(final String url, final Callback<JavaScriptObject, Void> callback) {
    final JsonpRequestBuilder builder = new JsonpRequestBuilder();
    builder.setTimeout(60000);
    @SuppressWarnings("unused")
    final JsonpRequest<JavaScriptObject> request = builder.requestObject(url,
        new AsyncCallback<JavaScriptObject>() {
          @Override
          public void onFailure(final Throwable exception) {
            Log.error("JSON exception: ", exception);
            callback.onFailure(null);
          }

          @Override
          public void onSuccess(final JavaScriptObject result) {
            callback.onSuccess(result);
          }
        });
  }

  /**
   * Process request.
   * 
   * @param url
   *          the url
   * @param callback
   *          the callback
   */
  public static void processRequest(final String url, final Callback<Response, Void> callback) {
    try {
      final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
      // Needed for CORS
      builder.setIncludeCredentials(true);
      @SuppressWarnings("unused")
      final Request request = builder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable exception) {
          Log.error("CORS exception: ", exception);
          callback.onFailure(null);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          if (200 == response.getStatusCode()) {
            callback.onSuccess(response);
          } else {
            Log.error("Couldn't retrieve CORS (" + response.getStatusText() + ")");
            callback.onFailure(null);
          }
        }
      });
    } catch (final RequestException exception) {
      Log.error("CORS exception: ", exception);
      callback.onFailure(null);
    }
  }
}