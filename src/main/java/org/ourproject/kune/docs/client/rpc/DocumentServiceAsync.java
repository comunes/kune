package org.ourproject.kune.docs.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface DocumentServiceAsync {
    void getContext(String userHash, String contextRef, AsyncCallback async);
    void getContent(String userHash, String ctxRef, String docRef, AsyncCallback callback);
}
