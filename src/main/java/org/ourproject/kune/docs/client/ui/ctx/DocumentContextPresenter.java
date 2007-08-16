package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class DocumentContextPresenter implements DocumentContext {
    private final WorkspaceDeckView view;
    private final Components components;

    public DocumentContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
	this.components = new Components(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setContent(final StateDTO content) {
	StateToken state = content.getState();
	FolderContext folderContext = components.getFolderContext();
	folderContext.setContainer(state, content.getFolder(), content.getFolderRights());
	view.show(folderContext.getView());
    }

    public void showAdmin() {
	AdminContext adminContext = components.getAdminContext();
	view.show(adminContext.getView());
    }

    public void showFolders() {
	FolderContext folderContext = components.getFolderContext();
	view.show(folderContext.getView());
    }
}
