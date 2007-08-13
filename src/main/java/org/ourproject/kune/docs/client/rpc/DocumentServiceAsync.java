package org.ourproject.kune.docs.client.rpc;

import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentServiceAsync {
    void getContext(String userHash, String contextRef, AsyncCallback async);

    void getContent(String userHash, String ctxRef, String docRef, AsyncCallback callback);

    void saveContent(String userHash, ContentDTO contentData, AsyncCallback asyncCallback);
}
