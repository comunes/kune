package org.ourproject.kune.chat.client.ui.rooms;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

class RoomUsersPanel extends VerticalPanel {
    public RoomUsersPanel() {
    }

    public int addUser(RoomUser user) {
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

    public void delUser(int index) {
	this.getWidget(index).setVisible(false);
    }
}
