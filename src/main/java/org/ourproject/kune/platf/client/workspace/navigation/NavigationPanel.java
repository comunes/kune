package org.ourproject.kune.platf.client.workspace.navigation;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class NavigationPanel extends HorizontalPanel implements NavigationView {

    public NavigationPanel(NavigationListener listener) {
	add(new Label("this is the nav panel"));
    }
}
