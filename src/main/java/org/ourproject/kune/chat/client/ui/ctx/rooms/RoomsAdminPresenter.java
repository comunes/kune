package org.ourproject.kune.chat.client.ui.ctx.rooms;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItems contextItems;

    public RoomsAdminPresenter(final ContextItems contextItems) {
	this.contextItems = contextItems;
	ContextItemsImages images = ContextItemsImages.App.getInstance();
	contextItems.registerType(ChatClientTool.TYPE_CHAT, images.page());
	contextItems.registerType(ChatClientTool.TYPE_ROOM, images.bulletArrowRight());
	contextItems.canCreate(ChatClientTool.TYPE_ROOM, "Add room", AddFolder.EVENT);
    }

    public void showRoom(final StateToken token, final ContainerDTO container, final AccessRightsDTO rights) {
	contextItems.showContainer(token, container, rights);
    }

    public View getView() {
	return contextItems.getView();
    }

}
