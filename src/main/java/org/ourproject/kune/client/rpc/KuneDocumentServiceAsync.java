package org.ourproject.kune.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface KuneDocumentServiceAsync {
	public void getRootDocument(String projectName, AsyncCallback callback);
}
