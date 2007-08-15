package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void getContent(String userHash, String groupName, String toolName, String folderRef, String contentRef,
	    AsyncCallback callback);

    void save(String userHash, ContentDTO content, AsyncCallback asyncCallback);

    void addContent(String user, Long parentFolderId, String name, AsyncCallback asyncCallback);
}
