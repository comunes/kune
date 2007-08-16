package org.ourproject.kune.chat.client.ui.ctx.rooms;

import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItems contextItems;

    public RoomsAdminPresenter(final ContextItems contextItems) {
	this.contextItems = contextItems;
	ContextItemsImages images = ContextItemsImages.App.getInstance();
	contextItems.registerType("room", images.page());
	contextItems.canCreate("room", "Add room", AddFolder.EVENT);
    }

    public View getView() {
	return contextItems.getView();
    }

}
