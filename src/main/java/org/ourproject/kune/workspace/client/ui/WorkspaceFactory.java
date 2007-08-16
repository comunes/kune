package org.ourproject.kune.workspace.client.ui;

import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsPresenter;

public class WorkspaceFactory {

    public static ContextItems createContextItems() {
	ContextItemsPresenter presenter = new ContextItemsPresenter();
	ContextItemsPanel panel = new ContextItemsPanel(presenter);
	presenter.init(panel);
	return presenter;
    }
}
