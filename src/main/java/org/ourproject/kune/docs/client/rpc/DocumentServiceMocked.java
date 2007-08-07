package org.ourproject.kune.docs.client.rpc;

import org.ourproject.kune.platf.client.rpc.MockedService;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentServiceMocked extends MockedService implements DocumentServiceAsync {
    public void getContext(final String userHash, final String contextRef, final AsyncCallback async) {
	GWT.log("DOC SERVICE: getContext - " + contextRef, null);
	delay(new Delayer() {
	    public void run() {
		async.onSuccess(createRoot(contextRef));
	    }
	});
    }

    protected ContextDataDTO createRoot(final String contextRef) {
	ContextDataDTO root = new ContextDataDTO(contextRef);
	root.add(new ContextItemDTO("welcome", "file", "welcome"));
	root.add(new ContextItemDTO("second", "file", "second"));
	root.add(new ContextItemDTO("thirth", "file", "thirth"));
	return root;
    }

    public void getContent(final String userHash, final String ctxRef, final String docRef, final AsyncCallback callback) {
	GWT.log("DOC SERVICE: getContent - " + ctxRef + ", " + docRef, null);
	delay(new Delayer() {
	    public void run() {
		callback.onSuccess(new ContentDataDTO(docRef, docRef,
			"<h1>welcome<h1>this is the content from the <b>'server'</b> ;) for " + docRef.toUpperCase()));
	    }
	});
    }

    public void saveContent(final String userHash, final ContentDataDTO contentData, final AsyncCallback asyncCallback) {
	delay(new Delayer() {
	    public void run() {
		asyncCallback.onSuccess(null);
	    }
	});
    }

}
