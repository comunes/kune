package org.ourproject.kune.docs.client.rpc;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentServiceMocked implements DocumentServiceAsync {

    public void getContext(String userHash, String contextRef, final AsyncCallback async) {
	GWT.log("DOC SERVICE: getContext - " + contextRef, null);
	delay(new Timer() {
	    public void run() {
		async.onSuccess(createRoot());
	    }
	});
    }

    protected ContextDataDTO createRoot() {
	ContextDataDTO root = new ContextDataDTO();
	ArrayList list = new ArrayList();
	list.add(new ContextItemDTO("welcome", "file", "welcome"));
	list.add(new ContextItemDTO("second", "file", "second"));
	list.add(new ContextItemDTO("thirth", "file", "thirth"));
	root.setItems(list);
	return root;
    }

    private void delay(Timer timer) {
	timer.schedule(1000);
    }
}
