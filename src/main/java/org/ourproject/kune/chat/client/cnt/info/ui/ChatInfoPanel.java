package org.ourproject.kune.chat.client.cnt.info.ui;

import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatInfoPanel extends VerticalPanel implements ChatInfo, View {
    private final VerticalPanel stateArea;

    public ChatInfoPanel(final ChatRoomListener listener) {
	FlowPanel flow = new FlowPanel();
	add(flow);
	stateArea = new VerticalPanel();
	add(stateArea);
    }

    public void setChatState(final ChatState state) {
	stateArea.clear();
	stateArea.add(new Label("base: " + state.httpBase));
	stateArea.add(new Label("domain: " + state.domain));
	stateArea.add(new Label("connected: " + state.isConnected));
	if (state.user != null) {
	    stateArea.add(new Label("user: " + state.user.userName));
	    stateArea.add(new Label("password: " + state.user.password));
	    stateArea.add(new Label("resource: " + state.user.resource));
	}
    }

    public View getView() {
	return this;
    }
}
