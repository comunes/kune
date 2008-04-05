package org.ourproject.kune.chat.client.cnt.info.ui;

import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatInfoPanel extends VerticalPanel implements ChatInfo, View {
    private final VerticalPanel stateArea;

    public ChatInfoPanel(final ChatRoomListener listener) {
	FlowPanel flow = new FlowPanel();
	add(flow);
	stateArea = new VerticalPanel();
	add(stateArea);
    }

    public View getView() {
	return this;
    }
}
