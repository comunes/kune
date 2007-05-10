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

import org.ourproject.kune.client.ui.DropDownPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class ContextDropDowns extends VerticalPanel {
	public ContextDropDowns() {
		super();
		this.setWidth("100%");
	}

	public void addDropDown(String title, Widget widget, boolean visible, String color) {
		DropDownPanel d = new DropDownPanel();
		d.setWidth("100%");
		d.setContent(widget);
		d.setContentVisible(visible);
        d.setTitle(title);
        d.setColor(color);
        this.add(d);
	}
}
