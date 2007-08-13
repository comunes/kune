package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public class DocumentContextPresenter implements DocumentContext {

    private final WorkspaceDeckView view;

    public DocumentContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setContent(final ContentDTO content) {
	FolderContext folderContext = DocumentFactory.getFolderContext();
	view.show(folderContext.getView());
    }

}
