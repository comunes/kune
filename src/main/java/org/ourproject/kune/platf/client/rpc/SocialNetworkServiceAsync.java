package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

    void requestJoinGroup(String hash, String groupShortName, AsyncCallback callback);

    void AcceptJoinGroup(String hash, String groupToAcceptShortName, String groupShortName, AsyncCallback callback);

    void deleteMember(String hash, String groupToDeleteShortName, String groupShortName, AsyncCallback callback);

    void denyJoinGroup(String hash, String groupToDenyShortName, String groupShortName, AsyncCallback callback);

    void unJoinGroup(String hash, String groupToUnJoinShortName, String groupShortName, AsyncCallback callback);

    void setCollabAsAdmin(String hash, String groupToSetAdminShortName, String groupShortName, AsyncCallback callback);

    void setAdminAsCollab(String hash, String groupToSetCollabShortName, String groupShortName, AsyncCallback callback);

    void addAdminMember(String hash, String groupToAddShortName, String groupShortName, AsyncCallback callback);

    void addCollabMember(String hash, String groupToAddShortName, String groupShortName, AsyncCallback callback);

    void addViewerMember(String hash, String groupToAddShortName, String groupShortName, AsyncCallback callback);

    void getGroupMembers(String hash, String groupShortName, AsyncCallback callback);

    void getParticipation(String hash, String groupShortName, AsyncCallback callback);
}
