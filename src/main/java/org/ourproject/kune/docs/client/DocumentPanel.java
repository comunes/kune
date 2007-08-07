package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentPanel extends SimplePanel implements DocumentView {
    private Widget current;

    public DocumentPanel() {
	current = null;
    }

    public void show(final View view) {
	if (current != null) {
	    this.remove(current);
	}
	current = (Widget) view;
	this.add(current);
    }

}
