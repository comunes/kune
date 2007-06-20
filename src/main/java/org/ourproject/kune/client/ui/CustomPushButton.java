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

import org.ourproject.kune.client.Img;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomPushButton extends FocusPanel implements SourcesClickEvents, EventListener  {
	
	// Following: http://developer.apple.com/documentation/UserExperience/Conceptual/OSXHIGuidelines/index.html
	public final static int LARGE = 20;

	public final static int SMALL = 17;

	public final static int MINI = 15;
		
	public final static int HORSPACELARGE = 12;
	
	public final static int HORSPACESMALL = 10;
	
	public final static int HORSPACEMINI = 8;
	
	public final static int VERSPACELARGE = 12;
	
	public final static int VERSPACESMALL = 10;
	
	public final static int VERSPACEMINI = 8;
	
	public final static int SPACEHELPBUTTON = 12; // Space of the help button with other elements
	
	public final static int TEXTSPACELARGE = 10; // 14 - 4 (Image)
	
	public final static int TEXTSPACESMALL = 7; // 11 - 4 (Image)
	
	public final static int TEXTSPACEMINI = 6; // 10 - 4 (Image)
	
	public final static int OKCANCELWIDTH = 68;
	
	private HorizontalPanel hr = null;
	
	private PushButton button = null;
	
	private Image leftImage = null;
	
	private Image rightImage = null;
	
    private AbstractImagePrototype leftImageUpClip = null;
	
	private AbstractImagePrototype rightImageUpClip = null;

	private String buttonStyle = null;
	
	private int buttonHeight;
	
    private String text = null;
    
    private ClickListenerCollection clickListeners;
	
	public CustomPushButton(String text) {       
        this(text, LARGE);
	}
	
	public CustomPushButton(String text, ClickListener listener) {    
		this(text, LARGE, listener);
	}
	
	public CustomPushButton(String text, int buttonHeight) {      
		this.buttonHeight = buttonHeight;
		this.text = text;
		initialize();
		layout();
		setProperties();
	}
	
	public CustomPushButton(String text, int buttonHeight, ClickListener listener) {
		this(text, buttonHeight);
		this.addClickListener(listener);
	}

    private void initialize() {
    	hr = new HorizontalPanel();
    	button = new PushButton(text);
    	hr.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
    	leftImage = new Image();
    	rightImage = new Image();
    	if (buttonHeight == LARGE) {
    		leftImageUpClip = Img.ref().button20llight();
    		rightImageUpClip = Img.ref().button20rlight();
            buttonStyle = "button20";
    	} else if (buttonHeight == SMALL) {
    		leftImageUpClip = Img.ref().button17llight();
    		rightImageUpClip = Img.ref().button17rlight();
            buttonStyle = "button17";
    	} else if (buttonHeight == MINI) {
    		leftImageUpClip = Img.ref().button15llight();
    		rightImageUpClip = Img.ref().button15rlight();
            buttonStyle = "button15";
    	}
        this.add(hr);
    }
    
    private void layout() {
    	leftImageUpClip.applyTo(leftImage);
    	rightImageUpClip.applyTo(rightImage);
    	hr.add(leftImage);
        hr.add(button);
        hr.add(rightImage);
    }
    
    private void setProperties() {
    	hr.setCellVerticalAlignment(button, VerticalPanel.ALIGN_MIDDLE);
    	button.setStyleName(buttonStyle);
    	button.addStyleName(buttonStyle);
        button.addStyleName("common-button");
    }
        
    public void setText(String text) {
    	this.text = text; 
    	button.setText(text);
    }
    
    public void addClickListener(ClickListener listener) {
        if (clickListeners == null) {
            clickListeners = new ClickListenerCollection();
        }
        clickListeners.add(listener);
    }
    
    public void removeClickListener(ClickListener listener) {
        if (clickListeners != null) {
            clickListeners.remove(listener);
        }
    }

    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK: {
                if (clickListeners != null) {
                    clickListeners.fireClick(this);
                }
                break;
            }            
        }
    }
    
    public void setEnabled(boolean enabled) {
    	button.setEnabled(enabled);
    }
}