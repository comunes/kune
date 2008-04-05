
package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.sitebar.Site;

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
