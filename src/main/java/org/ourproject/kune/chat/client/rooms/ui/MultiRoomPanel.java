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
import org.ourproject.kune.platf.client.ui.stacks.IndexedStackPanelWithSubItems;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchListener;
import org.ourproject.kune.workspace.client.workspace.ui.BottomTrayIcon;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.SplitButtonConfig;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextAreaConfig;
import com.gwtext.client.widgets.form.event.FieldListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.event.LayoutRegionListener;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.CheckItemConfig;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuConfig;
import com.gwtext.client.widgets.menu.TextItem;
import com.gwtext.client.widgets.menu.event.CheckItemListenerAdapter;

public class MultiRoomPanel implements MultiRoomView, View {
    private static final String MYBUDDIES = Kune.I18N.t("My buddies");

    private static final XmppIcons icons = XmppIcons.App.getInstance();

    protected static final String INPUT_FIELD = "input-area";
    private LayoutDialog dialog;
    private Button sendBtn;
    private final MultiRoomPresenter presenter;
    private LayoutRegion centralLayout;
    private TextArea subject;
    private DeckPanel roomUsersDeckPanel;
    private TextArea input;
    private final HashMap userListToIndex;
    private final HashMap panelIdToRoom;
    private final HashMap panelIdToTabId;
    private EmoticonPalettePanel emoticonPalettePanel;
    private PopupPanel emoticonPopup;
    private BottomTrayIcon bottomIcon;

    private Menu statusMenu;

    private CheckItem onlineMenuItem;

    private CheckItem offlineMenuItem;

    private CheckItem busyMenuItem;

    private CheckItem awayMenuItem;

    private ToolbarMenuButton statusButton;

    private IndexedStackPanelWithSubItems usersStack;

    private ToolbarButton inviteUserToRoom;

    private boolean statusManualChanged;

    public MultiRoomPanel(final MultiRoomPresenter presenter) {
        this.presenter = presenter;
        this.userListToIndex = new HashMap();
        panelIdToRoom = new HashMap();
        panelIdToTabId = new HashMap();
        createLayout();
    }

    public void addRoom(final Room room) {
        final BorderLayout layout = dialog.getLayout();
        layout.beginUpdate();

        ContentPanel roomPanel = (ContentPanel) room.getView();
        LayoutRegion centerRegion = layout.getRegion(LayoutRegionConfig.CENTER);
        centerRegion.add(roomPanel);

        String panelId = roomPanel.getId();
        panelIdToRoom.put(panelId, room);
        centerRegion.showPanel(panelId);
        panelIdToTabId.put(panelId, centerRegion.getTabs().getActiveTab().getId());
        // bug test: http://code.google.com/p/gwt-ext/issues/detai3754,l?id=81
        // ContentPanel panel = centerRegion.getPanel(panelId);
        // FireLog.debug("My panel id: " + panelId);
        // FireLog.debug("Region panel id: " + panel.getId());
        // FireLog.debug("Panel element: " + panel.getElement());
        // panel.add(new HTML("test"));

        layout.endUpdate();
    }

