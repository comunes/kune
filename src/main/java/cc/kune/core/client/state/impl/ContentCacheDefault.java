/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.state.impl;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.notify.ProgressShowEvent;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ContentCacheDefault implements ContentCache {
  private final Map<StateToken, StateAbstractDTO> cacheMap;
  private final EventBus eventBus;
  private final ContentServiceAsync server;
  private boolean useCache = false;

  @Inject
  public ContentCacheDefault(final ContentServiceAsync server, final EventBus eventBus,
      final Session session) {
    this.server = server;
    this.eventBus = eventBus;
    this.cacheMap = new HashMap<StateToken, StateAbstractDTO>();
    // Better, don't use while we don't check changes in the server (by others)
    // and we do server pub/sub
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        useCache = event.getInitData().useClientContentCache();
      }
    });
    useCache = true;
    session.onUserSignInOrSignOut(false, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        cacheMap.clear();
      }
    });
  }

  @Override
  public void cache(final StateToken encodeState, final StateAbstractDTO content) {
    assert encodeState != null;
    if (useCache) {
      cacheMap.put(encodeState, content);
    }
  }

  private StateAbstractDTO getCached(final StateToken newState) {
    assert newState != null;
    final StateAbstractDTO cached = cacheMap.get(newState);
    // We use cache always for docs (waves)
    return useCache ? cached : newState.hasAll() ? cached : null;
  }

  @Override
  public void getContent(final String user, final StateToken newState,
      final AsyncCallback<StateAbstractDTO> callback) {
    assert newState != null;
    eventBus.fireEvent(new ProgressShowEvent(""));
    final StateAbstractDTO catched = getCached(newState);
    if (catched != null) {
      callback.onSuccess(catched);
    } else {
      // NotifyUser.info("Getting state of: " + newState, true);
      server.getContent(user, newState, callback);
    }
  }

  @Override
  public void remove(final StateToken token) {
    cacheMap.remove(token);
  }

  @Override
  public void removeCacheOfGroup(final String group) {
    for (final StateToken entry : cacheMap.keySet()) {
      if (entry.getGroup().equals(group)) {
        cacheMap.put(entry, null);
      }
    }
  }

}
