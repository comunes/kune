package org.ourproject.kune.app.home.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HomeServiceAsync {
    void loadHome(String userHash, AsyncCallback asyncCallback);
}
