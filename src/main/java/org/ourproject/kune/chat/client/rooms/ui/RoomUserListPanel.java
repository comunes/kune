/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.chat.client.rooms.ui;

import org.ourproject.kune.chat.client.rooms.RoomUser;
import org.ourproject.kune.chat.client.rooms.RoomUserListView;
import org.ourproject.kune.platf.client.ui.IconLabel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	AbstractImagePrototype icon;
	if (user.getUserType() == RoomUser.MODERADOR) {
	    icon = RoomImages.App.getInstance().bulletStar();
	} else {
	    icon = RoomImages.App.getInstance().bulletBlack();
	}
	IconLabel userLabel = new IconLabel(icon, user.getAlias());
	userLabel.setColor(user.getColor());
	userPanel.add(userLabel);
	userPanel.addStyleName("kune-Margin-Medium-lr");
	this.insert(userPanel, this.getWidgetCount() - 1);
	return this.getWidgetIndex(userPanel);
    }

    public void delUser(final int index) {
	this.remove(index);
    }
}
