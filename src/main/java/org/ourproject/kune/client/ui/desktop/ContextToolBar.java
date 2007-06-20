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

import org.ourproject.kune.client.ui.RoundedPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextToolBar extends Composite {
    private RoundedPanel rn = null;
    private HorizontalPanel toolbarHP = null;
    
	public ContextToolBar() {
		super();
		initialize();
		layout();
		setProperties();
	}
	
	private void initialize() {
		toolbarHP = new HorizontalPanel();
		rn = new RoundedPanel(toolbarHP, RoundedPanel.TOPLEFT);
	}

	private void layout() {
		initWidget(rn);
        toolbarHP.add(new HTML("<b></b>"));
	}

	private void setProperties() {
		this.setWidth("100%");
		toolbarHP.setSpacing(0);
		toolbarHP.setBorderWidth(0);
		toolbarHP.setWidth("100%");
		toolbarHP.setHeight("25");
		toolbarHP.addStyleName("context-topbar");
		toolbarHP.setStyleName("context-topbar");
		rn.setCornerStyleName("context-topbar-outer");
	}

    public void add(Widget widget) {
    	toolbarHP.add(widget);
    }
    
    public void clear() {
    	toolbarHP.clear();
    }
}
