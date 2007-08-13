package org.ourproject.kune.workspace.client.component;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspaceDeckPanel extends SimplePanel implements WorkspaceDeckView {
    Widget current;

    public WorkspaceDeckPanel() {
	current = null;
    }

    public void show(final View view) {
	if (current != null) {
	    remove(current);
	}
	current = (Widget) view;
	add(current);
    }

}
