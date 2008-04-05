
package org.ourproject.kune.chat.client.cnt.room.ui;

import org.ourproject.kune.chat.client.cnt.room.ChatRoomView;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.ui.DefaultContentPanel;

public class ChatRoomPanel extends DefaultContentPanel implements ChatRoomView {

    public ChatRoomPanel() {
        setContent("History of room conversations." + Site.IN_DEVELOPMENT);
    }

}
