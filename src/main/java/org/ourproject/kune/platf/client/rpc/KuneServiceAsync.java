package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface KuneServiceAsync {
    void getDefaultGroup(String userHash, AsyncCallback callback);
}
