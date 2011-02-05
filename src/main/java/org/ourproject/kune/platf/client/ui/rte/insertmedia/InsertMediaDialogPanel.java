/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;

import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InsertMediaDialogPanel extends AbstractTabbedDialogPanel implements InsertMediaDialogView {

    private static final String INSERT_MEDIA_DIALOG = "k-imdp-dialog";
    private static final String INSERT_MEDIA_DIALOG_ERROR_ID = "k-imdp-dialog-err";
    private final InsertMediaGroup insertMediaGroup;

    public InsertMediaDialogPanel(final InsertMediaDialogPresenter presenter, final I18nTranslationService i18n,
            final NotifyLevelImages images, final InsertMediaGroup insertMediaGroup) {
        super(INSERT_MEDIA_DIALOG, i18n.t("Insert Media (audio/video)"), 390, HEIGHT + 100, 390, HEIGHT + 100, true,
                images, INSERT_MEDIA_DIALOG_ERROR_ID);
        super.setIconCls("k-film-icon");
        this.insertMediaGroup = insertMediaGroup;

        super.addHideListener(new Listener0() {
            @Override
            public void onEvent() {
                insertMediaGroup.resetAll();
            }
        });

        final Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                presenter.onInsert();
            }
        });

        final Button cancel = new Button(i18n.t("Cancel"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                presenter.onCancel();
            }
        });
        addButton(insert);
        addButton(cancel);
    }

    @Override
    public void createAndShow() {
        insertMediaGroup.createAll();
        super.createAndShow();
    }
}
