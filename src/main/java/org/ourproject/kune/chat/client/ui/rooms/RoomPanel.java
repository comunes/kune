package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.platf.client.ui.HorizontalLine;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;

public class RoomPanel implements RoomView {

    private final ContentPanel contentPanel;

    public RoomPanel(final RoomPresenter presenter) {
	String contentPanelId = Ext.generateId();
	contentPanel = new ContentPanel(contentPanelId, new ContentPanelConfig() {
	    {
		setClosable(true);
		setBackground(true);
		setAutoScroll(true);
	    }
	});
	contentPanel.addStyleName("kune-RoomPanel-Conversation");
    }

    public void setRoomName(final String name) {
	contentPanel.setTitle(name);
    }

    public void addEventMessage(final String message) {
	HTML messageHtml = new HTML(message);
	addWidget(messageHtml);
	messageHtml.addStyleName("kune-RoomPanel-EventMessage");
    }

    public void addMessage(final String userAlias, final String color, final String message) {
	String userHtml = "<span style=\"color: " + color + "; font-weight: bold;\">" + userAlias + "</span>:&nbsp;";
	HTML messageHtml = new HTML(userHtml + ChatFactory.formatter(message).getHTML());
	addWidget(messageHtml);
    }

    public void addTimeDelimiter(final String datetime) {
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalLine hr = new HorizontalLine();
	hp.add(new Label(datetime));
	hp.add(hr);
	hp.setWidth("100%");
	hp.setCellWidth(hr, "100%");
	addWidget(hp);
	hp.setStyleName("kune-RoomPanel-HorizDelimiter");
    }

    public ContentPanel getContentPanel() {
	return contentPanel;
    }

    private void addWidget(final Widget widget) {
	contentPanel.add(widget);
	FireLog.debug("ContentPanel id: " + contentPanel.getId() + " Scroll pos: " + contentPanel.getOffsetHeight());
	// DOM.setElementPropertyInt(contentPanel.getParent().getElement(),
	// "scrollTop", contentPanel.getOffsetHeight());
	// conversationSP.setScrollPosition(conversationVP.getOffsetHeight());
    }
}
