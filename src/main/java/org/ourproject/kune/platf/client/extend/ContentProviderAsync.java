package org.ourproject.kune.platf.client.extend;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentProviderAsync {
    void getContentTree(String userHash, AsyncCallback callback);
}
