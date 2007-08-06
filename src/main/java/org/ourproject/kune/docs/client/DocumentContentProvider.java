package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContentProvider implements ContentDataProvider {
    private final DocumentServiceAsync server;
    private final String userHash;

    public DocumentContentProvider(final DocumentServiceAsync server, final State state) {
	this.server = server;
	this.userHash = state.user;
    }

    public void getContent(final String ctxRef, final String reference, final ContentDataAcceptor acceptor) {
	server.getContent(userHash, ctxRef, reference, new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
	    }

	    public void onSuccess(final Object ctxData) {
		acceptor.accept((ContentDataDTO) ctxData);
	    }

	});
    }

}
