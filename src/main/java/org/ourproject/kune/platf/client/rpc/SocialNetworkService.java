package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

    String requestJoinGroup(String hash, String groupShortName) throws SerializableException;

    SocialNetworkResultDTO AcceptJoinGroup(String hash, String groupShortName, String groupToAcceptShortName)
            throws SerializableException;

    SocialNetworkResultDTO deleteMember(String hash, String groupShortName, String groupToDeleteShortName)
            throws SerializableException;

    SocialNetworkResultDTO denyJoinGroup(String hash, String groupShortName, String groupToDenyShortName)
            throws SerializableException;

    SocialNetworkResultDTO unJoinGroup(String hash, String groupShortName) throws SerializableException;

    SocialNetworkResultDTO setCollabAsAdmin(String hash, String groupShortName, String groupToSetAdminShortName)
            throws SerializableException;

    SocialNetworkResultDTO setAdminAsCollab(String hash, String groupShortName, String groupToSetCollabShortName)
            throws SerializableException;

    SocialNetworkResultDTO addAdminMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkResultDTO addCollabMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkResultDTO addViewerMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkDTO getGroupMembers(String hash, String groupShortName) throws SerializableException;

    ParticipationDataDTO getParticipation(String hash, String groupShortName) throws SerializableException;

    public static class App {
        private static SocialNetworkServiceAsync instance;

        public static SocialNetworkServiceAsync getInstance() {
            if (instance == null) {
                instance = (SocialNetworkServiceAsync) GWT.create(SocialNetworkService.class);
                ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "SocialNetworkService");
            }
            return instance;
        }

    }
}
