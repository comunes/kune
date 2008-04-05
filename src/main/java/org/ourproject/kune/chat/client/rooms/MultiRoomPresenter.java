
package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;

import com.google.gwt.user.client.ui.CellPanel;

public class MultiRoomPresenter implements MultiRoom, RoomListener {
    private MultiRoomView view;
    private Room currentRoom;
    private final MultiRoomListener listener;
    private boolean closeAllConfirmed;

    public MultiRoomPresenter(final MultiRoomListener listener) {
        this.listener = listener;
    }

    public void init(final MultiRoomView view) {
        this.view = view;
        closeAllConfirmed = false;

        // only for tests
        view.addPresenceBuddy("fulano", "I'm out for dinner", MultiRoomView.STATUS_AWAY);
        view.addPresenceBuddy("mengano", "Working", MultiRoomView.STATUS_BUSY);
    }

    public Room createRoom(final String roomName, final String userAlias, final UserType userType) {
        Room room = ChatFactory.createRoom(this);
        room.setRoomName(roomName);
        room.setUserAlias(userAlias);
        room.setUserType(userType);
        currentRoom = room;
        view.addRoomUsersPanel(room.getUsersListView());
        view.addRoom(room);
        return currentRoom;
    }

    public void show() {
        view.show();
        closeAllConfirmed = false;
    }

    public void onSend() {
        listener.onSendMessage(currentRoom, view.getInputText());
        // view.setSendEnabled(false);
        view.clearInputText();
    }

    public void closeRoom(final RoomPresenter room) {
        room.doClose();
        listener.onCloseRoom(room);
    }

    public void activateRoom(final Room nextRoom) {
        if (currentRoom != null) {
            currentRoom.saveInput(view.getInputText());
        }
        view.setSendEnabled(nextRoom.isReady());
        view.setInputText(nextRoom.getSavedInput());
        view.setSubject(nextRoom.getSubject());
        view.setSubjectEditable(nextRoom.getUserType().equals(RoomUser.MODERADOR));
        view.showUserList(nextRoom.getUsersListView());
        nextRoom.activate();
        currentRoom = nextRoom;
    }

    public void onRoomReady(final Room room) {
        if (currentRoom == room) {
            view.setSendEnabled(true);
        }
    }

    public void onMessageReceived(final Room room) {
        view.highlightRoom(room);
    }

    public void onCloseAllNotConfirmed() {
        closeAllConfirmed = false;
        view.setSubject("");
    }

    public boolean isCloseAllConfirmed() {
        return closeAllConfirmed;
    }

    public void attachIconToBottomBar(final View view) {

        DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                new UIExtensionPoint(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, (CellPanel) view));
    }

    public void changeRoomSubject(final String text) {
        // FIXME Do the real subject rename
        view.setSubject(text);
        currentRoom.setSubject(text);
    }

    public void onStatusSelected(final int status) {
        view.setStatus(status);
        switch (status) {
        case MultiRoomView.STATUS_AWAY:
            // FIXME
            break;
        default:
            break;
        }
    }

    public void addBuddy(final String shortName, final String longName) {
        // TODO Auto-generated method stub
    }

    public void inviteUserToRoom(final String shortName, final String longName) {
        // TODO Auto-generated method stub
    }

}
