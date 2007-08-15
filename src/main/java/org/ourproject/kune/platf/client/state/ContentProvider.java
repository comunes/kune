package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentProvider {
    void getContent(String user, StateToken newState, AsyncCallback callback);

    void cache(StateToken encodeState, ContentDTO content);
}
