package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContentService extends RemoteService {

    ContentDTO getContent(String userHash, StateToken token) throws ContentNotFoundException;

    int save(String user, String documentId, String content) throws AccessViolationException;

    ContentDTO addContent(String user, Long parentFolderId, String name);

    ContentDTO addFolder(String hash, String groupShortName, Long parentFolderId, String title)
	    throws ContentNotFoundException;

    public static class App {
	private static ContentServiceAsync instance;

	public static ContentServiceAsync getInstance() {
	    if (instance == null) {
		instance = (ContentServiceAsync) GWT.create(ContentService.class);
		((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "ContentService");

	    }
	    return instance;
	}

	public static void setMock(final ContentServiceMocked mock) {
	    instance = mock;
	}
    }

}
