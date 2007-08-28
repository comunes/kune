package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.platf.client.ui.HorizontalLine;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.event.ContentPanelListener;

public class RoomPanel implements RoomView {

    private final ContentPanel contentPanel;
    private final ScrollPanel scroll;
    private final VerticalPanel vp;

    public RoomPanel(final RoomPresenter presenter) {
	String contentPanelId = Ext.generateId();
	contentPanel = new ContentPanel(contentPanelId, new ContentPanelConfig() {
	    {
		setClosable(true);
		setBackground(true);
		setAutoScroll(true);
	    }
	});
	contentPanel.addContentPanelListener(new ContentPanelListener() {
	    public void onActivate(final ContentPanel cp) {
	    }

	    public void onDeactivate(final ContentPanel cp) {
	    }

	    public void onResize(final ContentPanel cp, final int width, final int height) {
		adjustScrolSize(width, height);
	    }
	});
	scroll = new ScrollPanel();
	contentPanel.add(scroll);
	vp = new VerticalPanel();
	scroll.add(vp);
	contentPanel.addStyleName("kune-RoomPanel-Conversation");
	adjustScrolSize(408, 200);
    }

    private void adjustScrolSize(final int width, final int height) {
	FireLog.debug("width: " + width + ", height: " + height);
	scroll.setWidth("" + (width - 2));
	scroll.setHeight("" + (height - 2));
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
	vp.add(widget);
	scroll.setScrollPosition(vp.getOffsetHeight());
    }
}
