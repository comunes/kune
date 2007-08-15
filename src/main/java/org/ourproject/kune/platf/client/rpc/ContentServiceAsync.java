package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void save(String userHash, ContentDTO content, AsyncCallback asyncCallback);

    void addContent(String user, Long parentFolderId, String name, AsyncCallback asyncCallback);

    void getContent(String user, StateToken newState, AsyncCallback asyncCallback);
}
