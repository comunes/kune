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
package org.ourproject.kune.platf.client.ui.rte.insertspecialchar;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InsertSpecialCharDialogPanel extends AbstractTabbedDialogPanel implements InsertSpecialCharDialogView {

    private static final String INSERT_SPECIAL_CHAR_DIALOG = "iscdp-dial";
    private static final String INSERT_SPECIAL_CHAR_DIALOG_ERROR_ID = "iscdp-err";
    private final InsertSpecialCharGroup insertSpecialCharGroup;

    public InsertSpecialCharDialogPanel(final InsertSpecialCharDialogPresenter presenter,
            final NotifyLevelImages images, final I18nTranslationService i18n,
            final InsertSpecialCharGroup insertSpecialCharGroup, final RTEImgResources imgResources) {
        super(INSERT_SPECIAL_CHAR_DIALOG, i18n.t("Insert Special Character"), 495, HEIGHT + 90, 495, HEIGHT + 90, true,
                images, INSERT_SPECIAL_CHAR_DIALOG_ERROR_ID);
        super.setIconCls("k-specialchars-icon");
        this.insertSpecialCharGroup = insertSpecialCharGroup;
        final Button close = new Button(i18n.t("Close"));
        close.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                hide();
            }
        });
        addButton(close);
    }

    @Override
    public void createAndShow() {
        insertSpecialCharGroup.createAll();
        super.createAndShow();
    }
}
