/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.wave.client.KuneWaveProfileManager;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

public class WaveClientManager {
  private WaveClientView webClient;

  @Inject
  public WaveClientManager(final Session session, final StateManager stateManager,
      final EventBus eventBus, final UserServiceAsync userService, final GSpaceArmor wsArmor,
      final KuneWaveProfileManager profiles, final InboxCountPresenter inboxCount,
      final TokenMatcher tokenMatcher, final WaveClientProvider webclientView) {
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
                    final ForIsWidget userSpace = wsArmor.getUserSpace();
                    if (webClient == null) {
                      if (userSpace.getWidgetCount() > 0) {
                        userSpace.remove(0);
                      }
                      webClient = webclientView.get();
                      userSpace.add(webClient);
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

  public WaveClientView getWebClient() {
    return webClient;
  }

  private native void setClientFlags(JavaScriptObject object) /*-{
    $wnd.__client_flags = object;
  }-*/;

  private native void setSessionJSON(JavaScriptObject object) /*-{
    $wnd.__session = object;
  }-*/;

  private native void setWebsocketAddress(String object) /*-{
    $wnd.__websocket_address = object;
  }-*/;

}
