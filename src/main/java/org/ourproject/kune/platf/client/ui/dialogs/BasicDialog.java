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

import com.gwtext.client.core.Ext;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Window;

public class BasicDialog extends Window {

    public BasicDialog(final String caption, final boolean modal) {
        this(Ext.generateId(), caption, modal, false);
    }

    public BasicDialog(final String caption, final boolean modal, final boolean autoScroll, final int width,
            final int height) {
        this(Ext.generateId(), caption, modal, autoScroll, width, height, width, height);
    }

    public BasicDialog(final String caption, final boolean modal, final boolean autoScroll, final int width,
            final int height, final int minWidth, final int minHeight) {
        this(Ext.generateId(), caption, modal, autoScroll, width, height, minWidth, minHeight);
    }

    public BasicDialog(final String id, final String caption, final boolean modal) {
        this(id, caption, modal, false);
    }

    public BasicDialog(final String id, final String caption, final boolean modal, final boolean autoScroll) {
        setId(id);
        setBorder(false);
        setAutoWidth(true);
        // Param values
        setTitle(caption);
        setModal(modal);
        setAutoScroll(autoScroll);
        // Def values
        setShadow(true);
        setPlain(true);
        setClosable(true);
        setCollapsible(true);
        setResizable(true);
        setCloseAction(Window.HIDE);
        setButtonAlign(Position.RIGHT);
    }

    public BasicDialog(final String id, final String caption, final boolean modal, final boolean autoScroll,
            final int width, final int height) {
        this(id, caption, modal, autoScroll, width, height, width, height);
    }

    public BasicDialog(final String id, final String caption, final boolean modal, final boolean autoScroll,
            final int width, final int height, final int minWidth, final int minHeight) {
        this(id, caption, modal, autoScroll);
        setAutoWidth(false);
        // Param values
        setWidth(width);
        setHeight(height);
        setMinWidth(minWidth);
        setMinHeight(minHeight);
    }

}
