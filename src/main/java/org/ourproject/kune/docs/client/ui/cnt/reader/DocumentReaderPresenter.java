package org.ourproject.kune.docs.client.ui.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public class DocumentReaderPresenter implements DocumentReader {
    private final DocumentReaderView view;

    public DocumentReaderPresenter(final DocumentReaderView view) {
	this.view = view;
    }

    public void showDocument(final String text, final AccessRightsDTO accessRights) {
	view.setContent(text);
	view.setEditEnabled(accessRights.isEditable);
    }

    public View getView() {
	return view;
    }
}
