
package org.ourproject.kune.chat.client.cnt;

import java.util.HashMap;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.ChatEvents;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.rooms.MultiRoomListener;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomUser;
import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.JoinRoomActionParams;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.ui.UnknowComponent;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public class ChatContentPresenter implements ChatContent, ChatRoomListener, MultiRoomListener {

    private final WorkspaceDeckView view;
    private final ChatComponents components;
    private final HashMap<String, Room> roomNamesToRooms;
    private StateDTO state;

    public ChatContentPresenter(final WorkspaceDeckView view) {
        this.view = view;
        this.components = new ChatComponents(this);
        this.roomNamesToRooms = new HashMap<String, Room>();
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
        return view;
    }

    public void setState(final StateDTO state) {
        this.state = state;
        String typeId = state.getTypeId();
        if (typeId.equals(ChatClientTool.TYPE_ROOT)) {
            ChatInfo info = components.getChatInfo();
            view.show(info.getView());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
        } else if (typeId.equals(ChatClientTool.TYPE_ROOM)) {
            ChatRoom viewer = components.getChatRoom();
            view.show(viewer.getView());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
            DefaultDispatcher.getInstance().fire(
                    PlatformEvents.ATTACH_TO_EXT_POINT,
                    new UIExtensionElement(UIExtensionPoint.CONTENT_TOOLBAR_LEFT, components.getChatRoomControl()
                            .getView()));
        } else {
            view.show(UnknowComponent.instance.getView());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
        }
    }

    public void onEnterRoom() {
        MultiRoom rooms = components.getRooms();
        String roomName = state.getFolder().getName();
        // FIXME Moderator?
        // Room room = getRoom(roomName, "me" + new Date().getTime(),
        // RoomUser.MODERADOR);
        Room room = getRoom(roomName, "me", RoomUser.MODERADOR);
        rooms.show();
        rooms.activateRoom(room);
    }

    private Room getRoom(final String roomName, final String userAlias, final UserType userType) {
        Room room = roomNamesToRooms.get(roomName);
        if (room == null) {
            room = createRoom(roomName, userAlias, userType);
            roomNamesToRooms.put(roomName, room);
        }
        return room;
    }

    private Room createRoom(final String roomName, final String userAlias, final UserType userType) {
        MultiRoom rooms = components.getRooms();
        final Room room = rooms.createRoom(roomName, userAlias, userType);
        DefaultDispatcher.getInstance().fireDeferred(ChatEvents.JOIN_ROOM, new JoinRoomActionParams(room, userAlias));
        return room;
    }

    public void onSendMessage(final Room room, final String message) {
        XmppRoom handler = room.getHandler();
        if (handler != null) {
            handler.sendMessage(message);
        } else {
            debugNoHandler(room);
        }
    }

    public void onCloseRoom(final Room room) {
        roomNamesToRooms.remove(room.getName());
    }

    private void debugNoHandler(final Room room) {
        Log.debug("Room '" + room.getName() + "' has no xmmp handler");
    }

}
