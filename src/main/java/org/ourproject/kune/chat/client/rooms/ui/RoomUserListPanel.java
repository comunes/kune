package org.ourproject.kune.chat.client.rooms.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

class RoomUserListPanel extends VerticalPanel implements RoomUserListView {
    public RoomUserListPanel() {
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
	this.add(userPanel);
	return this.getWidgetIndex(userPanel);
    }

    public void delUser(final int index) {
	this.remove(index);
    }
}
