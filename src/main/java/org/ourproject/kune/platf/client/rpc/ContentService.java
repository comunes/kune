package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContentService extends RemoteService {

    ContentDTO getContent(String userHash, String groupName, String toolName, String folderRef, String contentRef)
	    throws ContentNotFoundException;

    void save(String userHash, ContentDTO dto) throws AccessViolationException;

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
