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

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
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

    private Button sendBtn;

    private final MultiRoomPresenter presenter;

    private TextArea input;

    private LayoutRegion centralLayout;

    private Label subject;

    private DeckPanel usersDeckPanel;

    public MultiRoomPanel(final MultiRoomPresenter presenter) {
	this.presenter = presenter;
	createLayout();
    }

    public String createRoom(final RoomPresenter roomPresenter) {
	final BorderLayout layout = dialog.getLayout();
	layout.beginUpdate();

	RoomPanel chatRoomPanel = new RoomPanel(roomPresenter);
	roomPresenter.init(chatRoomPanel);
	layout.add(LayoutRegionConfig.CENTER, chatRoomPanel.getContentPanel());

	String contentId = chatRoomPanel.getContentPanel().getId();

	layout.endUpdate();

	// FIXME: Returns Exception, affected maybe by:
	// http://code.google.com/p/gwt-ext/issues/detail?id=81
	// layout.showPanel(contentId);

	return contentId;
    }

    public void show() {
	dialog.show();
    }

    public void hide() {
	dialog.hide();
    }

    public void sendBtnEnable(final boolean enabled) {
	if (enabled) {
	    sendBtn.enable();
	} else {
	    sendBtn.disable();
	}
    }

    public void setSubject(final String text) {
	subject.setText(text);
    }

    public boolean sendBtnIsDisabled() {
	FireLog.debug("Is btn disabled: " + sendBtn.isDisabled());
	return sendBtn.isDisabled();
    }

    protected RoomUsers createRoomUsersPanel() {
	RoomUsersPresenter usersPresenter = new RoomUsersPresenter();
	RoomUsersPanel panel = new RoomUsersPanel();
	usersPresenter.init(panel);

	return usersPresenter;
    }

    protected int addRoomUsersPanel(final RoomUsersPresenter presenter) {
	RoomUsersPanel view = presenter.getView();
	usersDeckPanel.add(view);
	return usersDeckPanel.getWidgetIndex(view);
    }

    protected void insertReturnInInput() {
	input.setText(input.getText() + "\n");
    }

    protected void clearInputText() {
	input.setText("");
	// FIXME: Trying to fix bug:
	DOM.setInnerText(input.getElement(), "");
    }

    protected void setInputText(final String text) {
	input.setText(text);
    }

    protected String getInputText() {
	return input.getText();
    }

    private void createLayout() {
	// create layout regions for layout dialog

	LayoutRegionConfig north = new LayoutRegionConfig() {
	    {
		setTitlebar(false);
		setInitialSize(30);
	    }
	};

	LayoutRegionConfig east = new LayoutRegionConfig() {
	    {
		setSplit(true);
		setInitialSize(150);
		setMinSize(100);
		setMaxSize(250);
		setCollapsible(true);
		setAnimate(true);
		setTitlebar(true);
		setAlwaysShowTabs(false);
	    }
	};

	LayoutRegionConfig center = new LayoutRegionConfig() {
	    {
		setTitlebar(false);
		setAutoScroll(true);
		setTabPosition("top");
		setCloseOnTab(true);
		setAlwaysShowTabs(true);
		setMargins(5, 5, 5, 5);
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
	}, north, south, null, east, center);

	sendBtn = dialog.addButton("Send");
	sendBtn.addButtonListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onSend();
	    }
	});

	final BorderLayout layout = dialog.getLayout();

	layout.beginUpdate();

	ContentPanel eastPanel = createUsersPanel();

	ContentPanel southPanel = createInputPanel();

	ContentPanel northPanel = createSubjectPanel();

	layout.add(LayoutRegionConfig.NORTH, northPanel);

	layout.add(LayoutRegionConfig.EAST, eastPanel);

	layout.add(LayoutRegionConfig.SOUTH, southPanel);

	layout.endUpdate();

	createListeners();

    }

    private void createListeners() {
	dialog.addDialogListener(new DialogListener() {
	    public boolean doBeforeHide(final LayoutDialog dialog) {
		if (centralLayout.getNumPanels() > 0) {
		    return Window.confirm("Sure?");
		}
		return true;
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

	centralLayout = dialog.getLayout().getRegion(LayoutRegionConfig.CENTER);

	centralLayout.addLayoutRegionListener(new LayoutRegionListener() {

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
		if (centralLayout.getNumPanels() == 0) {
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

    private ContentPanel createUsersPanel() {
	// i18n
	ContentPanel eastPanel = new ContentPanel(Ext.generateId(), "Users");
	usersDeckPanel = new DeckPanel();
	usersDeckPanel.addStyleName("kune-MultiRoomPanel-User");
	eastPanel.add(usersDeckPanel);
	return eastPanel;
    }

    private ContentPanel createSubjectPanel() {
	subject = new Label();
	ContentPanel northPanel = new ContentPanel(subject, "", new ContentPanelConfig() {
	    {
		setBackground(false);
	    }
	});
	northPanel.addStyleName("kune-MultiRoomPanel-Subject");
	return northPanel;
    }

    private ContentPanel createInputPanel() {
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
		presenter.onSend();
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
	return southPanel;
    }

    public void activeUsersPanel(final int index) {
	usersDeckPanel.showWidget(index);
    }

}
