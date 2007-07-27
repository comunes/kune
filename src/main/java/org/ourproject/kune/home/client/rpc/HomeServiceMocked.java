package org.ourproject.kune.home.client.rpc;

import org.ourproject.kune.home.client.Home;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class HomeServiceMocked implements HomeServiceAsync{

    public void loadHome(String userHash, AsyncCallback asyncCallback) {
        asyncCallback.onSuccess(new Home());
    }
 
}
