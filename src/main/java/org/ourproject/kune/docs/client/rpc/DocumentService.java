package org.ourproject.kune.docs.client.rpc;

import org.ourproject.kune.workspace.client.dto.ContentDTO;
import org.ourproject.kune.workspace.client.dto.ContextDataDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DocumentService extends RemoteService {
    public ContextDataDTO getContext(String userHash, String contextRef);

    public ContentDTO getContent(String userHash, String ctxRef, String docRef);

    // TODO: cambiar lo que devuelve
    String saveContent(String userHash, ContentDTO contentData);

    public class App {
	private static DocumentServiceAsync ourInstance = null;

	public static synchronized DocumentServiceAsync getInstance() {
	    if (ourInstance == null) {
		ourInstance = (DocumentServiceAsync) GWT.create(DocumentService.class);
		((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneDocumentService");
	    }
	    return ourInstance;
	}

	public static void setMock(final DocumentServiceAsync mock) {
	    ourInstance = mock;
	}
    }
}
