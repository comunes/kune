package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParamCallback<K, T> {

    K param;
    AsyncCallback<T> callback;

    public ParamCallback(final K param, final AsyncCallback<T> callback) {
        this.param = param;
        this.callback = callback;
    }

    public K getParam() {
        return param;
    }

    public AsyncCallback<T> getCallback() {
        return callback;
    }

}
