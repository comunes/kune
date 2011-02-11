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
package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.app.UserOptionsCollection;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;

import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.i18n.I18nTranslationService;

public class UserOptionsPanel extends AbstractTabbedDialogPanel implements UserOptionsView {

    public static final String USER_OP_PANEL_ID = "k-uop-diagpan";
    public static final String USER_OPTIONS_ERROR_ID = "k-uop-err-mess";

    private final UserOptionsCollection userPreferencesGroup;

    public UserOptionsPanel(final EntityOptions presenter, final EntityHeader entityHeader,
            final I18nTranslationService i18n, final NotifyLevelImages images,
            final UserOptionsCollection userOptionsGroup) {
        super(USER_OP_PANEL_ID, "", 400, HEIGHT + 80, 400, HEIGHT + 80, false, images, USER_OPTIONS_ERROR_ID);
        this.userPreferencesGroup = userOptionsGroup;
        super.setIconCls("k-options-icon");
        super.setTitle(i18n.t(CoreMessages.USER_OPTIONS_DIALOG_TITLE));
    }

    @Override
    public void createAndShow() {
        createAndShowItImpl();
    }

    private void createAndShowItImpl() {
        userPreferencesGroup.createAll();
        super.createAndShow();
    }
}
