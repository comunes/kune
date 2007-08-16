package org.ourproject.kune.chat.client.ui.ctx;

import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class ChatContextPresenter implements ChatContext {

    private final WorkspaceDeckView view;
    private final Components components;

    public ChatContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
	this.components = new Components(this);
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
	view.show(rooms.getView());
    }

}
