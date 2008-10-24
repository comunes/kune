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

import com.calclab.emiteuimodule.client.dialog.BasicDialogListener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class BasicDialogExtended extends BasicDialog {

    public BasicDialogExtended(final String title, final boolean modal, final boolean autoscroll, final int width,
            final int heigth, final String icon, final String firstButtonTitle, final String cancelButtonTitle,
            final BasicDialogListener listener) {
        this(title, modal, autoscroll, width, heigth, icon, firstButtonTitle, Ext.generateId(), cancelButtonTitle,
                Ext.generateId(), listener);
    }

    public BasicDialogExtended(final String title, final boolean modal, final boolean autoscroll, final int width,
            final int heigth, final String icon, final String firstButtonTitle, final String firstButtonId,
            final String cancelButtonTitle, final String cancelButtonId, final BasicDialogListener listener) {
        super(title, modal, autoscroll, width, heigth);
        setLayout(new FitLayout());
        setCollapsible(false);
        setButtonAlign(Position.RIGHT);
        setIconCls(icon);

        final Button firstButton = new Button(firstButtonTitle);
        firstButton.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                listener.onFirstButtonClick();
            }
        });

        firstButton.setTabIndex(3);
        firstButton.setId(firstButtonId);

        final Button cancel = new Button(cancelButtonTitle);
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                listener.onCancelButtonClick();
            }
        });
        cancel.setTabIndex(4);
        cancel.setId(cancelButtonId);
        addButton(firstButton);
        addButton(cancel);
    }
}
