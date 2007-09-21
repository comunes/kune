package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

    String requestJoinGroup(String hash, String groupShortName) throws SerializableException;

    void AcceptJoinGroup(String hash, String groupToAcceptShortName, String groupShortName)
	    throws SerializableException;

    void deleteMember(String hash, String groupToDeleteShortName, String groupShortName) throws SerializableException;

    void denyJoinGroup(String hash, String groupToDenyShortName, String groupShortName) throws SerializableException;

    void setCollabAsAdmin(String hash, String groupToSetAdminShortName, String groupShortName)
	    throws SerializableException;

    void setAdminAsCollab(String hash, String groupToSetCollabShortName, String groupShortName)
	    throws SerializableException;

    void addAdminMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

    void addCollabMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

    void addViewerMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

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
