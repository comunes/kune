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
public class ChatRoomViewerPanel extends VerticalPanel implements ChatRoomViewer, View {

    public ChatRoomViewerPanel(final ChatRoomViewerListener listener) {
	FlowPanel flow = new FlowPanel();
	// i18n
	flow.add(new Button("enter room", new ClickListener() {
	    public void onClick(final Widget arg0) {
		listener.onEnterRoom();
	    }
	}));
	add(new Label("panel del chat: contenido por defecto"));
    }

    public View getView() {
	return this;
    }
}
