package org.ourproject.kune.sitebar.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SiteBarServiceAsync {

    void login(String nick, String pass, AsyncCallback callback);

    void logout(AsyncCallback callback);

    void createNewGroup(GroupDTO group, AsyncCallback callback);

    void getAllLicenses(AsyncCallback callback);
}
