package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentServiceMocked extends MockedService implements ContentServiceAsync {

    public void getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef, final AsyncCallback callback) {

	ContentDTO content = new ContentDTO();
	FolderDTO folder = new FolderDTO();
	folder.setId(new Long(1));
	content.setFolder(folder);

	GroupDTO group = new GroupDTO();
	group.setShortName("kune");
	content.setGroup(group);

	content.setToolName(DocumentTool.NAME);
	content.setContentRights(new AccessRightsDTO(false, true, true));

	content.setContent("this is the content");

	answer(content, callback);
    }

    public void save(final String userHash, final ContentDTO content, final AsyncCallback asyncCallback) {

    }

    public void addContent(final String user, final Long parentFolderId, final String name,
	    final AsyncCallback asyncCallback) {

    }

}
