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
package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.i18n.I18nTranslationService;

public class UserOptionsLogoPanel extends EntityOptionsLogoPanel {
    public static final String PANEL_ID = "k-uolp-pan";
    public static final String BUTTON_ID = "k-uolp-sendb";

    public UserOptionsLogoPanel(final EntityOptionsLogoPresenter presenter, final WorkspaceSkeleton wskel,
            final I18nTranslationService i18n) {
        super(presenter, wskel, i18n, PANEL_ID, BUTTON_ID, FileConstants.USER_LOGO_FIELD);
    }

}
