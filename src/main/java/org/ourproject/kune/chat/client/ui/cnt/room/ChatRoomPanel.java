/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.ui.cnt.room;

import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * para simplificar, de momento, no hay presenter!!
 * 
 */
public class ChatRoomPanel extends VerticalPanel implements ChatRoom, View {
    private final VerticalPanel stateArea;

    public ChatRoomPanel(final ChatRoomListener listener) {
	FlowPanel flow = new FlowPanel();
	// i18n
	flow.add(new Button("enter room", new ClickListener() {
	    public void onClick(final Widget arg0) {
		listener.onEnterRoom();
	    }
	}));
	flow.add(new Button("reconnect", new ClickListener() {
	    public void onClick(final Widget arg0) {
		listener.onReconnect();
	    }
	}));
	add(flow);
	stateArea = new VerticalPanel();
	add(stateArea);
    }

    public void setState(final ChatState state) {
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
