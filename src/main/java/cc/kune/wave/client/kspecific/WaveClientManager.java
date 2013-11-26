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
package cc.kune.wave.client.kspecific;

import cc.kune.common.client.log.Log;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.wave.client.KuneWaveProfileManager;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

/**
 * The Class WaveClientManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveClientManager {

  /** The web client. */
  private WaveClientView webClient;

  /**
   * Instantiates a new wave client manager.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param eventBus
   *          the event bus
   * @param userService
   *          the user service
   * @param wsArmor
   *          the ws armor
   * @param profiles
   *          the profiles
   * @param inboxCount
   *          the inbox count
   * @param webclientView
   *          the webclient view
   */
  @Inject
  public WaveClientManager(final Session session, final EventBus eventBus,
      final UserServiceAsync userService, final HasWaveContainer panel,
      final KuneWaveProfileManager profiles, final WaveClientProvider webclientView) {
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        userService.getWaveClientParameters(session.getUserHash(),
            new AsyncCallbackSimple<WaveClientParams>() {
              @Override
              public void onSuccess(final WaveClientParams result) {
                setSessionJSON(JsonUtils.safeEval(result.getSessionJSON()));
                setClientFlags(JsonUtils.safeEval(result.getClientFlags()));
                setWebsocketAddress(result.getWebsocketAddress());
                Log.info("Wave client session: " + result.getSessionJSON());
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                  @Override
                  public void execute() {
                    final ForIsWidget waveContainer = panel.getForIsWidget();
                    if (webClient == null) {
                      if (waveContainer.getWidgetCount() > 0) {
                        waveContainer.remove(0);
                      }
                      webClient = webclientView.get();
                      waveContainer.add(webClient);
                    } else {
                      // this is done with the first webclient creation above
                      webClient.login();
                      webClient.asWidget().setVisible(true);
                    }
                  }
                });
              }
            });
      }
    });
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        if (webClient != null) {
          webClient.asWidget().setVisible(false);
          webClient.logout();
        }
        setSessionJSON(JsonUtils.safeEval("{}"));
        setClientFlags(JsonUtils.safeEval("{}"));
      }
    });
  }

  /**
   * Gets the web client.
   * 
   * @return the web client
   */
  public WaveClientView getWebClient() {
    return webClient;
  }

  /**
   * Sets the client flags.
   * 
   * @param object
   *          the new client flags
   */
  private native void setClientFlags(JavaScriptObject object) /*-{
		$wnd.__client_flags = object;
  }-*/;

  /**
   * Sets the session json.
   * 
   * @param object
   *          the new session json
   */
  private native void setSessionJSON(JavaScriptObject object) /*-{
		$wnd.__session = object;
  }-*/;

  /**
   * Sets the websocket address.
   * 
   * @param object
   *          the new websocket address
   */
  private native void setWebsocketAddress(String object) /*-{
		$wnd.__websocket_address = object;
  }-*/;

}
