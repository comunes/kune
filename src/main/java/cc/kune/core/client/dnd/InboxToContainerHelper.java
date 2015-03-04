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

package cc.kune.core.client.dnd;

import org.waveprotocol.box.webclient.search.CustomDigestDomImpl;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class InboxToContainerHelper {

  private final ContentServiceAsync contentService;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public InboxToContainerHelper(final ContentServiceAsync contentService, final Session session,
      final StateManager stateManager) {
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
  }

  public void publish(final Widget widget, final Object target) {
    try {
      final StateToken destToken = (StateToken) target;
      final CustomDigestDomImpl digest = (CustomDigestDomImpl) widget;
      contentService.publishWave(session.getUserHash(), destToken, digest.getWaveUri(),
          new AsyncCallbackSimple<StateContentDTO>() {
            @Override
            public void onSuccess(final StateContentDTO result) {
              NotifyUser.hideProgress();
              NotifyUser.success(I18n.t("Published"));
              digest.setGroup(result.getGroup());
              digest.makeNotDraggable();
              digest.removeTooltip();
              // contentCache.remove(destToken);
              stateManager.setRetrievedStateAndGo(result);
            }
          });
    } catch (final ClassCastException e) {
      Log.error("Some cast problem in d&d of type:" + widget + " to target: " + target, e);
    }
  }
}
