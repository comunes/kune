package org.ourproject.kune.docs.client.rpc;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.rpc.MockedService;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentServiceMocked extends MockedService implements DocumentServiceAsync {
    public void getContext(String userHash, final String contextRef, final AsyncCallback async) {
	GWT.log("DOC SERVICE: getContext - " + contextRef, null);
	delay(new Delayer() {
	    public void run() {
		async.onSuccess(createRoot(contextRef));
	    }
	});
    }

    protected ContextDataDTO createRoot(String contextRef) {
	ContextDataDTO root = new ContextDataDTO(contextRef);
	ArrayList list = new ArrayList();
	list.add(new ContextItemDTO("welcome", "file", "welcome"));
	list.add(new ContextItemDTO("second", "file", "second"));
	list.add(new ContextItemDTO("thirth", "file", "thirth"));
	root.setItems(list);
	return root;
    }

    public void getContent(String userHash, String ctxRef, final String docRef, final AsyncCallback callback) {
	GWT.log("DOC SERVICE: getContent - " + ctxRef + ", " + docRef, null);
	delay(new Delayer() {
	    public void run() {
		callback.onSuccess(new ContentDataDTO(docRef, "this is the content from the 'server' ;) for "
			+ docRef.toUpperCase()));
	    }
	});
    }


}
