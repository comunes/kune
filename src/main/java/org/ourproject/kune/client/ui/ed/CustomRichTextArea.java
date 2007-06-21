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
package org.ourproject.kune.client.ui.ed;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CustomRichTextArea extends Composite implements CustomRichTextAreaView {
	
	private RichTextArea area;

	private RichTextToolbar tb;

	private KeyboardListener areaKbListener;
	
    private CustomRichTextAreaController controller;
	
	public CustomRichTextArea(CustomRichTextAreaController controller) {
		area = new RichTextArea();
		tb = new RichTextToolbar(area, controller);
        this.controller = controller;
        
		VerticalPanel ed = new VerticalPanel();
		ed.add(tb);
		ed.add(area);

		//area.setHeight("20em");
		area.setHeight("100%");
		area.setWidth("100%");
		ed.setWidth("100%");

		initWidget(ed);

        areaKbListener = new KeyboardListener() {
        	public void onKeyDown(Widget sender, char keyCode, int modifiers) {}
        	public void onKeyPress(Widget sender, char keyCode, int modifiers) {}
        	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        		if (sender == area) {
        			fireEdit();
        		}
        	}
        };

        area.addKeyboardListener(areaKbListener);
	}
	
	public void setEnabled(boolean enabled) {
		area.setEnabled(enabled);
	}
	
	public String getHTML() {
		return area.getHTML();
	}
	
	public void setHTML(String html) {
		area.setHTML(html);
	}
	
	public void setHeight(String height) {
		area.setHeight(height);
	}
	
    public void enableSaveButton(boolean enabled) {
    	tb.enableSaveButton(enabled);
    }
    
    public void setTextSaveButton(String text) {
        tb.setTextSaveButton(text);
    }
    
    private void fireEdit() {
        controller.onEdit();
    }

}