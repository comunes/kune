package org.ourproject.kune.docs.client.ui.cnt;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public class DocumentContentPresenter implements DocumentContent {
    private final WorkspaceDeckView view;

    public DocumentContentPresenter(final WorkspaceDeckView view) {
	this.view = view;
    }

    public void setContent(final ContentDTO content) {
	DocumentReader reader = DocumentFactory.createDocumentReader();
	reader.showDocument(content.getText(), content.getAccessRights());
	view.show(reader.getView());
    }

    public void attach() {
    }

    public void detach() {

    }

    public View getView() {
	return view;
    }

}
