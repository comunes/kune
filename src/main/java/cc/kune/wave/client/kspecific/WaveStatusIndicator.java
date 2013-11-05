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

import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEvent;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEventHandler;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.sn.actions.SessionAction;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

public class WaveStatusIndicator {

  public static class WaveStatusAction extends SessionAction {

    @Inject
    public WaveStatusAction(final Session session, final I18nTranslationService i18n) {
      super(session, true);
      setVisible(false);
      ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {
        private void goOnline() {
          putValue(Action.NAME, ""); // i18n.t("Online"));
          putValue(AbstractAction.STYLES, "k-sitebar-wave-status, k-sitebar-wave-status-online");
          setVisible(false);
          NotifyUser.hideProgress();
        }

        @Override
        public void onNetworkStatus(final NetworkStatusEvent event) {
          switch (event.getStatus()) {
          case CONNECTED:
            goOnline();
            break;
          case RECONNECTED:
            session.check(new AsyncCallbackSimple<Void>() {
              @Override
              public void onSuccess(final Void result) {
                goOnline();
              }
            });
            break;
          case DISCONNECTED:
            if (session.isLogged()) {
              // FIXME: this is because we don't have way to logout in wave,
              // websocket, etc
              NotifyUser.showProgress(i18n.t("Connecting"));
            }
            break;
          case RECONNECTING:
            NotifyUser.showProgress(i18n.t("Offline"));
            // putValue(Action.NAME, i18n.t("Offline"));
            putValue(AbstractAction.STYLES, "k-sitebar-wave-status, k-sitebar-wave-status-offline");
            setVisible(true);
            break;
          }
        }
      });
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      // Do nothing
    }

  }

  @Inject
  public WaveStatusIndicator(final WaveStatusAction action) {
    final IconLabelDescriptor status = new IconLabelDescriptor(action);
    status.setPosition(0);
    SitebarActions.RIGHT_TOOLBAR.add(status);
  }
}
