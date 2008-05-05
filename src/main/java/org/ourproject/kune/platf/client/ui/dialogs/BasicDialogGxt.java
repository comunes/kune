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

package org.ourproject.kune.platf.client.ui.dialogs;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Window;

public class BasicDialogGxt extends Window {

    public BasicDialogGxt(final String caption, final boolean modal) {
	this(caption, modal, Scroll.AUTO);
    }

    public BasicDialogGxt(final String caption, final boolean modal, final Scroll scroll) {
	setAutoWidth(true);
	// Param values
	setTitle(caption);
	setModal(modal);
	setScrollMode(scroll);
	// Def values
	setShadow(true);
	setPlain(true);
	setClosable(true);
	setCollapsible(true);
	setResizable(true);
	setButtonAlign(HorizontalAlignment.RIGHT);
    }

    public BasicDialogGxt(final String caption, final boolean modal, final Scroll scroll, final int width,
	    final int height) {
	this(caption, modal, scroll, width, height, width, height);
    }

    public BasicDialogGxt(final String caption, final boolean modal, final Scroll scroll, final int width,
	    final int height, final int minWidth, final int minHeight) {
	this(caption, modal, scroll);
	setAutoWidth(false);
	// Param values
	setWidth(width);
	setHeight(height);
	setMinWidth(minWidth);
	setMinHeight(minHeight);
    }

}
