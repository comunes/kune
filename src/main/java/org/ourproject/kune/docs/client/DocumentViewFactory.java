package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.folder.NavigationPanel;
import org.ourproject.kune.docs.client.folder.NavigationView;
import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderPanel;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;

public class DocumentViewFactory {
    public static DocumentReaderView documentReaderView;
    public static NavigationView navigationView;
    public static DocumentPanel documentView;

    public static DocumentReaderView getDocumentReaderView(final DocumentReaderListener listener) {
	if (documentReaderView == null) {
	    documentReaderView = new DocumentReaderPanel(listener);
	}
	return documentReaderView;
    }

    public static NavigationView getNavigationtView() {
	if (navigationView == null) {
	    navigationView = new NavigationPanel();
	}
	return navigationView;
    }

    public static DocumentView getDocumentView() {
	if (documentView == null) {
	    documentView = new DocumentPanel();
	}
	return documentView;
    }

}
