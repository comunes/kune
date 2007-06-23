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
package org.ourproject.kune.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class ExpandPanel extends Composite {

	public static final int VERT = 1;
    
    public static final int HORIZ = 2;
    
    private SimplePanel panel = null;
    
	private HTML content = null;
	
	public ExpandPanel(int type) {
		initialize();
		layout();
		setProperties(type);
	}

    private void initialize() {
    	panel = new SimplePanel();
    	content = new HTML("<b></b>");
    }
    
    private void layout() {
        panel.add(content);
        initWidget(panel);
    }

    private void setProperties(int type) {
    	switch(type) {
    	case VERT:
    		content.setHeight("100%");
    		panel.setHeight("100%");
    		break;
    	case HORIZ:
    		content.setWidth("100%");
    		panel.setWidth("100%");
    		break;
    	default:
    		throw new IllegalArgumentException("Invalid type of ExpandPanel");     
     	}

    }
}
