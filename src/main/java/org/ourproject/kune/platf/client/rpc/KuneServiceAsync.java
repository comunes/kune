package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface KuneServiceAsync {

    void getDefaultGroup(String userHash, AsyncCallback callback);

    void createNewGroup(GroupDTO group, AsyncCallback callback);

    void getAllLicenses(AsyncCallback callback);

    void getNotCCLicenses(AsyncCallback callback);

    void getGroup(String userHash, String shortName, AsyncCallback asyncCallback);

}
