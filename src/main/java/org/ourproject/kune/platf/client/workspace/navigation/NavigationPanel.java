package org.ourproject.kune.platf.client.workspace.navigation;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationPanel extends VerticalPanel implements NavigationView {

    public NavigationPanel(NavigationListener listener) {
	add(new Label("this is the nav panel"));
    }

    public void add(String name, String type, String event) {
	add(new Hyperlink("[" + type + "] " + name, event));
    }
}
