package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

    void requestJoinGroup(String hash, String groupShortName, AsyncCallback callback);

    void AcceptJoinGroup(String hash, String groupShortName, AsyncCallback callback);

    void deleteMember(String hash, String groupShortName, AsyncCallback callback);

    void denyJoinGroup(String hash, String groupShortName, AsyncCallback callback);

}
