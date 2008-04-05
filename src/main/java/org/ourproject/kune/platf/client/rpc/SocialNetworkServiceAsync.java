package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

    void requestJoinGroup(String hash, String groupShortName, AsyncCallback<?> callback);

    void AcceptJoinGroup(String hash, String groupShortName, String groupToAcceptShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void deleteMember(String hash, String groupShortName, String groupToDeleteShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void denyJoinGroup(String hash, String groupShortName, String groupToDenyShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void unJoinGroup(String hash, String groupShortName, AsyncCallback<SocialNetworkResultDTO> callback);

    void setCollabAsAdmin(String hash, String groupShortName, String groupToSetAdminShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void setAdminAsCollab(String hash, String groupShortName, String groupToSetCollabShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addAdminMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addCollabMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addViewerMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void getGroupMembers(String hash, String groupShortName, AsyncCallback<SocialNetworkResultDTO> callback);

    void getParticipation(String hash, String groupShortName, AsyncCallback<ParticipationDataDTO> callback);
}
