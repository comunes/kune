package org.ourproject.kune.platf.client.extend;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentProvider {
    void getContentTree(String userHash, AsyncCallback callback);
}
