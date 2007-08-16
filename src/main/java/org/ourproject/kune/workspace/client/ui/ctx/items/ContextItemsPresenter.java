package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Action;

public class ContextItemsPresenter implements ContextItems {
    protected final ContextItemsView view;

    public ContextItemsPresenter(final ContextItemsView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void addGoAction(final Action action) {

    }
}
