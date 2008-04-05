
package org.ourproject.kune.chat.client.cnt.room.ui;

import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlView;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.CustomPushButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatRoomControlPanel extends HorizontalPanel implements ChatRoomControlView {
    private final CustomPushButton enterRoomBtn;

    public ChatRoomControlPanel(final ChatRoomListener listener) {
        enterRoomBtn = new CustomPushButton(Kune.I18N.t("Enter room"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onEnterRoom();
            }
        });
        add(enterRoomBtn);
        setEnterRoomEnabled(true);
    }

    public void setEnterRoomEnabled(final boolean isEnabled) {
        enterRoomBtn.setEnabled(isEnabled);
    }

}