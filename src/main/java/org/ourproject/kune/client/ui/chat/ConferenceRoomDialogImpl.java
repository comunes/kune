/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.client.ui.chat;

import java.util.Iterator;
import java.util.Vector;

import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.CustomPushButton;
import org.ourproject.kune.client.ui.ExpandPanel;
import org.ourproject.kune.client.ui.HorizontalLine;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConferenceRoomDialogImpl extends Composite implements ConferenceRoomDialog {

    private Vector userList = null;

    private VerticalPanel generalVP = null;

    private HorizontalPanel subjectHP = null;

    private String chatroomSubject = null;

    // private EditableLabel subjectLabel = null;

    private Label subjectLabel = null;

    private VerticalPanel contentVP = null;

    private HorizontalPanel conversationUsersHP = null;

    private VerticalPanel conversationUsersVP = null;

    private ScrollPanel conversationScroll = null;

    private VerticalPanel conversationVP = null;

    private VerticalPanel usersVP = null;

    private VerticalPanel inputOptionsVP = null;

    private TextArea inputTextArea = null;

    private HorizontalPanel optionsHP = null;

    private Image optionsImage = null;

    private Hyperlink optionsHyperlink = null;

    private CustomPushButton sendButton = null;

    private ConferenceRoom controller;

    private ExpandPanel expandOptions;

    public ConferenceRoomDialogImpl(ConferenceRoom controller) {
        super();
        this.controller = controller;
        initialize();
        layout();
        setProperties();
    }

    protected void initialize() {
        userList = new Vector();
        generalVP = new VerticalPanel();
        subjectHP = new HorizontalPanel();
        subjectLabel = new Label();
        // subjectLabel = new EditableLabel ("", new ChangeListener() {
        // public void onChange(Widget sender) {
        // // TODO Only for tests (put this out of ChatroomDialog) ...
        // ServiceXmppMucServiceManager.INSTANCE.requestChangeSubject(subjectLabel.getText(),
        // new ServiceXmppMucIResponse() {
        // public void accept(Object result) {
        // //TODO
        // SiteMessageDialog.get().setMessageInfo("Success in subject change");
        // }
        // public void failed(Throwable caught) {
        // //TODO
        // SiteMessageDialog.get().setMessageError("Error on subject change");
        // }
        // });
        // }
        // }, Trans.constants().Change(), Trans.constants().Cancel());
        contentVP = new VerticalPanel();
        conversationUsersVP = new VerticalPanel();
        conversationUsersHP = new HorizontalPanel();
        conversationScroll = new ScrollPanel();
        conversationVP = new VerticalPanel();
        conversationVP.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        usersVP = new VerticalPanel();
        inputOptionsVP = new VerticalPanel();
        inputTextArea = new TextArea();
        sendButton = new CustomPushButton(Trans.constants().Send(), CustomPushButton.SMALL, new ClickListener() {
            public void onClick(Widget arg0) {
                if (sendButton.isEnabled()) {
                    controller.onSend(inputTextArea.getText());
                }
            }
        });
        optionsHP = new HorizontalPanel();
        optionsHP.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        expandOptions = new ExpandPanel(ExpandPanel.HORIZ);
        optionsImage = new Image();
        optionsHyperlink = new Hyperlink();
    }

    protected void layout() {
        initWidget(generalVP);
        generalVP.add(subjectHP);
        generalVP.add(contentVP);
        subjectHP.add(subjectLabel);
        conversationUsersVP.add(conversationUsersHP);
        contentVP.add(conversationUsersVP);
        contentVP.add(inputOptionsVP);
        conversationScroll.add(conversationVP);
        conversationUsersHP.add(conversationScroll);
        conversationUsersHP.add(usersVP);
        inputOptionsVP.add(inputTextArea);
        inputOptionsVP.add(optionsHP);
        optionsHP.add(new BorderPanel(sendButton, CustomPushButton.VERSPACESMALL, 0));
        optionsHP.add(expandOptions);
        optionsHP.add(new BorderPanel(optionsImage, 0, 5, 0, 0));
        optionsHP.add(optionsHyperlink);
    }

    protected void setProperties() {
        generalVP.setBorderWidth(0);
        generalVP.setSpacing(5);
        generalVP.setWidth("100%");
        generalVP.setHeight("100%");

        subjectHP.setBorderWidth(0);
        subjectHP.setSpacing(0);
        subjectHP.setStyleName("kune-chatroom-subject");
        subjectHP.setWidth("100%");
        subjectHP.setHeight("20");
        subjectLabel.setWidth("100%");
        // subjectLabel.setVisibleLength(65);

        conversationUsersVP.setWidth("100%");
        conversationUsersVP.setHeight("100%");
        conversationUsersVP.setBorderWidth(0);
        conversationUsersVP.setSpacing(0);

        contentVP.addStyleName("kune-chatroom-content-outter");
        contentVP.setStyleName("kune-chatroom-content-outter");
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=1258&can=1&q=verticalsplitpanel
        //contentSP.setSplitPosition("150");
        contentVP.setWidth("100%");
        //contentVP.setHeight("100%");

        conversationUsersHP.addStyleName("kune-chatroom-content-inner-up");
        conversationUsersHP.setStyleName("kune-chatroom-content-inner-up");
        //conversationUsersSP.setSplitPosition("330");
        conversationUsersHP.setCellWidth(usersVP, "150");
        conversationUsersHP.setWidth("100%");
        conversationUsersHP.setHeight("100%");
        conversationUsersHP.setCellHeight(usersVP, "100%");
        conversationUsersHP.setCellHeight(conversationVP, "100%");

        conversationVP.addStyleName("kune-chatroom-content-conversation");
        conversationVP.setStyleName("kune-chatroom-content-conversation");
        conversationVP.setBorderWidth(0);
        conversationVP.setSpacing(0);
        conversationVP.setWidth("100%");
        conversationVP.setHeight("100%");

        usersVP.setBorderWidth(0);
        usersVP.setSpacing(0);
        usersVP.addStyleName("kune-chatroom-content-users");
        usersVP.setStyleName("kune-chatroom-content-users");
        usersVP.setWidth("150");
        usersVP.setHeight("100%");

        inputOptionsVP.setBorderWidth(0);
        inputOptionsVP.setSpacing(0);
        inputOptionsVP.addStyleName("kune-chatroom-content-inner-down");
        inputOptionsVP.setStyleName("kune-chatroom-content-inner-down");
        inputOptionsVP.setWidth("100%");

        inputTextArea.setStyleName("kune-chatroom-content-inner");
        inputTextArea.setHeight("100%");
        inputTextArea.setWidth("100%");
        inputTextArea.addKeyboardListener(new KeyboardListener() {
            public void onKeyDown(Widget arg0, char arg1, int arg2) {
            }

            public void onKeyPress(Widget arg0, char arg1, int arg2) {
            }

            public void onKeyUp(Widget widget, char key, int mod) {
                if (widget == inputTextArea) {
                    if (!sendButton.isEnabled()) {
                        sendButton.setEnabled(true);
                    }
                    if (key == KEY_ENTER) {
                        if (mod == MODIFIER_CTRL) {
                            inputTextArea.setText(inputTextArea.getText() + "\n");
                        }
                        else {
                            controller.onSend(inputTextArea.getText());
                        }
                    }
                }

            }
        });

        sendButton.setEnabled(false); // on init, not enabled

        optionsHP.setBorderWidth(0);
        optionsHP.setSpacing(0);
        optionsHP.setCellWidth(expandOptions, "100%");

        Img.ref().kunePreferences().applyTo(optionsImage);

        optionsHyperlink.setText(Trans.constants().Options());
    }

    public void addUser(ChatroomUser user) {
        Grid userGrid = new Grid(1, 2);
        Label userNameLabel = new Label(user.getNickName());
        Image userModeratorIcon = new Image();

        userList.add(user);
        if (user.getIsModerator()) {
            Img.ref().bulletStar().applyTo(userModeratorIcon);
        } else {
            Img.ref().bulletBlack().applyTo(userModeratorIcon);
        }
        userGrid.setWidget(0, 0, userModeratorIcon);
        userGrid.setWidget(0, 1, userNameLabel);
        userGrid.setBorderWidth(0);
        userGrid.setCellSpacing(0);
        usersVP.add(userGrid);
    }

    public void addToConversation(String nick, HTML chat) {
        HorizontalPanel userChat = new HorizontalPanel();

        userChat.setBorderWidth(0);
        userChat.setSpacing(0);
        userChat.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

        userChat.add(new HTML("<b>" + nick + "</b>:&nbsp;"));
        userChat.add(chat);

        conversationVP.add(userChat);
        conversationScroll.ensureVisible(userChat);
    }

    public void addTimeDelimiter(String datetime) {
        HorizontalPanel hp = new HorizontalPanel();
        HorizontalLine hr = new HorizontalLine();
        hp.add(new Label(datetime));
        hp.add(hr);
        hp.setWidth("100%");
        hp.setCellWidth(hr, "100%");
        conversationVP.add(hp);
        hp.setStyleName("kune-chatroom-delimiter");
    }

    public void clearConversation() {
        conversationVP.clear();
    }

    public void delUser(ChatroomUser userToDel) {
        int pos = 0;
        for (Iterator it = userList.iterator(); it.hasNext();) {
            ChatroomUser user = (ChatroomUser) it.next();
            if (user.getNickName() == userToDel.getNickName()) {
                usersVP.remove(pos);
                userList.remove(pos);
                return;
            }
            pos += 1;
        }
        throw new IllegalStateException("ChatroomUser not found");
    }

    public void permitSubjectChange(boolean permit) {
        // TODO
        // subjectLabel.setEditable(permit);
    }

    public void setSubject(String chatroomSubject) {
        this.chatroomSubject = chatroomSubject;
        subjectLabel.setText(this.chatroomSubject);
    }

    public void setUsers(Vector userList) {
        clearUsers();
        for (Iterator it = userList.iterator(); it.hasNext();) {
            User user = (User) it.next();
            addUser(new ChatroomUser(user.getNickName()));
        }
    }

    public void showActivityInTitle(boolean activity) {
        if (activity) {
            subjectLabel.setText("(*) " + this.chatroomSubject);
        } else {
            subjectLabel.setText(this.chatroomSubject);
        }
    }

    public void clearUsers() {
        usersVP.clear();
        userList.clear();
    }

    public int usersInChat() {
        return userList.size();
    }

    public void clearInputArea() {
        inputTextArea.setText("");
    }

    public void enableSendButton(boolean enabled) {
        sendButton.setEnabled(enabled);
    }

    public void adjustSize(int frameWidth, int frameHeight) {
        int conversationWidth = frameWidth - 190;
        int conversationHeight = frameHeight - 145 -60;
        conversationScroll.setWidth("" + conversationWidth);
        conversationScroll.setHeight("" + conversationHeight);
    }
}
