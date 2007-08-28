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

package org.ourproject.kune.docs.client.ui.cnt.reader;

import org.ourproject.kune.platf.client.ui.CustomButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Button;

public class DocumentReaderPanel extends VerticalPanel implements DocumentReaderView {
    private Button btnEdit;
    private final HTML content;

    public DocumentReaderPanel(final DocumentReaderListener listener) {
	add(createToolBar(listener));
	btnEdit.setVisible(false);
	content = new HTML("this is the content");
	add(content);
	this.setWidth("100%");
	this.setCellWidth(content, "100%");
    }

    private Widget createToolBar(final DocumentReaderListener listener) {
	HorizontalPanel panel = new HorizontalPanel();
	Label expand = new Label("");
	panel.add(expand);
	panel.setWidth("100%");
	expand.setWidth("100%");
	panel.setCellWidth(expand, "100%");
	panel.addStyleName("kune-DocumentReaderPanel");
	// i18n
	btnEdit = new CustomButton("Edit", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onEdit();
	    }
	}).getButton();
	panel.add(btnEdit);
	btnEdit.addStyleName("kune-Button-Large-lrSpace");
	return panel;
    }

    public void setContent(final String text) {
	if (text != null) {
	    content.setHTML(text);
	}
    }

    public void setEditEnabled(final boolean isEnabled) {
	btnEdit.setVisible(isEnabled);
    }

    public String getContent() {
	return content.getHTML();
    }

}
