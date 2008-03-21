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

package org.ourproject.kune.platf.client.ui.form;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AbstractFormPanel extends VerticalPanel implements FormView {
    private final Button btnOk;
    private final Button btnCancel;
    protected final VerticalPanel content;

    public AbstractFormPanel(final FormListener listener) {
	content = new VerticalPanel();
	add(content);

	btnOk = new Button("", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onAccept();
	    }
	});
	btnCancel = new Button("", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onCancel();
	    }
	});

	FlowPanel controls = new FlowPanel();
	controls.add(btnOk);
	controls.add(btnCancel);
	add(controls);
    }

    public void addRow(final String label, final Widget widget) {
	HorizontalPanel panel = new HorizontalPanel();
	panel.add(new Label(label));
	panel.add(widget);
	content.add(panel);
    }

    public void setCommandLabels(final String acceptLabel, final String cancelLabel) {
	btnOk.setText(acceptLabel);
	btnCancel.setText(cancelLabel);
    }
}
