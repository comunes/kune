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
 \*/
package org.ourproject.kune.workspace.client.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.AbstractSiteOptionsPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Widget;

public class SiteUserOptionsPanel extends AbstractSiteOptionsPanel implements SiteUserOptionsView {

    public static final String LOGGED_USER_MENU = "kune-sump-lum";

    private final Widget separator;

    public SiteUserOptionsPanel(final SiteUserOptionsPresenter presenter, final WorkspaceSkeleton wspace,
            final GuiBindingsRegister bindings) {
        super(bindings, LOGGED_USER_MENU);
        final AbstractToolbar siteBar = wspace.getSiteBar();
        siteBar.add(btn);
        separator = siteBar.addSeparator();
    }

    public void setLoggedUserName(final String name) {
        setBtnText(name);
    }

    @Override
    public void setVisible(final boolean visible) {
        btn.setVisible(visible);
        separator.setVisible(visible);
    }
}
