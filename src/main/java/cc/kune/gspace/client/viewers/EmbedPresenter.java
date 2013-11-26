/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.embed.EmbedSitebar;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.JSONConstants;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.InitDataDTOJs;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateAbstractDTOJs;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateTokenJs;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.UserInfoDTOJs;
import cc.kune.wave.client.kspecific.WaveClientManager;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * The Class EmbedPresenter is used to embed kune waves in other CMSs (for
 * instance)
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EmbedPresenter extends Presenter<EmbedPresenter.EmbedView, EmbedPresenter.EmbedProxy>
    implements ValueChangeHandler<String> {

  /**
   * The Interface EmbedProxy.
   */
  @ProxyStandard
  public interface EmbedProxy extends Proxy<EmbedPresenter> {
  }

  /**
   * The Interface EmbedView.
   */
  public interface EmbedView extends WaveViewerView {
  }

  private final Session session;
  private final Provider<EmbedSitebar> sitebar;

  /**
   * Instantiates a new embed presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param siteService
   *          the site service
   * @param service
   *          the service
   * @param waveClientManager
   *          the wave client manager
   * @param waveClient
   *          the wave client
   * @param matcher
   *          the matcher
   * @param session
   *          the session
   */
  private Timer timer;

  @Inject
  public EmbedPresenter(final EventBus eventBus, final EmbedView view, final EmbedProxy proxy,
      final WaveClientManager waveClientManager, final WaveClientProvider waveClient,
      final I18nUITranslationService i18n, final Session session, final Provider<EmbedSitebar> sitebar) {
    super(eventBus, view, proxy);
    this.session = session;
    this.sitebar = sitebar;
    TokenMatcher.init(GwtWaverefEncoder.INSTANCE);
    Log.info("Started embed presenter");

    final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL()
        + "json/SiteJSONService/getInitData?"
        + new UrlParam(JSONConstants.HASH_PARAM, session.getUserHash()));

    processRequest(builder, new Callback<Response, Void>() {
      @Override
      public void onFailure(final Void reason) {
        // Do nothing
      }

      @Override
      public void onSuccess(final Response response) {
        final InitDataDTOJs initData = JsonUtils.safeEval(response.getText());
        session.setInitData(parse(initData));
        final UserInfoDTOJs userInfo = (UserInfoDTOJs) initData.getUserInfo();
        // NotifyUser.info(json.getClass(), true);
        session.setCurrentUserInfo(parse(userInfo), null);
        getContentFromHash();

        timer = new Timer() {
          @Override
          public void run() {
            getContentFromHash();
          }
        };

        session.onUserSignInOrSignOut(false, new UserSignInOrSignOutHandler() {
          @Override
          public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
            timer.schedule(500);
          }
        });
      }
    });

  }

  private void errorRetrieving() {
    NotifyUser.important(I18n.t("Error retrieving content"));
    NotifyUser.hideProgress();
  }

  private void getContentFromHash() {
    final String currentHash = HistoryUtils.undoHashbang(History.getToken());
    final boolean isGroupToken = TokenMatcher.isGroupToken(currentHash);
    final boolean isWaveToken = TokenMatcher.isWaveToken(currentHash);
    final String suffix = isGroupToken ? "" : isWaveToken ? "ByWaveRef" : "";

    if (isGroupToken || isWaveToken) {
      // Ok is a token like group.tool.number
      final RequestBuilder request = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL()
          + "json/ContentJSONService/getContent" + suffix + "?"
          + new UrlParam(JSONConstants.HASH_PARAM, session.getUserHash())
          + new UrlParam("&" + JSONConstants.TOKEN_PARAM, URL.encodeQueryString(currentHash)));
      processRequest(request, new Callback<Response, Void>() {
        @Override
        public void onFailure(final Void reason) {
          notFound();
        }

        @Override
        public void onSuccess(final Response response) {
          // final StateToken currentToken = new StateToken(currentHash);
          NotifyUser.hideProgress();
          final StateAbstractDTOJs state = JsonUtils.safeEval(response.getText());
          final StateTokenJs stateToken = (StateTokenJs) state.getStateToken();

          // getContent returns the default content if doesn't finds the content
          if ((isGroupToken && currentHash.equals(stateToken.getEncoded()))
              || (isWaveToken && currentHash.equals(state.getWaveRef()))) {
            onGetContentSucessful(session, parse(state));
          } else {
            // getContent returns def content if content not found
            notFound();
          }
        }

        private StateAbstractDTO parse(final StateAbstractDTOJs stateJs) {
          final StateContentDTO state = new StateContentDTO();
          state.setContent(stateJs.getContent());
          state.setWaveRef(stateJs.getWaveRef());
          state.setTitle(stateJs.getTitle());
          state.setIsParticipant(stateJs.isParticipant());
          // state.setStateToken(new StateToken((StateTokenJs)
          // stateJs.getStateToken()).getEncoded());
          return state;
        }
      });

    } else {
      // Do something
      notFound();
    }
  }

  /**
   * Not found.
   */
  private void notFound() {
    NotifyUser.important(I18n.t("Content not found"));
    NotifyUser.hideProgress();
  }

  private void onGetContentSucessful(final Session session, final StateAbstractDTO state) {
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;

    if (session.isLogged() && stateContent.isParticipant()) {
      getView().setEditableContent(stateContent);
    } else {
      getView().setContent(stateContent);
    }

    // FIXME use GWTP here
    GWT.runAsync(new RunAsyncCallback() {
      @Override
      public void onFailure(final Throwable reason) {
        // By now, do nothing
      }

      @Override
      public void onSuccess() {
        sitebar.get();
      }

    });
  }

  @Override
  public void onValueChange(final ValueChangeEvent<String> event) {
    getContentFromHash();
  }

  private InitDataDTO parse(final InitDataDTOJs initJ) {
    final InitDataDTO init = new InitDataDTO();
    init.setStoreUntranslatedStrings(initJ.getStoreUntranslatedStrings());
    init.setSiteLogoUrl(initJ.getSiteLogoUrl());
    init.setSiteLogoUrlOnOver(initJ.getsiteLogoUrlOnOver());
    return init;
  }

  private UserInfoDTO parse(final UserInfoDTOJs userInfo) {
    final UserInfoDTO info = new UserInfoDTO();
    info.setUserHash(userInfo.getUserHash());
    info.setSessionJSON(userInfo.getSessionJSON());
    info.setWebsocketAddress(userInfo.getWebsocketAddress());
    info.setClientFlags(userInfo.getClientFlags());

    return info;
  }

  private void processRequest(final RequestBuilder builder, final Callback<Response, Void> callback) {
    try {
      @SuppressWarnings("unused")
      final Request request = builder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable exception) {
          errorRetrieving();
          Log.error("JSON exception: ", exception);
          callback.onFailure(null);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          if (200 == response.getStatusCode()) {
            callback.onSuccess(response);
          } else {
            errorRetrieving();
            Log.error("Couldn't retrieve JSON (" + response.getStatusText() + ")");
            callback.onFailure(null);
          }
        }
      });
    } catch (final RequestException exception) {
      errorRetrieving();
      Log.error("JSON exception: ", exception);
      callback.onFailure(null);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}
