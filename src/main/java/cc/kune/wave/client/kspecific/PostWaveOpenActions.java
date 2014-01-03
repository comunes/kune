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

package cc.kune.wave.client.kspecific;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.PathToolbarUtils;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * The Class PostWaveOpenActions.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PostWaveOpenActions {

  private final ActionFlowPanel bottomToolbar;
  private final ContentServiceAsync contentService;
  private final PathToolbarUtils pathToolbaUtils;
  private final Session session;
  private WaveClientView webClient;

  @Inject
  public PostWaveOpenActions(final EventBus eventBus, final ContentServiceAsync contentService,
      final Session session, final PathToolbarUtils pathToolbaUtils, final ActionFlowPanel bottomToolbar) {
    this.contentService = contentService;
    this.session = session;
    this.pathToolbaUtils = pathToolbaUtils;
    this.bottomToolbar = bottomToolbar;
    eventBus.addHandler(OnWaveClientStartEvent.getType(),
        new OnWaveClientStartEvent.OnWaveClientStartHandler() {
          @Override
          public void onOnWaveClientStart(final OnWaveClientStartEvent event) {
            webClient = event.getView();
            webClient.addToBottonBar(bottomToolbar);
          }
        });
    eventBus.addHandler(BeforeOpenWaveEvent.getType(), new BeforeOpenWaveEvent.BeforeOpenWaveHandler() {
      @Override
      public void onBeforeOpenWave(final BeforeOpenWaveEvent event) {
        beforeWaveOpen();
      }
    });
    eventBus.addHandler(AfterOpenWaveEvent.getType(), new AfterOpenWaveEvent.AfterOpenWaveHandler() {
      @Override
      public void onAfterOpenWave(final AfterOpenWaveEvent event) {
        afterWaveOpen(event.getWaveId());
      }
    });
  }

  private void afterWaveOpen(final String waveUri) {

    contentService.getContentByWaveRef(session.getUserHash(), waveUri,
        new AsyncCallbackSimple<StateAbstractDTO>() {
          @Override
          public void onSuccess(final StateAbstractDTO result) {
            if (result instanceof StateContentDTO) {
              final StateContentDTO state = (StateContentDTO) result;
              final ContainerSimpleDTO doc = new ContainerSimpleDTO(state.getTitle(),
                  state.getContainer().getStateToken(), state.getStateToken(), state.getTypeId());
              final GuiActionDescCollection actions = pathToolbaUtils.createPath(state.getGroup(),
                  state.getContainer(), false, true, doc);
              bottomToolbar.addAll(actions);
              webClient.showBottomToolbar();
            } else {
              webClient.hideBottomToolbar();
            }
          }
        });
  }

  private void beforeWaveOpen() {
    bottomToolbar.clear();
  }

}
