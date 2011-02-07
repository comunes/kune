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
package org.ourproject.kune.platf.client.ui.dialogs;

import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.layout.FormLayout;

public class DefaultTabbedForm {
    private final FormPanel formPanel;
    private final TabPanel tabPanel;

    public DefaultTabbedForm(final int width, final int labelWidth) {
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setLabelWidth(labelWidth);
        formPanel.setBorder(false);
        formPanel.setWidth(width);
        formPanel.setButtonAlign(Position.RIGHT);

        tabPanel = new TabPanel();
        tabPanel.setActiveTab(0);
        formPanel.add(tabPanel);
    }

    public void addButton(final Button button) {
        formPanel.addButton(button);
    }

    public void addTab(final String title, final Panel tab) {
        tab.setTitle(title);
        tab.setLayout(new FormLayout());
        tab.setAutoHeight(true);
        tab.setPaddings(10);
        tabPanel.add(tab);
        if (formPanel.isRendered()) {
            formPanel.doLayout();
        }
    }

    public Form getForm() {
        return formPanel.getForm();
    }

    public Component getPanel() {
        return formPanel;
    }

}
