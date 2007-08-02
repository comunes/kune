package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContentProvider implements ContentDataProvider {
    private final DocumentServiceAsync server;
    private final String userHash;

    public DocumentContentProvider(DocumentServiceAsync server, String userHash) {
	this.server = server;
	this.userHash = userHash;
    }

    public void getContent(String ctxRef, String reference, final ContentDataAcceptor acceptor) {
	server.getContent(userHash, ctxRef, reference, new AsyncCallback () {
	    public void onFailure(Throwable arg0) {
	    }

	    public void onSuccess(Object ctxData) {
		acceptor.accept((ContentDataDTO) ctxData);
	    }

	});
    }

}
