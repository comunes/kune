/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.chat.client.ctx.room;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;

import com.calclab.emiteuimodule.client.dialog.BasicDialogExtended;
import com.calclab.emiteuimodule.client.dialog.BasicDialogListener;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class AddRoomPanel implements AddRoomView {

    public static final String CANCEL_ID = "k-ct-arp-cb";
    public static final String ADD_ROOM_ID = "k-ct-arp-ab";
    public static final String ROOM_NAME_ID = "k-ct-arp-rfield";
    private final AddRoomPresenter presenter;
    private BasicDialogExtended dialog;
    private final I18nTranslationService i18n;
    private FormPanel formPanel;
    private TextField roomName;

    public AddRoomPanel(final AddRoomPresenter presenter, final I18nTranslationService i18n) {
        this.i18n = i18n;
        this.presenter = presenter;
    }

    public void reset() {
        formPanel.getForm().reset();
    }

    public void show() {
        if (dialog == null) {
            dialog = new BasicDialogExtended(i18n.t("Add a chat room"), false, false, 330, 130, "chat-icon",
                    i18n.tWithNT("Add", "used in button"), ADD_ROOM_ID, i18n.tWithNT("Cancel", "used in button"),
                    CANCEL_ID, new BasicDialogListener() {
                        public void onCancelButtonClick() {
                            dialog.hide();
                            reset();
                        }

                        public void onFirstButtonClick() {
                            doAddRoom();
                        }
                    });
            dialog.setResizable(false);
            createForm();

            // TODO define a UI Extension Point here
        }
        dialog.show();
        roomName.focus();
    }

    protected void doAddRoom() {
        roomName.validate();
        if (formPanel.getForm().isValid()) {
            DeferredCommand.addCommand(new Command() {
                public void execute() {
                    presenter.addRoom(roomName.getValueAsString());
                    reset();
                }
            });
            dialog.hide();
        }
    }

    private void createForm() {
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setAutoScroll(false);

        formPanel.setWidth(333);
        formPanel.setLabelWidth(100);
        formPanel.setPaddings(10);

        roomName = new TextField(i18n.t("Room Name"), "name", 150);
        roomName.setAllowBlank(false);
        roomName.setValidationEvent(false);
        roomName.setRegex(TextUtils.UNIX_NAME);
        roomName.setRegexText(i18n.t("The name must contain only characters, numbers and dashes"));
        roomName.setId(ROOM_NAME_ID);
        formPanel.add(roomName);

        dialog.add(formPanel);
        roomName.addListener(new FieldListenerAdapter() {
            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == 13) {
                    doAddRoom();
                }
            }
        });
    }
}
