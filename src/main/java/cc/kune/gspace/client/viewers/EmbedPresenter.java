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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.embed.EmbedConfiguration;
import cc.kune.core.client.embed.EmbedJsActions;
import cc.kune.core.client.embed.EmbedSitebar;
import cc.kune.core.client.events.EmbAppStartEvent;
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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

  private final EmbedConfiguration conf;
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

  @Inject
  public EmbedPresenter(final EventBus eventBus, final EmbedView view, final EmbedProxy proxy,
      final WaveClientManager waveClientManager, final WaveClientProvider waveClient,
      final I18nTranslationService i18n, final Session session, final Provider<EmbedSitebar> sitebar,
      final EmbedConfiguration conf) {
    super(eventBus, view, proxy);
    NotifyUser.showProgressLoading();
    // FIXME: use AppStart to detect browser compatibility
    this.session = session;
    this.sitebar = sitebar;
    this.conf = conf;
    EmbedConfiguration.export();
    EmbedJsActions.export();
    TokenMatcher.init(GwtWaverefEncoder.INSTANCE);
    eventBus.addHandler(EmbAppStartEvent.getType(), new EmbAppStartEvent.EmbAppStartHandler() {
      @Override
      public void onAppStart(final EmbAppStartEvent event) {
        onAppStarted();
      }
    });
    if (conf.isReady()) {
      onAppStarted();
    }
  }

  private void errorRetrieving() {
    NotifyUser.important(I18n.t("Ups, we find some problem trying to show you this document"));
    NotifyUser.hideProgress();
  }

  private void getContentFromHash(final String currentHash) {

    final boolean isGroupToken = TokenMatcher.isGroupToken(currentHash);
    final boolean isWaveToken = TokenMatcher.isWaveToken(currentHash);
    final String suffix = isGroupToken ? "" : isWaveToken ? "ByWaveRef" : "";

    if (isGroupToken || isWaveToken) {
      // Ok is a token like group.tool.number
      final String getContentUrl = GWT.getModuleBaseURL() + "json/ContentJSONService/getContent"
          + suffix + "?" + new UrlParam(JSONConstants.HASH_PARAM, session.getUserHash())
          + new UrlParam("&" + JSONConstants.TOKEN_PARAM, URL.encodeQueryString(currentHash));
      processRequest(getContentUrl, new Callback<JavaScriptObject, Void>() {
        @Override
        public void onFailure(final Void reason) {
          notFound();
        }

        @Override
        public void onSuccess(final JavaScriptObject response) {
          // final StateToken currentToken = new StateToken(currentHash);
          NotifyUser.hideProgress();
          final StateAbstractDTOJs state = (StateAbstractDTOJs) response;
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

  private String getCurrentHash() {
    return HistoryUtils.undoHashbang(History.getToken());
  }

  /**
   * Not found.
   */
  private void notFound() {
    NotifyUser.important(I18n.t("Content not found"));
    NotifyUser.hideProgress();
  }

  private void onAppStarted() {
    final String userHash = session.getUserHash();
    Log.info("Started embed presenter with userhash: " + userHash);

    final String initUrl = GWT.getModuleBaseURL() + "json/SiteJSONService/getInitData?"
        + new UrlParam(JSONConstants.HASH_PARAM, userHash);
    processRequest(initUrl, new Callback<JavaScriptObject, Void>() {
      @Override
      public void onFailure(final Void reason) {
        // Do nothing
      }

      @Override
      public void onSuccess(final JavaScriptObject response) {
        final InitDataDTOJs initData = (InitDataDTOJs) response;
        session.setInitData(parse(initData));
        final UserInfoDTOJs userInfo = (UserInfoDTOJs) initData.getUserInfo();
        session.setCurrentUserInfo(parse(userInfo), null);
        final String currentHash = getCurrentHash();
        if (currentHash != null) {
          RevealRootContentEvent.fire(EmbedPresenter.this, EmbedPresenter.this);
          getContentFromHash(currentHash);
        } else {
          // Maybe we embed via JNSI
        }
      }
    });
  }

  private void onGetContentSucessful(final Session session, final StateAbstractDTO state) {
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;

    final boolean isLogged = session.isLogged();
    final boolean isParticipant = stateContent.isParticipant();
    Log.info("Is logged: " + isLogged + " isParticipant: " + isParticipant);
    final Boolean readOnly = conf.get().getReadOnly();
    Log.info("Is readonly: " + readOnly);
    final Boolean isReadOnly = readOnly == null ? false : readOnly;
    if (isLogged && isParticipant && !isReadOnly) {
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
    getContentFromHash(getCurrentHash());
  }

  private InitDataDTO parse(final InitDataDTOJs initJ) {
    final InitDataDTO init = new InitDataDTO();
    init.setStoreUntranslatedStrings(initJ.getStoreUntranslatedStrings());
    init.setSiteLogoUrl(initJ.getSiteLogoUrl());
    init.setSiteLogoUrlOnOver(initJ.getsiteLogoUrlOnOver());
    return init;
  }

  private UserInfoDTO parse(final UserInfoDTOJs userInfo) {
    final String userHash = userInfo.getUserHash();
    if (userHash == null) {
      Log.info("We are NOT logged");
      return null;
    } else {
      final UserInfoDTO info = new UserInfoDTO();
      info.setUserHash(userHash);
      Log.info("We are logged");
      info.setSessionJSON(userInfo.getSessionJSON());
      info.setWebsocketAddress(userInfo.getWebsocketAddress());
      info.setClientFlags(userInfo.getClientFlags());
      return info;
    }
  }

  private void processRequest(final String url, final Callback<JavaScriptObject, Void> callback) {
    final JsonpRequestBuilder builder = new JsonpRequestBuilder();
    builder.setTimeout(60000);
    @SuppressWarnings("unused")
    final JsonpRequest<JavaScriptObject> request = builder.requestObject(url,
        new AsyncCallback<JavaScriptObject>() {
          @Override
          public void onFailure(final Throwable exception) {
            errorRetrieving();
            Log.error("JSON exception: ", exception);
            callback.onFailure(null);
          }

          @Override
          public void onSuccess(final JavaScriptObject result) {
            callback.onSuccess(result);
          }
        });

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    // We do this only if we embed this via url #tokenhash
    // RevealRootContentEvent.fire(this, this);
  }
}
