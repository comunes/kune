package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentServiceMocked extends MockedService implements ContentServiceAsync {

    public void getContent(final String user, final StateToken newState, final AsyncCallback callback) {
	StateDTO content = new StateDTO();
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

    public void save(final String user, final String documentId, final String content, final AsyncCallback asyncCallback) {

    }

    public void addContent(final String user, final Long parentFolderId, final String name,
	    final AsyncCallback asyncCallback) {

    }

    public void addFolder(final String hash, final String groupShortName, final Long parentFolderId,
	    final String title, final AsyncCallback callback) {
    }

}
