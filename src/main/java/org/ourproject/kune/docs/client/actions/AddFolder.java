package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

public class AddFolder extends WorkspaceAction {
    public static final String KEY = "docs.addFolder";

    public void execute(final Object value) {
	showNewFolderDialog((FolderDTO) value);
    }

    private void showNewFolderDialog(final FolderDTO value) {

    }

}
