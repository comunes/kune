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
package cc.kune.core.client.state;

import java.util.HashMap;
import java.util.Map;

import cc.kune.core.client.notify.spiner.ProgressShowEvent;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ContentCacheDefault implements ContentCache {
  private final Map<StateToken, StateAbstractDTO> cacheMap;
  private final EventBus eventBus;
  private final ContentServiceAsync server;
  private final boolean useCache;

  @Inject
  public ContentCacheDefault(final ContentServiceAsync server, final EventBus eventBus,
      final Session session) {
    this.server = server;
    this.eventBus = eventBus;
    this.cacheMap = new HashMap<StateToken, StateAbstractDTO>();
    // Don't use while we don't check changes in the server
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

  @Override
  public void clearCacheOfGroup(final String group) {
    for (final StateToken entry : cacheMap.keySet()) {
      if (entry.getGroup().equals(group)) {
        cacheMap.remove(entry);
      }
    }
  }

  private StateAbstractDTO getCached(final StateToken newState) {
    assert newState != null;
    return useCache ? cacheMap.get(newState) : null;
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
  public void removeContent(final StateToken token) {
    cacheMap.remove(token);
  }

}
