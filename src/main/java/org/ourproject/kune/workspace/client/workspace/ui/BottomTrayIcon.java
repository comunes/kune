/*
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

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;

public class BottomTrayIcon extends MenuBar implements View {

    public BottomTrayIcon(final String title) {
        super(true);
        // super.setTitle(title);
        KuneUiUtils.setQuickTip(this, title);
        super.addStyleDependentName("kune-IconBottomMenu-offset");
        super.addStyleName("kune-Margin-Medium-r");
        super.setStyleName("kune-IconBottomPanel");
    }

    public void addMainButton(final AbstractImagePrototype icon, final Command cmd) {
        super.addItem(icon.getHTML(), true, cmd);
    }

}
