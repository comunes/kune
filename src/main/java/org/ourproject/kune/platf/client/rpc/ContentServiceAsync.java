package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void getContent(String userHash, String groupName, String toolName, String folderRef, String contentRef,
	    AsyncCallback callback);

}
