package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentProviderImpl implements ContentProvider {

    private final ContentServiceAsync server;
    private final HashMap cache;

    public ContentProviderImpl(final ContentServiceAsync server) {
	this.server = server;
	this.cache = new HashMap();
    }

    public void getContent(final String user, final StateToken newState, final AsyncCallback callback) {
	ContentDTO catched = getCached(newState);
	if (catched != null) {
	    callback.onSuccess(catched);
	} else {
	    server.getContent(user, newState, callback);
	}
    }

    private ContentDTO getCached(final StateToken newState) {
	return (ContentDTO) cache.remove(newState);
    }

    public void cache(final StateToken encodeState, final ContentDTO content) {
	cache.put(encodeState, content);
    }

}
