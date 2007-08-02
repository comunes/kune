package org.ourproject.kune.docs.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocumentPanel extends VerticalPanel implements DocumentView {

    public DocumentPanel() {
	add(new Label("this is the doc panel"));
    }
}
