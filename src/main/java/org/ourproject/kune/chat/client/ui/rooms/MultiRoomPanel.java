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

package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.util.KeyMapConfig;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListener;
import com.gwtext.client.widgets.event.KeyListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.event.LayoutRegionListener;

public class MultiRoomPanel implements MultiRoomView {

    private LayoutDialog dialog;

    private final Map rooms;

    private Button sendBtn;

    private final MultiRoomPresenter presenter;

    private TextArea input;

    public MultiRoomPanel(final MultiRoomPresenter presenter) {
	this.presenter = presenter;
	createLayout();
	rooms = new HashMap();
    }

    public String createRoom(final RoomPresenter roomPresenter) {
	final BorderLayout layout = dialog.getLayout();
	layout.beginUpdate();

	RoomPanel chatRoomPanel = new RoomPanel(roomPresenter);
	roomPresenter.init(chatRoomPanel);
	layout.add(LayoutRegionConfig.CENTER, chatRoomPanel.getContentPanel());

	String contentId = chatRoomPanel.getContentPanel().getId();
	GWT.log("Panel chat: " + contentId, null);
	layout.showPanel(contentId);
	layout.endUpdate();
	return contentId;
    }

    public void show() {
	dialog.show();
    }

    public void hide() {
	dialog.hide();
    }

    public void addUser(final String roomId, final String userAlias, final String color) {
	// TODO Auto-generated method stub

    }

    public void clearTextArea() {
	input.setText("");
    }

    public void sendBtnEnable(final boolean enabled) {
	if (enabled) {
	    sendBtn.enable();
	} else {
	    sendBtn.disable();
	}
    }

    public boolean sendBtnIsDisabled() {
	FireLog.debug("Is btn disabled: " + sendBtn.isDisabled());
	return sendBtn.isDisabled();
    }

    protected void insertReturnInInput() {
	input.setText(input.getText() + "\n");
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
		setTitle("Subject");
		setTitlebar(true);
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
		return Window.confirm("Sure?");
	    }

	    public boolean doBeforeShow(final LayoutDialog dialog) {
		return true;
	    }

	    public void onHide(final LayoutDialog dialog) {
	    }

	    public void onKeyDown(final LayoutDialog dialog, final EventObject e) {
	    }

	    public void onMove(final LayoutDialog dialog, final int x, final int y) {
	    }

	    public void onResize(final LayoutDialog dialog, final int width, final int height) {
	    }

	    public void onShow(final LayoutDialog dialog) {
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

	    public void onKeyUp(final Widget arg0, final char arg1, final int arg2) {
		FireLog.debug("keylist1, key: " + arg1);
	    }
	});

	dialog.addKeyListener(13, new KeyListener() {
	    public void onKey(final int key, final EventObject e) {
		FireLog.debug("Enter pressed");
		presenter.onSend(input.getText());
	    }
	});

	dialog.addKeyListener(new KeyMapConfig() {
	    {
		// setCtrl(true);
	    }
	}, new KeyListener() {

	    public void onKey(final int key, final EventObject e) {
		FireLog.debug("Key " + key + " pressed");
	    }
	});

	layout.add(LayoutRegionConfig.SOUTH, southPanel);

	layout.endUpdate();

	layout.getRegion(LayoutRegionConfig.CENTER).addLayoutRegionListener(new LayoutRegionListener() {

	    public boolean doBeforeRemove(final LayoutRegion region, final ContentPanel panel) {
		if (Window.confirm("Are you sure?")) {
		    presenter.closeRoom(panel.getId());
		    return true;
		}
		return false;
	    }

	    public void onCollapsed(final LayoutRegion region) {
	    }

	    public void onExpanded(final LayoutRegion region) {
	    }

	    public void onInvalidated(final LayoutRegion region) {
	    }

	    public void onPanelActivated(final LayoutRegion region, final ContentPanel panel) {
		presenter.activateRoom(panel.getId());
	    }

	    public void onPanelAdded(final LayoutRegion region, final ContentPanel panel) {
	    }

	    public void onPanelRemoved(final LayoutRegion region, final ContentPanel panel) {
		if (dialog.getLayout().getRegion(LayoutRegionConfig.CENTER).getNumPanels() == 0) {
		    presenter.onNoRooms();
		}
	    }

	    public void onResized(final LayoutRegion region, final int newSize) {
	    }

	    public void onSlideHide(final LayoutRegion region) {
	    }

	    public void onSlideShow(final LayoutRegion region) {
	    }

	    public void onVisibilityChange(final LayoutRegion region, final boolean visibility) {
	    }
	});

    }

    // private Form createFormImput() {
    // Form form = new Form(new FormConfig() {
    // {
    // setWidth("100%");
    // setHideLabels(true);
    // }
    // });
    //
    // form.add(new com.gwtext.client.widgets.form.TextArea(new TextAreaConfig()
    // {
    // {
    // setName("input");
    // }
    // }));
    //
    // form.render();
    // return form;
    // }

    class RoomDescriptor {
	private String roomId;
	private String roomLongName;
	private String input;
	private String contentPanelId;
	private Element elementId;
	private Map users;

	public RoomDescriptor(final String roomId, final String roomLongName, final String contentPanelId,
		final Element elementId) {
	    this.roomId = roomId;
	    this.roomLongName = roomLongName;
	    this.contentPanelId = contentPanelId;
	    this.elementId = elementId;
	    this.users = new HashMap();
	    this.input = "";
	}

	public Element getElementId() {
	    return elementId;
	}

	public void setElementId(final Element elementId) {
	    this.elementId = elementId;
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

	public String getContentPanelId() {
	    return contentPanelId;
	}

	public void setContentPanelId(final String contentPanelId) {
	    this.contentPanelId = contentPanelId;
	}

	public Map getUsers() {
	    return users;
	}

	public void setUsers(final Map users) {
	    this.users = users;
	}

	public String getInput() {
	    return input;
	}

	public void setInput(final String input) {
	    this.input = input;
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

    protected String getInputText() {
	return input.getText();
    }

    protected void restoreInput(final String roomId) {
	input.setText(((RoomDescriptor) rooms.get(roomId)).getInput());
    }

    protected void saveInput(final String roomId) {
	((RoomDescriptor) rooms.get(roomId)).setInput(input.getText());
    }

    protected void clearSavedInput(final String roomId) {
	((RoomDescriptor) rooms.get(roomId)).setInput("");
    }

    protected void setInputText(final String text) {
	input.setText(text);
    }
}
