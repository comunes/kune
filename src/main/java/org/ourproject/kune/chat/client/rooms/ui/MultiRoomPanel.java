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

import java.util.HashMap;

import org.ourproject.kune.chat.client.rooms.EmoticonPaletteListener;
import org.ourproject.kune.chat.client.rooms.MultiRoomPresenter;
import org.ourproject.kune.chat.client.rooms.MultiRoomView;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomPresenter;
import org.ourproject.kune.chat.client.rooms.RoomUserListView;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.BottomTrayIcon;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchListener;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.TextItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class MultiRoomPanel implements MultiRoomView, View {
    private static final XmppIcons icons = XmppIcons.App.getInstance();

    protected static final String INPUT_FIELD = "input-area";
    private Window dialog;
    private Button sendBtn;
    private final MultiRoomPresenter presenter;
    private TextArea subject;
    private DeckPanel roomUsersDeckPanel;
    private TextArea input;
    private final HashMap<RoomUserListView, Integer> userListToIndex;
    private final HashMap<String, Room> panelIdToRoom;
    // private final HashMap panelIdToTabId;
    private EmoticonPalettePanel emoticonPalettePanel;
    private PopupPanel emoticonPopup;
    private BottomTrayIcon bottomIcon;
    private Menu statusMenu;
    private CheckItem onlineMenuItem;
    private CheckItem offlineMenuItem;
    private CheckItem busyMenuItem;
    private CheckItem awayMenuItem;
    private ToolbarMenuButton statusButton;
    // private IndexedStackPanelWithSubItems usersStack;
    private ToolbarButton inviteUserToRoom;
    private TabPanel centerPanel;
    private Panel usersPanel;;

    public MultiRoomPanel(final MultiRoomPresenter presenter) {
        this.presenter = presenter;
        this.userListToIndex = new HashMap<RoomUserListView, Integer>();
        panelIdToRoom = new HashMap<String, Room>();
        // panelIdToTabId = new HashMap();
        createLayout();
    }

    public void addRoom(final Room room) {
        Panel roomPanel = (Panel) room.getView();
        centerPanel.add(roomPanel);
        String panelId = roomPanel.getId();
        Log.debug("Panel id added: " + panelId);
        panelIdToRoom.put(panelId, room);
        roomPanel.show();
        // centerPanel.setActiveItemID(panelId);
        // panelIdToTabId.put(panelId, centerPanel.getActiveTab().getId());
    }

    public void highlightRoom(final Room room) {
        // Panel roomPanel = (Panel) room.getView();
        // String panelId = roomPanel.getId();
        // String tabId = (String) panelIdToTabId.get(panelId);
        // centerPanel.get(tabId).getTextEl().highlight();
    }

    public void show() {
        dialog.show();
        dialog.expand();
        // dialog.center();
        if (bottomIcon == null) {
            bottomIcon = new BottomTrayIcon(Kune.I18N.t("Show/hide chat dialog"));
            bottomIcon.addMainButton(Images.App.getInstance().chat(), new Command() {
                public void execute() {
                    if (dialog.isVisible()) {
                        dialog.hide();
                    } else {
                        dialog.show();
                    }
                }
            });
            presenter.attachIconToBottomBar(bottomIcon);
        }
    }

    public void setSendEnabled(final boolean enabled) {
        if (enabled) {
            sendBtn.enable();
        } else {
            sendBtn.disable();
        }
    }

    public void setSubject(final String text) {
        subject.setValue(text);
    }

    public void setSubjectEditable(final boolean editable) {
        subject.setDisabled(editable);
    }

    public void showUserList(final RoomUserListView view) {
        Integer index = userListToIndex.get(view);
        roomUsersDeckPanel.showWidget(index.intValue());
        usersPanel.setActiveItem(1);
    }

    public void addRoomUsersPanel(final RoomUserListView view) {
        roomUsersDeckPanel.add((Widget) view);
        userListToIndex.put(view, new Integer(roomUsersDeckPanel.getWidgetIndex((Widget) view)));
    }

    public void removeRoomUsersPanel(final RoomUserListView view) {
        Integer index = userListToIndex.get(view);
        roomUsersDeckPanel.remove(index.intValue());
        userListToIndex.remove(view);
    }

    public void clearInputText() {
        input.reset();
    }

    public void setInputText(final String text) {
        input.setRawValue(text);
    }

    public String getInputText() {
        return input.getValueAsString();
    }

    public void addPresenceBuddy(final String name, final String title, final int status) {
        // StackSubItemAction[] actions = {
        // new StackSubItemAction(Images.App.getInstance().chat(),
        // Kune.I18N.t("Start a chat with this person"),
        // WorkspaceEvents.GOTO), StackSubItemAction.DEFAULT_VISIT_GROUP };
        // usersStack.addStackSubItem(MYBUDDIES, getStatusIcon(status), name,
        // title, actions, presenter);
    }

    public void removePresenceBuddy(final String name) {
        // usersStack.removeStackSubItem(MYBUDDIES, name);
    }

    private void createLayout() {
        dialog = new BasicDialog(Kune.I18N.t("Chat rooms"), false, false, 600, 415, 300, 300);
        dialog.setBorder(false);
        dialog.setCollapsible(true);
        dialog.setIconCls("chat-icon");
        sendBtn = new Button(Kune.I18N.t("Send"));
        sendBtn.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.onSend();
            }
        });
        dialog.addButton(sendBtn);
        dialog.setLayout(new BorderLayout());

        Panel northPanel = new Panel();
        northPanel.setHeight(54);
        northPanel.add(createSubjectPanel());
        northPanel.setBorder(false);
        BorderLayoutData northData = new BorderLayoutData(RegionPosition.NORTH);
        dialog.add(northPanel, northData);

        Panel southPanel = new Panel();
        southPanel.setHeight(75);
        southPanel.add(createInputPanel());
        southPanel.setBorder(false);
        BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
        southData.setSplit(true);
        dialog.add(southPanel, southData);

        Panel eastPanel = new Panel(Kune.I18N.t("Users"));
        eastPanel.setWidth(150);
        eastPanel.setCollapsible(true);
        eastPanel.setBorder(false);
        eastPanel.setLayout(new FitLayout());
        eastPanel.add(createUsersPanel());
        BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);

        eastData.setMinSize(100);
        eastData.setMaxSize(250);
        eastData.setSplit(true);
        eastData.setMargins(3, 3, 0, 3);
        eastData.setCMargins(3, 3, 3, 3);
        eastData.setSplit(true);
        dialog.add(eastPanel, eastData);

        centerPanel = new TabPanel();
        centerPanel.setBorder(true);
        centerPanel.setDeferredRender(false);
        centerPanel.setActiveTab(0);
        centerPanel.setEnableTabScroll(true);
        centerPanel.setAutoScroll(true);
        BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);
        dialog.add(centerPanel, centerData);

        createListeners();

    }

    private void createListeners() {
        dialog.addListener(new WindowListenerAdapter() {

            // public boolean doBeforeHide(final Component component) {
            // if (centralLayout.getNumPanels() > 0) {
            // if (presenter.isCloseAllConfirmed()) {
            // return true;
            // } else {
            // MessageBox.confirm(Kune.I18N.t("Confirm"), Kune.I18N
            // .t("Are you sure you want to exit all the rooms?"), new
            // MessageBox.ConfirmCallback() {
            // public void execute(final String btnID) {
            // if (btnID.equals("yes")) {
            // presenter.closeAllRooms();
            // } else {
            // presenter.onCloseAllNotConfirmed();
            // }
            // }
            // });
            // return false;
            // }
            // }
            // return true;
            // }

            public void onCollapse(final Panel panel) {
                // dialog.hide();
            }

            public void onClose(final Panel panel) {
                Log.debug("Close chat dialog");
            }

        });

        centerPanel.addListener(new PanelListenerAdapter() {
            public boolean doBeforeRemove(final Container self, final Component component) {
                final String panelId = component.getId();
                final RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panelId);
                if (presenter.isCloseAllConfirmed() || roomPresenter.isCloseConfirmed()) {
                    panelIdToRoom.remove(panelId);
                    // panelIdToTabId.remove(panelId);
                    removeRoomUsersPanel(roomPresenter.getUsersListView());
                    presenter.closeRoom(roomPresenter);
                    return true;
                } else {
                    MessageBox.confirm(Kune.I18N.t("Confirm"), Kune.I18N
                            .t("Are you sure you want to exit from this room?"), new MessageBox.ConfirmCallback() {
                        public void execute(final String btnID) {
                            RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panelId);
                            if (btnID.equals("yes")) {
                                roomPresenter.onCloseConfirmed();
                                self.remove(panelId);
                            } else {
                                roomPresenter.onCloseNotConfirmed();
                            }
                        }
                    });
                }
                return false;
            }

            public void onActivate(final Panel panel) {
                Log.debug("Panel activated: " + panel.getId());
                RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panel.getId());
                presenter.activateRoom(roomPresenter);
            }

            public void onRemove(final Container self, final Component component) {
            }

        });
    }

    private Panel createUsersPanel() {
        usersPanel = new Panel();
        usersPanel.setLayout(new AccordionLayout(true));
        usersPanel.setAutoScroll(true);
        usersPanel.setBorder(false);
        // usersStack = new IndexedStackPanelWithSubItems();
        // usersStack.setStyleName("kune-StackedDropDownPanel");
        roomUsersDeckPanel = new DeckPanel();
        roomUsersDeckPanel.addStyleName("kune-MultiRoomPanel-User");
        Panel buddiesPanel = new Panel(Kune.I18N.t("My buddies"));
        buddiesPanel.setIconCls("userf-icon");
        Panel roomUsersPanel = new Panel(Kune.I18N.t("Now in this room"));
        roomUsersPanel.setIconCls("group-icon");
        roomUsersPanel.add(roomUsersDeckPanel);
        // usersStack.addStackItem(MYBUDDIES, Kune.I18N.t("Presence of my
        // buddies"), true);
        // usersStack.add(roomUsersDeckPanel, Kune.I18N.t("Now in this room"));
        usersPanel.add(buddiesPanel);
        usersPanel.add(roomUsersPanel);
        // usersStack.setWidth("100%");
        return usersPanel;
    }

    private Toolbar createTopToolbar() {

        final Toolbar topToolbar = new Toolbar();

        statusMenu = new Menu();
        statusMenu.setShadow(true);

        statusMenu.addItem(new TextItem("<b class=\"menu-title\">" + Kune.I18N.t("Change your status") + "</b>"));

        onlineMenuItem = createStatusCheckItem(STATUS_ONLINE);
        offlineMenuItem = createStatusCheckItem(STATUS_OFFLINE);
        busyMenuItem = createStatusCheckItem(STATUS_BUSY);
        awayMenuItem = createStatusCheckItem(STATUS_AWAY);

        statusMenu.addItem(onlineMenuItem);
        statusMenu.addItem(offlineMenuItem);
        statusMenu.addItem(busyMenuItem);
        statusMenu.addItem(awayMenuItem);

        statusButton = new ToolbarMenuButton("Set status", statusMenu);
        statusButton.setTooltip(Kune.I18N.t("Set status"));

        // FIXME hardcoded
        setStatus(STATUS_ONLINE);

        topToolbar.addButton(statusButton);

        statusButton.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                statusMenu.show("chat-menu-button");
            }
        });

        topToolbar.addSeparator();

        inviteUserToRoom = new ToolbarButton();
        inviteUserToRoom.setIcon("images/group_add.png");
        inviteUserToRoom.setCls("x-btn-icon");
        inviteUserToRoom.setTooltip(Kune.I18N.t("Invite another user to this chat room"));

        ToolbarButton buddyAdd = new ToolbarButton();
        buddyAdd.setIcon("images/user_add.png");
        buddyAdd.setCls("x-btn-icon");
        buddyAdd.setTooltip(Kune.I18N.t("Add a new buddy"));

        final EntityLiveSearchListener inviteUserToRoomListener = new EntityLiveSearchListener() {
            public void onSelection(String shortName, String longName) {
                presenter.inviteUserToRoom(shortName, longName);
            }
        };

        final EntityLiveSearchListener addBuddyListener = new EntityLiveSearchListener() {
            public void onSelection(String shortName, String longName) {
                presenter.addBuddy(shortName, longName);
            }
        };

        buddyAdd.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_USERLIVESEARCH, addBuddyListener);
            }
        });

        inviteUserToRoom.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_USERLIVESEARCH, inviteUserToRoomListener);
            }
        });

        topToolbar.addButton(buddyAdd);

        topToolbar.addButton(inviteUserToRoom);

        return topToolbar;
    }

    private CheckItem createStatusCheckItem(final int status) {
        CheckItem checkItem = new CheckItem();
        checkItem.setText(getStatusText(status));
        checkItem.setGroup("chatstatus");

        checkItem.addListener(new BaseItemListenerAdapter() {
            public void onClick(final BaseItem item, final EventObject e) {
                presenter.onStatusSelected(status);
            }
        });
        return checkItem;
    }

    private Panel createSubjectPanel() {
        FormPanel subjectForm = createGenericInputForm();

        subject = new TextArea();
        // TODO: Fixed in gwt-ext 2.0.3 TextArea.setEnterIsSpecial
        subject.addListener(new TextFieldListenerAdapter() {
            public void onSpecialKey(final Field field, final EventObject e) {
                Log.debug("Special key: " + e.getKey());
                if (e.getKey() == 13) {
                    presenter.changeRoomSubject(field.getValueAsString());
                    e.stopEvent();
                }
            }
        });
        subject.addListener(new FieldListenerAdapter() {
            public void onSpecialKey(final Field field, final EventObject e) {
                Log.debug("Special key: " + e.getKey());
                if (e.getKey() == 13) {
                    presenter.changeRoomSubject(field.getValueAsString());
                    e.stopEvent();
                }
            }
        });

        final Toolbar topToolbar = createTopToolbar();

        subjectForm.add(subject);

        Panel northPanel = new Panel();
        northPanel.setLayout(new FitLayout());
        northPanel.setTopToolbar(topToolbar);
        subject.setWidth("100%");
        subject.setHeight("100%");
        northPanel.doLayout();

        northPanel.addStyleName("kune-MultiRoomPanel-Subject");

        return northPanel;
    }

    private Panel createInputPanel() {
        FormPanel inputForm = createGenericInputForm();
        input = new TextArea();
        input.addListener(new TextFieldListenerAdapter() {
            public void onSpecialKey(final Field field, final EventObject e) {
                if (e.getKey() == EventObject.RETURN) {
                    presenter.onSend();
                    e.stopEvent();
                }
            }
        });
        input.addListener(new FieldListenerAdapter() {
            public void onSpecialKey(final Field field, final EventObject e) {
                if (e.getKey() == EventObject.RETURN) {
                    presenter.onSend();
                    e.stopEvent();
                }
            }
        });

        inputForm.add(input);

        /* Input toolbar */

        final Toolbar inputToolbar = new Toolbar();
        ToolbarButton emoticonIcon = new ToolbarButton();
        emoticonIcon.setIcon("images/smile.png");
        emoticonIcon.setCls("x-btn-icon x-btn-focus");
        emoticonIcon.setTooltip(Kune.I18N.t("Insert a emoticon"));

        emoticonIcon.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                showEmoticonPalette(e.getXY()[0], e.getXY()[1]);
            }
        });
        inputToolbar.addButton(emoticonIcon);
        inputToolbar.addSeparator();

        Panel southPanel = new Panel();
        southPanel.setLayout(new FitLayout());
        southPanel.setTopToolbar(inputToolbar);
        southPanel.add(inputForm);
        input.setWidth("100%");
        input.setHeight("100%");
        southPanel.doLayout();

        return southPanel;
    }

    private FormPanel createGenericInputForm() {
        FormPanel form = new FormPanel();
        form.setLayout(new FitLayout());
        form.setHideLabels(true);
        form.setBorder(false);
        return form;
    }

    private void showEmoticonPalette(final int x, final int y) {
        if (emoticonPalettePanel == null) {
            emoticonPalettePanel = new EmoticonPalettePanel(new EmoticonPaletteListener() {
                public void onEmoticonSelected(final String emoticonText) {
                    input.setRawValue(input.getText() + " " + emoticonText + " ");
                    emoticonPopup.hide();
                }
            });
        }
        emoticonPopup = new PopupPanel(true);
        emoticonPopup.setVisible(false);
        emoticonPopup.show();
        emoticonPopup.setPopupPosition(x + 2, y - 160);
        emoticonPopup.setWidget(emoticonPalettePanel);
        emoticonPopup.setVisible(true);
    }

    private AbstractImagePrototype getStatusIcon(final int status) {
        switch (status) {
        case STATUS_ONLINE:
            return icons.online();
        case STATUS_OFFLINE:
            return icons.offline();
        case STATUS_BUSY:
            return icons.busy();
        case STATUS_INVISIBLE:
            return icons.invisible();
        case STATUS_XA:
            return icons.extendedAway();
        case STATUS_AWAY:
            return icons.away();
        case STATUS_MESSAGE:
            return icons.message();
        default:
            throw new IndexOutOfBoundsException("Xmpp status unknown");

        }
    }

    public void setStatus(final int status) {
        switch (status) {
        case STATUS_ONLINE:
            onlineMenuItem.setChecked(true);
            break;
        case STATUS_OFFLINE:
            offlineMenuItem.setChecked(true);
            break;
        case STATUS_BUSY:
            busyMenuItem.setChecked(true);
            break;
        case STATUS_AWAY:
            awayMenuItem.setChecked(true);
            break;
        default:
            break;
        }
        String icon = getStatusIcon(status).getHTML();
        statusButton.setText(icon);
    }

    private String getStatusText(final int status) {
        String textLabel;

        switch (status) {
        case STATUS_ONLINE:
            textLabel = Kune.I18N.t("online");
            break;
        case STATUS_OFFLINE:
            textLabel = Kune.I18N.t("offline");
            break;
        case STATUS_BUSY:
            textLabel = Kune.I18N.t("busy");
            break;
        case STATUS_INVISIBLE:
            textLabel = Kune.I18N.t("invisible");
            break;
        case STATUS_XA:
            textLabel = Kune.I18N.t("extended away");
            break;
        case STATUS_AWAY:
            textLabel = Kune.I18N.t("away");
            break;
        default:
            throw new IndexOutOfBoundsException("Xmpp status unknown");
        }
        return getStatusIcon(status).getHTML() + textLabel;
    }

}
