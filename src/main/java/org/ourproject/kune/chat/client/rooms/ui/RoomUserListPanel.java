/*
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

package org.ourproject.kune.chat.client.rooms.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RoomUserListPanel extends VerticalPanel implements RoomUserListView {
    public RoomUserListPanel() {
	Label expand = new Label();
	this.add(expand);
	expand.setStyleName("kune-expandVert");
	this.setCellHeight(expand, "100%");
    }

    public int addUser(final RoomUser user) {
	HorizontalPanel userPanel = new HorizontalPanel();
	Image userIcon = new Image();
	Label userAliasLabel = new HTML("<span style=\"color: " + user.getColor() + "\">" + user.getAlias() + "</span>");
	if (user.getUserType() == RoomUser.MODERADOR) {
	    RoomImages.App.getInstance().bulletStar().applyTo(userIcon);
	} else {
	    RoomImages.App.getInstance().bulletBlack().applyTo(userIcon);
	}
	userPanel.add(userIcon);
	userPanel.add(userAliasLabel);
	this.insert(userPanel, this.getWidgetCount() - 1);
	return this.getWidgetIndex(userPanel);
    }

    public void delUser(final int index) {
	this.remove(index);
    }
}
