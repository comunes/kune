/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client.ui.desktop;

import org.ourproject.kune.client.ui.ExpandPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextContents extends Composite {
	
	private VerticalPanel panel = null;
	
	private ExpandPanel expand = null;
	
	public ContextContents() {
		initialize();
		layout();
		setPropierties();
	}

	private void initialize() {
        panel = new VerticalPanel();
        expand = new ExpandPanel(ExpandPanel.VERT);
	}

	private void layout() {
		panel.add(expand);
		initWidget(panel);
    }

	private void setPropierties() {
        panel.setBorderWidth(0);
        panel.setSpacing(0);
        panel.setStyleName("context-contents");
        panel.addStyleName("context-contents");
        panel.setCellHeight(expand, "100%");
	}
	
	public void clear() {
        panel.clear();
        panel.add(expand);
        panel.setCellHeight(expand, "100%");
	}
	
	public void add(Widget widget) {
		panel.insert(widget, panel.getWidgetCount()-1);
	}
}
