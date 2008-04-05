
package org.ourproject.kune.chat.client.ctx.rooms;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.ChatEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItems contextItems;

    public RoomsAdminPresenter(final ContextItems contextItems) {
        this.contextItems = contextItems;
        ContextItemsImages images = ContextItemsImages.App.getInstance();
        contextItems.setParentTreeVisible(false);
        contextItems.registerType(ChatClientTool.TYPE_CHAT, images.page());
        contextItems.registerType(ChatClientTool.TYPE_ROOM, images.chatGreen());
        contextItems.canCreate(ChatClientTool.TYPE_ROOM, Kune.I18N.t("New chat room"), ChatEvents.ADD_ROOM);
    }

    // FIXME: cierta lógica de negocio en el cliente
    // ¿debemos quitarla? es decir, enviar desde el servidor si se puede añadir
    // hijos al contenedor
    public void showRoom(final StateToken token, final ContainerDTO container, final AccessRightsDTO rights) {
        contextItems.showContainer(token, container, rights);
        String type = container.getTypeId();
        if (type.equals(ChatClientTool.TYPE_ROOM)) {
            contextItems.setControlsVisible(false);
        }
    }

    public View getView() {
        return contextItems.getView();
    }

}
