package org.ourproject.kune.chat.client.ui;

import org.ourproject.kune.chat.client.ui.cnt.ChatContent;
import org.ourproject.kune.chat.client.ui.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.ui.ctx.ChatContext;
import org.ourproject.kune.chat.client.ui.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdminPanel;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;

public class ChatFactory {

    public static ChatContent createChatContent() {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	ChatContentPresenter presenter = new ChatContentPresenter(panel);
	return presenter;
    }

    public static ChatContext createChatContext() {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	ChatContextPresenter presenter = new ChatContextPresenter(panel);
	return presenter;
    }

    public static RoomsAdmin createRoomsAdmin() {
	RoomsAdminPanel panel = new RoomsAdminPanel();
	RoomsAdminPresenter presenter = new RoomsAdminPresenter(panel);
	return presenter;
    }

}
