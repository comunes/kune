package org.ourproject.kune.docs.client.ui.ctx.admin;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminContextPanel extends VerticalPanel implements AdminContextView {
    public AdminContextPanel() {
	add(new Label("esto es la administración del documento"));
    }
}
