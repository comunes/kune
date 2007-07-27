package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class KuneServiceMocked implements KuneServiceAsync {
    public void getDefaultGroup(String userHash, AsyncCallback callback) {
        callback.onSuccess(new GroupDTO());
    }
}
