package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.actions.AddDocument;
import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.docs.client.actions.GoParentFolder;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextListener;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class DocumentContextPresenter implements DocumentContext, FolderContextListener {
    private final WorkspaceDeckView view;
    private final Components components;
    private StateDTO content;

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
	this.content = content;
	StateToken state = content.getState();
	FolderContext folderContext = components.getFolderContext();
	folderContext.setFolder(state, content.getFolder(), content.getFolderRights());
	view.show(folderContext.getView());
    }

    public void onAddDocument() {
	Dispatcher.App.instance.fire(AddDocument.KEY, content.getFolder());
    }

    public void onAddFolder() {
	Dispatcher.App.instance.fire(AddFolder.KEY, content.getFolder(), content.getGroup());
    }

    public void onGoParent() {
	Dispatcher.App.instance.fire(GoParentFolder.KEY, content.getFolder(), content);
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
