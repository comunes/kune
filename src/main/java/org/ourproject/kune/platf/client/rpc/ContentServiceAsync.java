package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void addContent(String user, Long parentFolderId, String name, AsyncCallback callback);

    void getContent(String user, StateToken newState, AsyncCallback callback);

    void addFolder(String hash, String groupShortName, Long parentFolderId, String title, AsyncCallback callback);

    void save(String user, String documentId, String content, AsyncCallback asyncCallback);
}
