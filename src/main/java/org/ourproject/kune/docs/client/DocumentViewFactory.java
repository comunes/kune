package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderPanel;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationPanel;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;

public class DocumentViewFactory {
    public static DocumentReaderView documentView;
    public static NavigationView navigationView;

    public static DocumentReaderView getDocumentView() {
	if (documentView == null)
	    documentView = new DocumentReaderPanel();
	return documentView;
    }

    public static NavigationView getNavigationtView() {
	if (navigationView == null)
	    navigationView = new NavigationPanel();
	return navigationView;
    }

}