    public void highlightRoom(final Room room) {
        final BorderLayout layout = dialog.getLayout();
        LayoutRegion centerRegion = layout.getRegion(LayoutRegionConfig.CENTER);
        ContentPanel roomPanel = (ContentPanel) room.getView();
        String panelId = roomPanel.getId();
        String tabId = (String) panelIdToTabId.get(panelId);
        centerRegion.getTabs().getTab(tabId).getTextEl().highlight();
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

    public void closeRooms() {
        // dialog.destroy(true);
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
        Integer index = (Integer) userListToIndex.get(view);
        roomUsersDeckPanel.showWidget(index.intValue());
        usersStack.showStack(1);
    }

    public void addRoomUsersPanel(final RoomUserListView view) {
        roomUsersDeckPanel.add((Widget) view);
        userListToIndex.put(view, new Integer(roomUsersDeckPanel.getWidgetIndex((Widget) view)));
    }

    public void removeRoomUsersPanel(final RoomUserListView view) {
        Integer index = (Integer) userListToIndex.get(view);
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
        StackSubItemAction[] actions = {
                new StackSubItemAction(Images.App.getInstance().chat(), Kune.I18N.t("Start a chat with this person"),
                        WorkspaceEvents.GOTO), StackSubItemAction.DEFAULT_VISIT_GROUP };
        usersStack.addStackSubItem(MYBUDDIES, getStatusIcon(status), name, title, actions, presenter);
    }

    public void removePresenceBuddy(final String name) {
        usersStack.removeStackSubItem(MYBUDDIES, name);
    }

    private void createLayout() {
        // create layout regions for layout dialog

        LayoutRegionConfig north = new LayoutRegionConfig() {
            {
                setTitlebar(false);
                setInitialSize(54);
                setHideWhenEmpty(false);
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
                setMargins(5, 0, 0, 0);
            }
        };

        LayoutRegionConfig center = new LayoutRegionConfig() {
            {
                setTitlebar(false);
                setAutoScroll(true);
                setTabPosition("top");
                setCloseOnTab(true);
                setAlwaysShowTabs(true);
                setMargins(5, 0, 0, 0);
                setHideWhenEmpty(true);
            }
        };

        LayoutRegionConfig south = new LayoutRegionConfig() {
            {
                setSplit(true);
                setHideWhenEmpty(false);
                setInitialSize(75);
            }
        };

        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(false);
                setWidth(600);
                setHeight(415);
                setShadow(true);
                setMinHeight(300);
                setMinHeight(300);
                setProxyDrag(true);
                setTitle(Kune.I18N.t("Chat rooms"));
                setCollapsible(true);
            }
        }, north, south, null, east, center);

        sendBtn = dialog.addButton(Kune.I18N.t("Send"));
        sendBtn.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                presenter.onSend();
            }
        });

        final BorderLayout layout = dialog.getLayout();

        layout.beginUpdate();

        layout.setMonitorWindowResize(true);

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
        dialog.addDialogListener(new DialogListenerAdapter() {
            public boolean doBeforeHide(final LayoutDialog dialog) {
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
                return true;
            }

            public void onHide(final LayoutDialog dialog) {
                GWT.log("Chat: hide event", null);
            }

            public void onResize(final LayoutDialog dialog, final int width, final int height) {
                if (height < 40) {
                    // There is no a minimize event, then when resize has less
                    // than this height, is equivalent to a minimize, and we put
                    // the dialog in the bottom of the screen
                    // dialog.moveTo(dialog.getAbsoluteLeft(),
                    // Window.getClientHeight() - height - 1);
                    dialog.hide();
                }
            }

            public void onShow(final LayoutDialog dialog) {
            }

        });

        centralLayout = dialog.getLayout().getRegion(LayoutRegionConfig.CENTER);

        centralLayout.addLayoutRegionListener(new LayoutRegionListener() {

            public boolean doBeforeRemove(final LayoutRegion region, final ContentPanel panel) {
                final String panelId = panel.getId();
                final RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panelId);
                if (presenter.isCloseAllConfirmed() || roomPresenter.isCloseConfirmed()) {
                    panelIdToRoom.remove(panelId);
                    panelIdToTabId.remove(panelId);
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
                                region.remove(panelId);
                            } else {
                                roomPresenter.onCloseNotConfirmed();
                            }
                        }
                    });
                }
                return false;
            }

            public void onCollapsed(final LayoutRegion region) {
                FireLog.debug("Collapse event");
            }

            public void onExpanded(final LayoutRegion region) {
            }

            public void onInvalidated(final LayoutRegion region) {
            }

            public void onPanelActivated(final LayoutRegion region, final ContentPanel panel) {
                RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panel.getId());
                presenter.activateRoom(roomPresenter);
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
        ContentPanel eastPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Users"), new ContentPanelConfig() {
            {
                setAutoScroll(true);
                setFitToContainer(true);
                setFitToFrame(true);
            }
        });
        usersStack = new IndexedStackPanelWithSubItems();
        usersStack.setStyleName("kune-StackedDropDownPanel");
        roomUsersDeckPanel = new DeckPanel();
        roomUsersDeckPanel.addStyleName("kune-MultiRoomPanel-User");
        usersStack.addStackItem(MYBUDDIES, Kune.I18N.t("Presence of my buddies"), true);
        usersStack.add(roomUsersDeckPanel, Kune.I18N.t("Now in this room"));
        eastPanel.add(usersStack);
        usersStack.setWidth("100%");
        return eastPanel;
    }

    private ContentPanel createSubjectPanel() {
        Form subjectForm = new Form(new FormConfig() {
            {
                setHideLabels(true);
                setWidth("100%");
            }
        });

        subject = new TextArea(new TextAreaConfig() {
            {
                setFieldListener(new FieldListener() {
                    public void onBlur(final Field field) {
                    }

                    public void onChange(final Field field, final Object newVal, final Object oldVal) {
                    }

                    public void onFocus(final Field field) {
                    }

                    public void onInvalid(final Field field, final String msg) {
                    }

                    public void onSpecialKey(final Field field, final EventObject e) {
                        if (e.getKey() == 13) {
                            presenter.changeRoomSubject(field.getValueAsString());
                            e.stopEvent();
                        }
                    }

                    public void onValid(final Field field) {
                    }
                });
            }
        });

        final Toolbar topToolbar = createTopToolbar();

        subjectForm.add(subject);

        subjectForm.end();
        subjectForm.render();

        subject.setWidth("100%");
        subject.setHeight("100%");

        ContentPanel northPanel = new ContentPanel(subjectForm, "", new ContentPanelConfig() {
            {
                setBackground(false);
                setFitToFrame(true);
                setToolbar(topToolbar);
            }
        });
        northPanel.addStyleName("kune-MultiRoomPanel-Subject");

        return northPanel;
    }

    private Toolbar createTopToolbar() {

        final Toolbar topToolbar = new Toolbar("chat-topbar");

        statusMenu = new Menu("xmmp-presence-menu", new MenuConfig() {
            {
                setShadow(true);
            }
        });

        statusMenu.addItem(new TextItem("<b class=\"menu-title\">" + Kune.I18N.t("Change your status") + "</b>"));

        onlineMenuItem = createStatusCheckItem(STATUS_ONLINE);
        offlineMenuItem = createStatusCheckItem(STATUS_OFFLINE);
        busyMenuItem = createStatusCheckItem(STATUS_BUSY);
        awayMenuItem = createStatusCheckItem(STATUS_AWAY);

        statusMenu.addItem(onlineMenuItem);
        statusMenu.addItem(offlineMenuItem);
        statusMenu.addItem(busyMenuItem);
        statusMenu.addItem(awayMenuItem);

        statusButton = new ToolbarMenuButton("chat-menu-button", "Set status", statusMenu, new SplitButtonConfig() {
            {
                setTooltip(Kune.I18N.t("Set status"));
                setMenu(statusMenu);
            }
        });

        setStatus(STATUS_ONLINE);

        topToolbar.addButton(statusButton);

        statusButton.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                statusMenu.show("chat-menu-button");
            }
        });

        topToolbar.addSeparator();

        inviteUserToRoom = new ToolbarButton(new ButtonConfig() {
            {
                // i18n
                setIcon("images/group_add.png");
                setCls("x-btn-icon");
                setTooltip("Invite another user to this chat room");
            }
        });
        ToolbarButton buddyAdd = new ToolbarButton(new ButtonConfig() {
            {
                setIcon("images/user_add.png");
                setCls("x-btn-icon");
                setTooltip("Add a new buddy");
            }
        });

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

        buddyAdd.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_USERLIVESEARCH, addBuddyListener, null);
            }
        });

        inviteUserToRoom.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                DefaultDispatcher.getInstance()
                        .fire(WorkspaceEvents.ADD_USERLIVESEARCH, inviteUserToRoomListener, null);
            }
        });

        topToolbar.addButton(buddyAdd);

        topToolbar.addButton(inviteUserToRoom);

        return topToolbar;
    }

    private CheckItem createStatusCheckItem(final int status) {
        return new CheckItem(new CheckItemConfig() {
            {
                setText(getStatusText(status));
                setGroup("chatstatus");
                setCheckItemListener(new CheckItemListenerAdapter() {
                    public void onCheckChange(final CheckItem item, final boolean checked) {
                        presenter.onStatusSelected(status);
                    }
                });
            }
        });
    }

    private ContentPanel createInputPanel() {
        Form inputForm = new Form(new FormConfig() {
            {
                setHideLabels(true);
                setWidth("100%");
            }
        });

        input = new TextArea(new TextAreaConfig() {
            {
                setFieldListener(new FieldListener() {
                    public void onBlur(final Field field) {
                    }

                    public void onChange(final Field field, final Object newVal, final Object oldVal) {
                    }

                    public void onFocus(final Field field) {
                    }

                    public void onInvalid(final Field field, final String msg) {
                    }

                    public void onSpecialKey(final Field field, final EventObject e) {
                        if (e.getKey() == 13) {
                            presenter.onSend();
                            e.stopEvent();
                        }
                    }

                    public void onValid(final Field field) {
                    }
                });
            }
        });

        inputForm.add(input);

        inputForm.end();
        inputForm.render();

        /* Input toolbar */

        final Toolbar inputToolbar = new Toolbar("chat-input-topbar");

        ToolbarButton emoticonIcon = new ToolbarButton(new ButtonConfig() {
            {
                setIcon("images/smile.png");
                setCls("x-btn-icon x-btn-focus");
                setTooltip(Kune.I18N.t("Insert a emoticon"));

            }
        });

        emoticonIcon.addButtonListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                showEmoticonPalette(e.getXY()[0], e.getXY()[1]);
            }
        });

        inputToolbar.addButton(emoticonIcon);

        inputToolbar.addSeparator();

        ContentPanel southPanel = new ContentPanel(inputForm, "", new ContentPanelConfig() {
            {
                setBackground(true);
                setFitToFrame(true);
                setToolbar(inputToolbar);
            }
        });

        input.setWidth("100%");
        input.setHeight("100%");

        return southPanel;
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
        // emoticonPopup.setPopupPosition(x - 170, y - 170);
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
        if (!statusManualChanged) {
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
