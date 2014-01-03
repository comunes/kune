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

import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.embed.EmbedConfiguration;
import cc.kune.core.client.embed.EmbedOpenEvent;
import cc.kune.core.client.embed.EmbedSitebar;
import cc.kune.core.client.events.EmbAppStartEvent;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.JSONConstants;
import cc.kune.core.shared.dto.InitDataDTOJs;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateAbstractDTOJs;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateTokenJs;
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
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
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

  private final ClientFileDownloadUtils clientDownUtils;
  private final boolean devMode = true;
  private final Session session;
  private final Provider<EmbedSitebar> sitebar;
  protected String stateTokenToOpen;

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
      final ClientFileDownloadUtils clientDownUtils) {
    super(eventBus, view, proxy);
    this.clientDownUtils = clientDownUtils;
    NotifyUser.showProgressLoading();
    // FIXME: Maybe use AppStart to detect browser compatibility in the future
    this.session = session;
    this.sitebar = sitebar;
    session.setEmbedded(true);
    TokenMatcher.init(GwtWaverefEncoder.INSTANCE);
    eventBus.addHandler(EmbedOpenEvent.getType(), new EmbedOpenEvent.EmbedOpenHandler() {
      @Override
      public void onEmbedOpen(final EmbedOpenEvent event) {
        stateTokenToOpen = event.getStateToken();
        if (session.getInitData() == null) {
          // Not initialized
        } else {
          // ok, we can continue
          getContentFromHistoryHash(stateTokenToOpen);
        }
      }
    });
    eventBus.addHandler(EmbAppStartEvent.getType(), new EmbAppStartEvent.EmbAppStartHandler() {
      @Override
      public void onAppStart(final EmbAppStartEvent event) {
        // This event is generated after configuration via JSNI
        onAppStarted();
      }
    });
    if (EmbedConfiguration.isReady() || isCurrentHistoryHashValid(getCurrentHistoryHash())) {
      // The event was fired already, so start!
      onAppStarted();
    }
  }

  private void getContentFromHistoryHash(final String stateTokenS) {
    Log.info("Opening statetoken: " + stateTokenS);
    final boolean isGroupToken = TokenMatcher.isGroupToken(stateTokenS);
    final boolean isWaveToken = TokenMatcher.isWaveToken(stateTokenS);
    final String suffix = isGroupToken ? "" : isWaveToken ? "ByWaveRef" : "";

    if (isGroupToken || isWaveToken) {
      // Ok is a token like group.tool.number
      final String getContentUrl = EmbedHelper.getServerWithPath()
          + "cors/ContentCORSService/getContent" + suffix + "?"
          + new UrlParam(JSONConstants.TOKEN_PARAM, URL.encodeQueryString(stateTokenS));

      // FIXME Exception if is not public?

      EmbedHelper.processRequest(getContentUrl, new Callback<Response, Void>() {

        @Override
        public void onFailure(final Void reason) {
          notFound();
        }

        @Override
        public void onSuccess(final Response response) {
          // final StateToken currentToken = new StateToken(currentHash);
          NotifyUser.hideProgress();
          final StateAbstractDTOJs state = JsonUtils.safeEval(response.getText());
          final StateTokenJs stateTokenJs = (StateTokenJs) state.getStateToken();

          // getContent returns the default content if doesn't finds the content
          if ((isGroupToken && stateTokenS.equals(stateTokenJs.getEncoded()))
              || (isWaveToken && stateTokenS.equals(state.getWaveRef()))) {
            onGetContentSuccessful(session, EmbedHelper.parse(state));
          } else {
            // getContent returns def content if content not found
            notFound();
          }
        }
      });

    } else {
      // Do something
      notFound();
    }
  }

  private String getCurrentHistoryHash() {
    return HistoryUtils.undoHashbang(History.getToken());
  }

  private boolean isCurrentHistoryHashValid(final String currentHistoryHash) {
    return currentHistoryHash != null
        && (TokenMatcher.isGroupToken(currentHistoryHash) || TokenMatcher.isWaveToken(currentHistoryHash));
  }

  /**
   * Not found.
   */
  private void notFound() {
    NotifyUser.important(I18n.t("Content not found"));
    NotifyUser.hideProgress();
  }

  private void onAppStarted() {
    // We set the prefix for avatars url with the server url
    clientDownUtils.setPrefix(EmbedConfiguration.get().getServerUrl());

    final String userHash = session.getUserHash();
    Log.info("Started embed presenter with user hash: " + userHash);

    final String initUrl = EmbedHelper.getServerWithPath() + "cors/SiteCORSService/getInitData";

    EmbedHelper.processRequest(initUrl, new Callback<Response, Void>() {
      @Override
      public void onFailure(final Void reason) {
        // Do nothing
        Log.info("Failed to get init data");
      }

      @Override
      public void onSuccess(final Response response) {
        final InitDataDTOJs initData = JsonUtils.safeEval(response.getText());
        session.setInitData(EmbedHelper.parse(initData));
        final UserInfoDTOJs userInfo = (UserInfoDTOJs) initData.getUserInfo();
        session.setUserHash(userInfo.getUserHash());
        session.setCurrentUserInfo(EmbedHelper.parse(userInfo), null);
        final String currentHash = getCurrentHistoryHash();
        final boolean isValid = isCurrentHistoryHashValid(currentHash);
        if (stateTokenToOpen != null) {
          // The open event already received, so open the content
          Log.info("Opening token from JSNI open call");
          getContentFromHistoryHash(stateTokenToOpen);
        } else if (isValid) {
          // We start the embed from the url #hash
          Log.info("Opening token from history token");
          stateTokenToOpen = currentHash;
          getContentFromHistoryHash(currentHash);
        } else {
          // We embed the document via JSNI, so, we wait for the open event
        }
        // We configure sign-out
        session.onUserSignOut(false, new UserSignOutHandler() {
          @Override
          public void onUserSignOut(final UserSignOutEvent event) {
            Log.info("On user sign out");
            if (stateTokenToOpen != null) {
              getContentFromHistoryHash(stateTokenToOpen);
            }
          }
        });
      }
    });
  }

  private void onGetContentSuccessful(final Session session, final StateAbstractDTO state) {
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;

    final boolean isLogged = session.isLogged();
    final boolean isParticipant = stateContent.isParticipant();
    Log.info("Is logged: " + isLogged + " isParticipant: " + isParticipant);
    final Boolean readOnly = EmbedConfiguration.get().getReadOnly();
    Log.info("Is readonly: " + readOnly);
    final Boolean isReadOnly = readOnly == null ? false : readOnly;
    if (isLogged && isParticipant && !isReadOnly) {
      getView().setEditableContent(stateContent);
    } else {
      getView().setContent(stateContent);
    }

    // FIXME use GWTP here?
    GWT.runAsync(new RunAsyncCallback() {
      @Override
      public void onFailure(final Throwable reason) {
        // By now, do nothing
      }

      @Override
      public void onSuccess() {
        sitebar.get().init(stateTokenToOpen);
      }

    });
  }

  @Override
  public void onValueChange(final ValueChangeEvent<String> event) {
    // Only used in dev mode
    if (devMode) {
      getContentFromHistoryHash(getCurrentHistoryHash());
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
