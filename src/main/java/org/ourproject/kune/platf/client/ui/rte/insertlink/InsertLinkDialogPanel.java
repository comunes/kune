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
package org.ourproject.kune.platf.client.ui.rte.insertlink;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InsertLinkDialogPanel extends AbstractTabbedDialogPanel implements InsertLinkDialogView {
    public static final String TEXT_EDT_INSERT_DIALOG = "k-ted-iep-dialog";
    public static final String TEXT_EDT_INSERT_DIALOG_ERROR_ID = "k-ted-iep-dialog-err";
    private final InsertLinkGroup textEditorInsertElementGroup;

    public InsertLinkDialogPanel(final InsertLinkDialogPresenter presenter, Images images, I18nTranslationService i18n,
            final InsertLinkGroup textEditorInsertElementGroup) {
        super(TEXT_EDT_INSERT_DIALOG, i18n.tWithNT("Insert a link",
                "Option in a text editor to insert links and other elements"), 380, HEIGHT + 100, 380, HEIGHT + 100,
                true, images, TEXT_EDT_INSERT_DIALOG_ERROR_ID);
        super.setIconCls("k-link-icon");
        this.textEditorInsertElementGroup = textEditorInsertElementGroup;
        super.addHideListener(new Listener0() {
            public void onEvent() {
                textEditorInsertElementGroup.resetAll();
            }
        });

        Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onInsert();
            }
        });

        Button cancel = new Button(i18n.t("Cancel"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onCancel();
            }
        });
        addButton(insert);
        addButton(cancel);

    }

    @Override
    public void createAndShow() {
        textEditorInsertElementGroup.createAll();
        super.createAndShow();
    }
}