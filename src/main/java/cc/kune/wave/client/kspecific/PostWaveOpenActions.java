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
import cc.kune.common.client.log.Log;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.polymer.client.PolymerUtils;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The Class PostWaveOpenActions.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PostWaveOpenActions {

  private final ActionFlowPanel bottomToolbar;
  private final ContentServiceAsync contentService;
  private final EventBus eventBus;
  private final Session session;
  private final StateManager stateManager;
  private final Timer timer;
  private WaveClientView webClient;

  @Inject
  public PostWaveOpenActions(final EventBus eventBus, final ContentServiceAsync contentService,
      final Session session, final ActionFlowPanel bottomToolbar, final StateManager stateManager) {
    this.eventBus = eventBus;
    this.contentService = contentService;
    this.session = session;
    // Not used now
    this.bottomToolbar = bottomToolbar;
    this.stateManager = stateManager;
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
        beforeWaveOpen(event.getWaveId());
      }
    });
    eventBus.addHandler(AfterOpenWaveEvent.getType(), new AfterOpenWaveEvent.AfterOpenWaveHandler() {
      @Override
      public void onAfterOpenWave(final AfterOpenWaveEvent event) {
        afterWaveOpen(event.getWaveId());
      }
    });
    timer = new Timer() {
      @Override
      public void run() {
        PolymerUtils.setMainSelected();
        PolymerUtils.setNarrowVisible(false);
      }
    };
  }

  private void afterWaveOpen(final String waveUri) {
    Log.info("After open wave: " + waveUri);

    contentService.getContentByWaveRef(session.getUserHash(), waveUri,
        new AsyncCallbackSimple<StateAbstractDTO>() {
      @Override
      public void onSuccess(final StateAbstractDTO result) {
        if (result instanceof StateContentDTO) {
          final StateContentDTO state = (StateContentDTO) result;
          stateManager.setRetrievedStateAndGo(state);
          SpaceSelectEvent.fire(eventBus, Space.groupSpace);
          // Disabled
          // timer.schedule(4000);
          // webClient.showBottomToolbar();
        } else {
          // webClient.hideBottomToolbar();
          SpaceSelectEvent.fire(eventBus, Space.userSpace);
          if (PolymerUtils.isXSmall() && PolymerUtils.getMainSelected().equals("drawer")) {
            PolymerUtils.setMainSelected();
          }
        }
      }
    });
  }

  private void beforeWaveOpen(final String waveUri) {
    bottomToolbar.clear();
    Log.info("Before open wave: " + waveUri);
  }

}
