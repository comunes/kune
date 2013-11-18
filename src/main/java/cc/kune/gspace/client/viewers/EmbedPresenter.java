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
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.wave.client.kspecific.WaveClientManager;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

@Singleton
public class EmbedPresenter extends Presenter<EmbedPresenter.EmbedView, EmbedPresenter.EmbedProxy> {

  @ProxyStandard
  public interface EmbedProxy extends Proxy<EmbedPresenter> {
  }

  public interface EmbedView extends WaveViewerView {
  }

  @Inject
  public EmbedPresenter(final EventBus eventBus, final EmbedView view, final EmbedProxy proxy,
      final SiteServiceAsync siteService, final ContentServiceAsync service,
      final WaveClientManager waveClientManager, final WaveClientProvider waveClient,
      final TokenMatcher matcher, final Session session) {
    super(eventBus, view, proxy);
    matcher.init(GwtWaverefEncoder.INSTANCE);
    final String currentHash = HistoryUtils.undoHashbang(History.getToken());
    if (matcher.isGroupToken(currentHash)) {
      siteService.getInitData(session.getUserHash(), new AsyncCallbackSimple<InitDataDTO>() {
        @Override
        public void onSuccess(final InitDataDTO initData) {
          session.setInitData(initData);
          session.setCurrentUserInfo(initData.getUserInfo(), null);

          // Ok is a token like group.tool.number
          final StateToken currentToken = new StateToken(currentHash);
          service.getContent(session.getUserHash(), currentToken,
              new AsyncCallbackSimple<StateAbstractDTO>() {
                @Override
                public void onSuccess(final StateAbstractDTO state) {
                  NotifyUser.hideProgress();
                  if (state.getStateToken().equals(currentToken)) {
                    getView().clear();
                    final StateContentDTO stateContent = (StateContentDTO) state;
                    final AccessRights rights = stateContent.getContentRights();
                    Log.info("Content rights: " + rights);
                    if (session.isLogged() && rights.isEditable()) {
                      getView().setEditableContent(stateContent);
                    } else {
                      getView().setContent(stateContent);
                    }
                  } else {
                    // getContent returns def content if content not found
                    notFound();
                  }
                }
              });
        }
      });
    } else {
      // Do something
      notFound();
    }
  }

  private void notFound() {
    NotifyUser.important(I18n.t("Content not found"));
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}
