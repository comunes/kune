/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class BottomTrayIcon extends MenuBar implements View {

    public BottomTrayIcon(final String title) {
	super(true);
	KuneUiUtils.setQuickTip(this, title);
	super.addStyleDependentName("kune-IconBottomMenu-offset");
	super.addStyleName("kune-Margin-Medium-r");
	super.setStyleName("kune-IconBottomPanel");
    }

    public MenuItem addMainButton(final AbstractImagePrototype icon, final Command cmd) {
	return super.addItem(icon.getHTML(), true, cmd);
    }

}
