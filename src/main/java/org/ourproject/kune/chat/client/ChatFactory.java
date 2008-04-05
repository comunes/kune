
package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.ChatContent;
import org.ourproject.kune.chat.client.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.info.ui.ChatInfoPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControl;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlPresenter;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPresenter;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomControlPanel;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomPanel;
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.chat.client.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.rooms.MultiRoomListener;
import org.ourproject.kune.chat.client.rooms.MultiRoomPresenter;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomListener;
import org.ourproject.kune.chat.client.rooms.RoomPresenter;
import org.ourproject.kune.chat.client.rooms.RoomUserList;
import org.ourproject.kune.chat.client.rooms.RoomUserListPresenter;
import org.ourproject.kune.chat.client.rooms.ui.MultiRoomPanel;
import org.ourproject.kune.chat.client.rooms.ui.RoomPanel;
import org.ourproject.kune.chat.client.rooms.ui.RoomUserListPanel;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

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
        ContextItems contextItems = WorkspaceFactory.createContextItems();
        RoomsAdminPresenter presenter = new RoomsAdminPresenter(contextItems);
        return presenter;
    }

    public static MultiRoom createChatMultiRoom(final MultiRoomListener listener) {
        MultiRoomPresenter presenter = new MultiRoomPresenter(listener);
        MultiRoomPanel panel = new MultiRoomPanel(presenter);
        presenter.init(panel);
        return presenter;
    }

    public static ChatRoom createChatRoomViewer(final ChatRoomListener listener) {
        ChatRoomPanel panel = new ChatRoomPanel();
        ChatRoomPresenter presenter = new ChatRoomPresenter(panel);
        return presenter;
    }

    public static ChatRoomControl createChatRoomControlViewer(final ChatRoomListener listener) {
        ChatRoomControlPanel panel = new ChatRoomControlPanel(listener);
        ChatRoomControlPresenter presenter = new ChatRoomControlPresenter(panel);
        return presenter;
    }

    public static RoomUserList createUserList() {
        RoomUserListPresenter userListPresenter = new RoomUserListPresenter();
        RoomUserListPanel panel = new RoomUserListPanel();
        userListPresenter.init(panel);
        return userListPresenter;
    }

    public static Room createRoom(final RoomListener listener) {
        RoomUserList userList = ChatFactory.createUserList();
        RoomPresenter presenter = new RoomPresenter(listener);
        presenter.setUserList(userList);
        RoomPanel panel = new RoomPanel(presenter);
        presenter.init(panel);
        return presenter;
    }

    public static ChatInfo createChatInfo(final ChatRoomListener listener) {
        ChatInfoPanel panel = new ChatInfoPanel(listener);
        return panel;
    }
}
