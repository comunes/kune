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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomRichTextArea extends Composite implements CustomRichTextAreaView {

    private static final String BACKCOLOR_ENABLED = "#FFF";

    private static final String BACKCOLOR_DISABLED = "#CCC";

    private RichTextArea area;

    private RichTextToolbar areaToolBar;

    private VerticalPanel areaVP;

    private CustomRichTextAreaController controller;

    // NOTE: Bug with setHTML:
    // http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/eda382814a62ecb7/f12fbea0c463f905
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=1228&can=1&q=Richtextarea 
    private boolean areaLoaded = false;

    private String initHtml = "";
    
    public CustomRichTextArea(CustomRichTextAreaController controller) {
	this.controller = controller;
	initiaze();
	layout();
	setProperties();
    }

    private void initiaze() {
	area = new RichTextArea() {
	    protected void onLoad() {
		DeferredCommand.addCommand(new Command() {
		    public void execute() {
			areaLoaded = true;
			area.setHTML(initHtml);
		    }
		});
	    }
	};
	areaToolBar = new RichTextToolbar(area, controller);
	areaVP = new VerticalPanel();
    }

    private void layout() {
	areaVP.add(areaToolBar);
	areaVP.add(area);
	initWidget(areaVP);
    }

    private void setProperties() {
	//area.setHeight("20em");
	area.setHeight("100%");
	area.setWidth("100%");
	areaVP.setWidth("100%");
    }

    public void setEnabled(boolean enabled) {
	if (enabled) {
	    DOM.setStyleAttribute(area.getElement(), "backgroundColor", BACKCOLOR_ENABLED);
	} else {
	    DOM.setStyleAttribute(area.getElement(), "backgroundColor", BACKCOLOR_DISABLED);
	}
	area.setEnabled(enabled);
    }

    public String getHTML() {
	return area.getHTML();
    }
    
    public String getText() {
	return area.getText();
    }

    public void setHTML(String html) {
	// NOTE: Bug with setHTML:
	// http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/eda382814a62ecb7/f12fbea0c463f905
	// http://code.google.com/p/google-web-toolkit/issues/detail?id=1228&can=1&q=Richtextarea
	if (areaLoaded) {
	    area.setHTML(html);
	}
	else {
	    initHtml = html;
	}
    }
    
    public void setText(String text) {
        area.setText(text);
    }
    

    public void setHeight(String height) {
	area.setHeight(height);
    }

    public void setEnabledSaveButton(boolean enabled) {
	areaToolBar.setEnabledSaveButton(enabled);
    }

    public void setEnabledCancelButton(boolean enabled) {
	areaToolBar.setEnabledCancelButton(enabled);
    }

    public void setTextSaveButton(String text) {
	areaToolBar.setTextSaveButton(text);
    }
    
    public void tests() {
	//this.areaToolBar.
    }
}