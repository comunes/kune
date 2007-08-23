package org.ourproject.kune.chat.client.ui.rooms;

import com.google.gwt.user.client.ui.HTML;
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

    public void setTitle(final String title) {
	contentPanel.setTitle(title);
    }

    public void addMessage(final HTML message) {
	contentPanel.add(message);
    }

    public ContentPanel getContentPanel() {
	return contentPanel;
    }
}
