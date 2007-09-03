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
import org.ourproject.kune.platf.client.ui.CustomButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * para simplificar, de momento, no hay presenter!!
 * 
 */
public class ChatRoomPanel extends VerticalPanel implements ChatRoom, View {

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

	// FIXME Abstract and DocumentReaderPanel
	HorizontalPanel panel = new HorizontalPanel();
	Label expand = new Label("");
	panel.add(expand);
	panel.setWidth("100%");
	expand.setWidth("100%");
	panel.setCellWidth(expand, "100%");
	panel.addStyleName("kune-DocumentReaderPanel");
	// i18n
	btnEnter = new CustomButton("Enter room", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onEnterRoom();
	    }
	}).getButton();
	panel.add(btnEnter);
	btnEnter.addStyleName("kune-Button-Large-lrSpace");
	return panel;
    }

    public View getView() {
	return this;
    }
}
