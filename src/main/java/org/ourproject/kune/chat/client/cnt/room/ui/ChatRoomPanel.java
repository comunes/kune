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

package org.ourproject.kune.chat.client.cnt.room.ui;

import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomView;
import org.ourproject.kune.workspace.client.ui.ToolBarPanel;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatRoomPanel extends VerticalPanel implements ChatRoomView {

    private com.gwtext.client.widgets.Button btnEnter;
    private final HTML content;

    public ChatRoomPanel(final ChatRoomListener listener) {
	add(createToolBar(listener));
	// FIXME: control perms
	btnEnter.setVisible(true);
	content = new HTML("");
	add(content);
	this.setWidth("100%");
	this.setCellWidth(content, "100%");
	content.addStyleName("main-content");
    }

    private Widget createToolBar(final ChatRoomListener listener) {
	ToolBarPanel toolbar = new ToolBarPanel();
	// i18n
	toolbar.addButton("Enter room", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onEnterRoom();
	    }
	});
	return toolbar;
    }

}
