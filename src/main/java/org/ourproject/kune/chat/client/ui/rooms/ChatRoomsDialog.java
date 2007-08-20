package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.event.LayoutRegionListener;

public class ChatRoomsDialog implements ChatRoomsDialogView {

    private LayoutDialog dialog;

    private final Map rooms;

    private final Map contentPanelsToRooms;

    private Button sendBtn;

    private final ChatRoomsPresenter presenter;

    private TextArea input;

    public ChatRoomsDialog(final ChatRoomsPresenter presenter) {
	this.presenter = presenter;
	createLayout();
	rooms = new HashMap();
	contentPanelsToRooms = new HashMap();
    }

    private void createLayout() {
	// create layout regions for layout dialog
	LayoutRegionConfig east = new LayoutRegionConfig() {
	    {
		setSplit(true);
		setInitialSize(150);
		setMinSize(100);
		setMaxSize(250);
		setCollapsible(true);
		setAnimate(true);
		setTitlebar(true);
	    }
	};

	LayoutRegionConfig south = new LayoutRegionConfig() {
	    {
		setSplit(true);
		// setInitialSize(600);
		setHideWhenEmpty(false);
		setInitialSize(50);
	    }
	};

	LayoutRegionConfig center = new LayoutRegionConfig() {
	    {
		setAutoScroll(true);
		setTabPosition("top");
		setCloseOnTab(true);
		setAlwaysShowTabs(true);
		setMargins(5, 5, 5, 5);
	    }
	};

	dialog = new LayoutDialog(new LayoutDialogConfig() {
	    {
		setModal(false);
		setWidth(600);
		setHeight(400);
		setShadow(true);
		setMinHeight(300);
		setMinHeight(300);
		setProxyDrag(true);
		// i18n
		setTitle("Chat rooms");
	    }
	}, null, south, null, east, center);

	dialog.addDialogListener(new DialogListener() {

	    public boolean doBeforeHide(final LayoutDialog dialog) {
		// TODO Auto-generated method stub
		return Window.confirm("Sure?");
	    }

	    public boolean doBeforeShow(final LayoutDialog dialog) {
		// TODO Auto-generated method stub
		return true;
	    }

	    public void onHide(final LayoutDialog dialog) {
		// TODO Auto-generated method stub

	    }

	    public void onKeyDown(final LayoutDialog dialog, final EventObject e) {
		// TODO Auto-generated method stub

	    }

	    public void onMove(final LayoutDialog dialog, final int x, final int y) {
		// TODO Auto-generated method stub

	    }

	    public void onResize(final LayoutDialog dialog, final int width, final int height) {
		// TODO Auto-generated method stub

	    }

	    public void onShow(final LayoutDialog dialog) {
		// TODO Auto-generated method stub

	    }
	});

	sendBtn = dialog.addButton("Send");
	sendBtn.addButtonListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onSend(input.getText());
	    }
	});

	// add content to various regions
	final BorderLayout layout = dialog.getLayout();

	layout.beginUpdate();

	// i18n
	layout.add(LayoutRegionConfig.EAST, new ContentPanel(Ext.generateId(), "Users"));

	input = new TextArea();
	ContentPanel southPanel = new ContentPanel(input, "", new ContentPanelConfig() {
	    {
		setBackground(true);
	    }
	});
	input.setWidth("100%");
	input.setHeight("100%");

	input.addKeyboardListener(new KeyboardListener() {
	    public void onKeyDown(final Widget arg0, final char arg1, final int arg2) {
	    }

	    public void onKeyPress(final Widget arg0, final char arg1, final int arg2) {
	    }

	    public void onKeyUp(final Widget widget, final char key, final int mod) {
		presenter.onInput(key, mod);
	    }

	});

	layout.add(LayoutRegionConfig.SOUTH, southPanel);

	layout.endUpdate();

    }

    public void insertReturnInInput() {
	input.setText(input.getText() + "\n");
    }

    public void show() {
	dialog.show();
    }

    public void hide() {
	dialog.hide();
    }

    public void addMessage(final String roomId, final String userAlias, final String message) {
	Element contentPanelId = ((RoomDescriptor) rooms.get(roomId)).getContentPanelId();
	DOM.appendChild(contentPanelId, (formatter(message)).getElement());
	// conversation.setScrollPosition(conversationVP.getOffsetHeight());
    }

    private HTML formatter(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");
	return new HTML(message);
    }

    public void addUser(final String roomId, final String userAlias, final String color) {
	// TODO Auto-generated method stub

    }

    public void clearMessages(final String roomId) {
	// TODO Auto-generated method stub

    }

    public void clearTextArea() {
	input.setText("");
    }

    public void closeRoom(final String roomId) {
	// TODO Auto-generated method stub

    }

    public void sendBtnEnable(final boolean enabled) {
	if (enabled) {
	    sendBtn.enable();
	} else {
	    sendBtn.disable();
	}
    }

    public boolean sendBtnIsDisabled() {
	return sendBtn.isDisabled();
    }

    public void createRoom(final String roomId, final String longName) {
	// adding multiple Content Panels to the same region resutls in tabs

	final BorderLayout layout = dialog.getLayout();
	layout.beginUpdate();

	String contentPanelId = Ext.generateId();
	ContentPanel contentPanel = new ContentPanel(contentPanelId, new ContentPanelConfig() {
	    {
		setTitle(longName);
		setClosable(true);
		setBackground(true);
	    }
	});
	layout.add(LayoutRegionConfig.CENTER, contentPanel);
	layout.getRegion(LayoutRegionConfig.CENTER).addLayoutRegionListener(new LayoutRegionListener() {

	    public boolean doBeforeRemove(final LayoutRegion region, final ContentPanel panel) {
		// TODO Auto-generated method stub
		return false;
	    }

	    public void onCollapsed(final LayoutRegion region) {
		// TODO Auto-generated method stub

	    }

	    public void onExpanded(final LayoutRegion region) {
		// TODO Auto-generated method stub

	    }

	    public void onInvalidated(final LayoutRegion region) {
		// TODO Auto-generated method stub

	    }

	    public void onPanelActivated(final LayoutRegion region, final ContentPanel panel) {
		String room = (String) contentPanelsToRooms.get(panel);
		presenter.onRoomSelected(room);
	    }

	    public void onPanelAdded(final LayoutRegion region, final ContentPanel panel) {
		// TODO Auto-generated method stub

	    }

	    public void onPanelRemoved(final LayoutRegion region, final ContentPanel panel) {
		// TODO Auto-generated method stub

	    }

	    public void onResized(final LayoutRegion region, final int newSize) {
		// TODO Auto-generated method stub

	    }

	    public void onSlideHide(final LayoutRegion region) {
		// TODO Auto-generated method stub

	    }

	    public void onSlideShow(final LayoutRegion region) {
		// TODO Auto-generated method stub

	    }

	    public void onVisibilityChange(final LayoutRegion region, final boolean visibility) {
		// TODO Auto-generated method stub

	    }
	});

	rooms.put(roomId, new RoomDescriptor(roomId, longName, contentPanel.getElement()));
	contentPanelsToRooms.put(contentPanelId, roomId);
	layout.showPanel(contentPanelId);
	layout.endUpdate();
    }

    public void removeUser(final String roomId, final String userAlias) {
	// TODO Auto-generated method stub

    }

    class RoomDescriptor {
	private String roomId;
	private String roomLongName;
	private Element contentPanelId;
	private Map users;

	public RoomDescriptor(final String roomId, final String roomLongName, final Element element) {
	    this.roomId = roomId;
	    this.roomLongName = roomLongName;
	    this.contentPanelId = element;
	    this.users = new HashMap();
	}

	public String getRoomId() {
	    return roomId;
	}

	public void setRoomId(final String roomId) {
	    this.roomId = roomId;
	}

	public String getRoomLongName() {
	    return roomLongName;
	}

	public void setRoomLongName(final String roomLongName) {
	    this.roomLongName = roomLongName;
	}

	public Element getContentPanelId() {
	    return contentPanelId;
	}

	public void setContentPanelId(final Element contentPanelId) {
	    this.contentPanelId = contentPanelId;
	}

	public Map getUsers() {
	    return users;
	}

	public void setUsers(final Map users) {
	    this.users = users;
	}

    }

    class ChatUserDescriptor {
	String alias;
	String color;

	public ChatUserDescriptor(final String alias, final String color) {
	    this.alias = alias;
	    this.color = color;
	}

	public String getAlias() {
	    return alias;
	}

	public void setAlias(final String alias) {
	    this.alias = alias;
	}

	public String getColor() {
	    return color;
	}

	public void setColor(final String color) {
	    this.color = color;
	}

    }

    public String getInputText() {
	return input.getText();
    }
}
