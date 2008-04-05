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

import org.ourproject.kune.platf.client.ui.CustomButton;
import org.ourproject.kune.platf.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Window;

public class TwoButtonsDialog {

    private final Window dialog;

    public TwoButtonsDialog(final String caption, final String firstButton, final String secondButton,
            final boolean modal, final boolean minimizable, final int width, final int height, final int minWidth,
            final int minHeight, final FormListener listener) {

        dialog = new Window();

        // Param values
        dialog.setTitle(caption);
        dialog.setModal(modal);
        dialog.setWidth(width);
        dialog.setHeight(height);
        dialog.setMinWidth(minWidth);
        dialog.setMinHeight(minHeight);
        dialog.setCollapsible(minimizable);

        // Def values
        dialog.

        setShadow(true);

        dialog.addButton(new CustomButton(firstButton, new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onAccept();
            }
        }).getButton());

        dialog.addButton(new CustomButton(secondButton, new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onCancel();
            }
        }).getButton());

    }

    public TwoButtonsDialog(final String caption, final String firstButton, final String secondButton,
            final boolean modal, final boolean minimizable, final int width, final int height,
            final FormListener listener) {
        this(caption, firstButton, secondButton, modal, minimizable, width, height, width, height, listener);

    }

    public void add(final Widget widget) {
        dialog.add(widget);
    }

    public void show() {
        dialog.show();
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
    }
}
