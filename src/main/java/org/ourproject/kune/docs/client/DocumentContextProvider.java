package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider;

public class DocumentContextProvider implements ContextDataProvider{
    private final DocumentServiceAsync docService;

    public DocumentContextProvider(DocumentServiceAsync docService) {
	this.docService = docService;
    }

    public void getContext(String id, ContextDataAcceptor acceptor) {
    }
}
