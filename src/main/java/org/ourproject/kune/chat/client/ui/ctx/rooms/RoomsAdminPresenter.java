package org.ourproject.kune.chat.client.ui.ctx.rooms;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItems contextItems;

    public RoomsAdminPresenter(final ContextItems contextItems) {
	this.contextItems = contextItems;
    }

    public View getView() {
	return contextItems.getView();
    }

}
