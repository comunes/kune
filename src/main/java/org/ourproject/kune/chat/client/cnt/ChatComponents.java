
package org.ourproject.kune.chat.client.cnt;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControl;
import org.ourproject.kune.chat.client.rooms.MultiRoom;

class ChatComponents {

    private final ChatContentPresenter owner;
    private MultiRoom multiRoom;
    private ChatRoom chatRoom;
    private ChatInfo chatInfo;
    private ChatRoomControl chatRoomControl;

    public ChatComponents(final ChatContentPresenter owner) {
        this.owner = owner;
    }

    public ChatRoom getChatRoom() {
        if (chatRoom == null) {
            chatRoom = ChatFactory.createChatRoomViewer(owner);
        }
        return chatRoom;
    }

    public ChatRoomControl getChatRoomControl() {
        if (chatRoomControl == null) {
            chatRoomControl = ChatFactory.createChatRoomControlViewer(owner);
        }
        return chatRoomControl;
    }

    public MultiRoom getRooms() {
        if (multiRoom == null) {
            multiRoom = ChatFactory.createChatMultiRoom(owner);
        }
        return multiRoom;
    }

    public ChatInfo getChatInfo() {
        if (chatInfo == null) {
            chatInfo = ChatFactory.createChatInfo(owner);
        }
        return chatInfo;
    }

}
