/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.dialogs;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

@Deprecated
public class InfoDialog extends BasicDialog {

    private final Panel mainPanel;

    public InfoDialog(final String dialogId, final String title, final String okButtonText, final String okButtonId,
            final boolean modal, final boolean autoScroll, final int width, final int height) {
        super(dialogId, title, modal, autoScroll, width, height);
        final Button okButton = new Button(okButtonText);
        okButton.setId(okButtonId);
        okButton.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                destroy();
            }
        });
        mainPanel = new Panel();
        mainPanel.setBorder(false);
        mainPanel.setHeader(false);
        mainPanel.setPaddings(20);
        super.add(mainPanel);
        addButton(okButton);
    }

    public InfoDialog(final String dialogId, final String title, final String header, final String text,
            final String okButtonText, final String okButtonId, final boolean modal, final boolean autoScroll,
            final int width, final int height) {
        this(dialogId, title, header, text, okButtonText, okButtonId, modal, autoScroll, width, height, false);
    }

    public InfoDialog(final String dialogId, final String title, final String header, final String text,
            final String okButtonText, final String okButtonId, final boolean modal, final boolean autoScroll,
            final int width, final int height, final boolean isHtml) {
        this(dialogId, title, okButtonText, okButtonId, modal, autoScroll, width, height);

        final Label headerLabel = new Label(header);
        Widget textLabel;
        if (isHtml) {
            textLabel = new HTML(text);
        } else {
            textLabel = new Label(text);
        }

        mainPanel.add(headerLabel);
        mainPanel.add(textLabel);

        headerLabel.addStyleName("k-infod-head");
    }

    public void add(final Panel panel) {
        mainPanel.add(panel);
    }

    public void setMainPanelPaddings(final int padding) {
        mainPanel.setPaddings(padding);
    }
}
