package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

    String requestJoinGroup(String hash, String groupShortName);

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
