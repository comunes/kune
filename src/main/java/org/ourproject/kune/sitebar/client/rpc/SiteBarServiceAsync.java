package org.ourproject.kune.sitebar.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SiteBarServiceAsync {

    void login(String nick, String pass, AsyncCallback callback);

    void logout(AsyncCallback callback);

    void createNewGroup(String shortName, String longName, String publicDesc, int type, AsyncCallback callback);

}
