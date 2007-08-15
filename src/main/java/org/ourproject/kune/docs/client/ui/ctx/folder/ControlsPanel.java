package org.ourproject.kune.docs.client.ui.ctx.folder;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

class ControlsPanel extends VerticalPanel {
    private final Button btnAddDocument;
    private final Button btnAddFolder;

    public ControlsPanel() {
	btnAddDocument = new Button("add document");
	btnAddFolder = new Button("add folder");
	add(btnAddDocument);
	add(btnAddFolder);
    }

    public void setVisibleControls(final boolean isAddDocumentVisible, final boolean isAddFolderVisible) {
	btnAddDocument.setVisible(isAddDocumentVisible);
	btnAddFolder.setVisible(isAddFolderVisible);
    }
}
