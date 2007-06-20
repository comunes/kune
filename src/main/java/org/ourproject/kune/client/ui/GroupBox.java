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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * A Group box used to break a window into distinct logical areas. We follow Apple description:
 * 
 * http://developer.apple.com/documentation/UserExperience/Conceptual/OSXHIGuidelines/index.html
 * 
 *
 */
public class GroupBox extends Composite {
	private Label boxLabel = null;
	private VerticalPanel generalVP = null;
	private VerticalPanel innerVP = null;
	private SimplePanel box = null;
	private SimpleRoundedPanel outerBoxBorder = null;
	
	public GroupBox() {
		initialize();
		layout();
		setProperties();
	}

    private void initialize() {
    	generalVP = new VerticalPanel();
    	boxLabel = new Label();
    	box = new SimplePanel();
    	outerBoxBorder = new SimpleRoundedPanel(box, RoundedPanel.ALL);
        innerVP = new VerticalPanel();
    }
    
    private void layout() {
    	generalVP.add(new BorderPanel(boxLabel, 0, 0, 2, 14));
    	generalVP.add(outerBoxBorder);
    	box.setWidget(innerVP);
    	this.initWidget(new BorderPanel(generalVP, 12));
    }
    
    private void setProperties() {
    	generalVP.setBorderWidth(0);
    	generalVP.setSpacing(0);
    	generalVP.setStyleName("group-box-outter");
    	box.setStyleName("group-box");
    	outerBoxBorder.setCornerStyleName("group-outer-box-border");
    	innerVP.setBorderWidth(0);
    	innerVP.setSpacing(0);
    	innerVP.setStyleName("group-box-inner");
    	boxLabel.setStyleName("group-box-label");
    }
    
    public void setTitle(String title) {
    	boxLabel.setText(title);
    }
    
    public void add(Widget widget) {
        innerVP.add(widget);
    }
    
    public void remove(Widget widget) {
    	innerVP.remove(widget);
    }
}
