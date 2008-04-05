
package org.ourproject.kune.chat.client.ctx;

import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

public class ChatContextPresenter implements ChatContext {

    private final WorkspaceDeckView view;
    private final ChatContextComponents components;

    public ChatContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
	this.components = new ChatContextComponents(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setState(final StateDTO state) {
	RoomsAdmin rooms = components.getRoomsAdmin();
	rooms.showRoom(state.getStateToken(), state.getFolder(), state.getFolderRights());
	view.show(rooms.getView());
    }

}
