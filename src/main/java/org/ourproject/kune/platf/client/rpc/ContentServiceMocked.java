package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.workspace.client.dto.AccessRightsDTO;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentServiceMocked extends MockedService implements ContentServiceAsync {

    public void getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef, final AsyncCallback callback) {

	ContentDTO content = new ContentDTO();
	content.setToolName(DocumentTool.NAME);
	content.setAccessRights(new AccessRightsDTO());
	answer(content, callback);
    }

}
