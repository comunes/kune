package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContextProvider implements ContextDataProvider{
    private final DocumentServiceAsync docService;
    private final String userHash;

    public DocumentContextProvider(DocumentServiceAsync docService, String userHash) {
	this.docService = docService;
	this.userHash = userHash;
    }

    public void getContext(String id, final ContextDataAcceptor acceptor) {
	docService.getContext(userHash, id, new AsyncCallback () {
	    public void onFailure(Throwable caugth) {
		acceptor.failed(caugth);
	    }

	    public void onSuccess(Object ctxData) {
		acceptor.accept((ContextDataDTO) ctxData);
	    }

	});
    }
}
