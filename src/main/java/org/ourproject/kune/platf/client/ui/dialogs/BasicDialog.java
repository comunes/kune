/*
 *
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

package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.ui.CustomButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class BasicDialog {

    private final LayoutDialog dialog;

    public BasicDialog(final String caption, final String buttonText, final boolean modal, final int width,
            final int height, final int minWidth, final int minHeight, final ClickListener listener) {

        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                // Param values
                setTitle(caption);
                setModal(modal);
                setWidth(width);
                setHeight(height);
                setMinWidth(minWidth);
                setMinHeight(minHeight);

                // Def values
                setCollapsible(false);
                setShadow(true);
            }
        }, new LayoutRegionConfig());

        dialog.addButton(new CustomButton(buttonText, listener).getButton());

    }

    public BasicDialog(final String caption, final String buttonText, final boolean modal, final int width,
            final int height, final ClickListener listener) {
        this(caption, buttonText, modal, width, height, width, height, listener);
    }

    public void add(final Widget widget) {
        BorderLayout layout = dialog.getLayout();
        ContentPanel contentPanel = new ContentPanel();
        contentPanel.add(widget);
        layout.add(contentPanel);
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
