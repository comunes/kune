/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentProviderImpl implements ContentProvider {

    private final ContentServiceAsync server;
    private final HashMap<StateToken, StateDTO> cache;

    public ContentProviderImpl(final ContentServiceAsync server) {
        this.server = server;
        this.cache = new HashMap<StateToken, StateDTO>();
    }

    public void getContent(final String user, final StateToken newState, final AsyncCallback<StateDTO> callback) {
        Site.showProgressProcessing();
        StateDTO catched = getCached(newState);
        if (catched != null) {
            callback.onSuccess(catched);
        } else {
            server.getContent(user, newState.getGroup(), newState, callback);
        }
    }

    private StateDTO getCached(final StateToken newState) {
        return cache.remove(newState);
    }

    public void cache(final StateToken encodeState, final StateDTO content) {
        cache.put(encodeState, content);
    }

}
