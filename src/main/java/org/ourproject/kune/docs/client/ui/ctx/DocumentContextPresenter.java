package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public class DocumentContextPresenter implements DocumentContext {
    private final WorkspaceDeckView view;
    private final Components components;

    public DocumentContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
	this.components = new Components();
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setContent(final ContentDTO content) {
	FolderContext folderContext = components.getFolderContext();
	folderContext.setFolder(content.getFolder());
	view.show(folderContext.getView());
    }

}
