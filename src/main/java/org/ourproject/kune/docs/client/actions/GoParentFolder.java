package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public class GoParentFolder extends WorkspaceAction {
    public static final String KEY = "docs.goParent";

    public void execute(final Object value, final Object extra) {
	goParent((FolderDTO) value, (ContentDTO) extra);
    }

    private void goParent(final FolderDTO folder, final ContentDTO content) {
	StateToken state = content.getState();
	state.setDocument(null);
	state.setFolder(folder.getParentFolderId().toString());
	stateManager.setState(state);
    }

}
