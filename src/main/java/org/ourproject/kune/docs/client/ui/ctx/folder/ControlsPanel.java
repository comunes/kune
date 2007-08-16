package org.ourproject.kune.docs.client.ui.ctx.folder;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class ControlsPanel extends VerticalPanel {
    private final Button btnAddDocument;
    private final Button btnAddFolder;

    public ControlsPanel(final FolderContextListener listener) {
	btnAddDocument = new Button("add document", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onAddDocument();
	    }
	});
	btnAddFolder = new Button("add folder", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onAddFolder();
	    }
	});
	add(btnAddDocument);
	add(btnAddFolder);
    }

}
