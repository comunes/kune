package org.ourproject.kune.docs.client;

import java.util.ArrayList;

import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.workspace.ContextDataProvider;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentContextProvider implements ContextDataProvider {
    private final DocumentServiceAsync docService;
    private final State state;

    public DocumentContextProvider(DocumentServiceAsync docService, State state) {
	this.docService = docService;
	this.state = state;
    }

    public void getContext(String id, final ContextDataAcceptor acceptor) {
	docService.getContext(state.user, id, new AsyncCallback() {
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
		    String newRef = state.encode(DocumentTool.NAME, ctxData.getContextRef(), item.getReference());
		    item.setToken(newRef);
		}
	    }

	});
    }
}
