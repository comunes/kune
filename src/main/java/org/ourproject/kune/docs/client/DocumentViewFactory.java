package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.workspace.navigation.NavigationPanel;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;

public class DocumentViewFactory {
    public static DocumentView documentView;
    public static NavigationView navigationView;

    public static DocumentView getDocumentView() {
	if (documentView == null)
	    documentView = new DocumentPanel();
	return documentView;
    }

    public static NavigationView getNavigationtView() {
	if (navigationView == null)
	    navigationView = new NavigationPanel();
	return navigationView;
    }

}
