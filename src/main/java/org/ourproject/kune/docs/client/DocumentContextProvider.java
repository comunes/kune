package org.ourproject.kune.docs.client;

import java.util.ArrayList;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContextProvider implements ContextDataProvider {
    private final DocumentServiceAsync docService;
    private final String userHash;

    public DocumentContextProvider(DocumentServiceAsync docService, String userHash) {
	this.docService = docService;
	this.userHash = userHash;
    }

    public void getContext(String id, final ContextDataAcceptor acceptor) {
	docService.getContext(userHash, id, new AsyncCallback() {
	    public void onFailure(Throwable caugth) {
		acceptor.failed(caugth);
	    }

	    public void onSuccess(Object data) {
		ContextDataDTO ctxData = (ContextDataDTO) data;
		encodeReferences(ctxData);
		acceptor.accept(ctxData);
	    }

	    private void encodeReferences(ContextDataDTO ctxData) {
		ContextItemDTO item;
		ArrayList items = ctxData.getChildren();
		for (int index = 0; index < items.size(); index++) {
		    item = (ContextItemDTO) items.get(index);
		    String newRef = HistoryToken
			    .encode(DocumentTool.NAME, ctxData.getContextRef(), item.getReference());
		    item.setToken(newRef);
		}
	    }

	});
    }
}
