package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.platf.client.ui.HorizontalLine;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	    }
	});
    }

    public void setRoomName(final String name) {
	contentPanel.setTitle(name);
    }

    public void addMessage(final HTML message) {
	contentPanel.add(message);
    }

    public ContentPanel getContentPanel() {
	return contentPanel;
    }

    public void addTimeDelimiter(final String datetime) {
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalLine hr = new HorizontalLine();
	hp.add(new Label(datetime));
	hp.add(hr);
	hp.setWidth("100%");
	hp.setCellWidth(hr, "100%");
	contentPanel.add(hp);
	hp.setStyleName("kune-RoomPanel-HorizDelimiter");
    }
}
