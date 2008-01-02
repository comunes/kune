package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.services.KuneErrorHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackSimple implements AsyncCallback {

    public void onFailure(final Throwable caught) {
        KuneErrorHandler.getInstance().process(caught);
    }

}
