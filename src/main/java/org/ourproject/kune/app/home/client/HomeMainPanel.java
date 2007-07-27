package org.ourproject.kune.app.home.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class HomeMainPanel extends SimplePanel implements HomeMainView {

    public HomeMainPanel() {
        add(new Label("el contenido de home"));
    }
}
