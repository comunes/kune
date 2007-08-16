package org.ourproject.kune.chat.client.ui.ctx.rooms;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsView;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItemsView view;

    public RoomsAdminPresenter(final ContextItemsView view) {
	this.view = view;
	view.setParentTreeVisible(false);
    }

    public View getView() {
	return view;
    }

}
