package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void save(String userHash, ContentDTO content, AsyncCallback callback);

    void addContent(String user, Long parentFolderId, String name, AsyncCallback callback);

    void getContent(String user, StateToken newState, AsyncCallback callback);

    void addFolder(String hash, String groupShortName, Long parentFolderId, String title, AsyncCallback callback);
}
