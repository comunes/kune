/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import cc.kune.core.client.notify.ProgressShowEvent;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;

public class ContentProviderDefault implements ContentProvider {
    private final ContentServiceAsync server;
    private final HashMap<StateToken, StateAbstractDTO> cache;
    private final EventBus eventBus;

    @Inject
    public ContentProviderDefault(final ContentServiceAsync server, final EventBus eventBus) {
        this.server = server;
        this.eventBus = eventBus;
        this.cache = new HashMap<StateToken, StateAbstractDTO>();
    }

    @Override
    public void cache(final StateToken encodeState, final StateAbstractDTO content) {
        cache.put(encodeState, content);
    }

    @Override
    public void getContent(final String user, final StateToken newState, final AsyncCallback<StateAbstractDTO> callback) {
        eventBus.fireEvent(new ProgressShowEvent(""));
        final StateAbstractDTO catched = getCached(newState);
        if (catched != null) {
            callback.onSuccess(catched);
        } else {
            server.getContent(user, newState, callback);
        }
    }

    private StateAbstractDTO getCached(final StateToken newState) {
        return cache.remove(newState);
    }

}
