package org.ourproject.kune.docs.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class NavigationPanel extends HorizontalPanel implements NavigationView {

    public NavigationPanel() {
	add(new Label("this is the nav panel"));
    }
}
