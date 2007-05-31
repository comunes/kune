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

import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.ui.BorderPanel;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class SiteMessageDialog extends VerticalPanel implements ClickListener {
	private static final int TIMEVISIBLE = 4000;
	
	// TODO permit multiple messages
	private static SiteMessageDialog singleton;
    private TextArea message = null;
    private Image icon = null;
    private HorizontalPanel messageHP = null;
    private Hyperlink closeLink = null;
    
    Timer timer = new Timer() {
        public void run() {
        	SiteMessageDialog.get().setVisible(false);
        }
    };
    
	public SiteMessageDialog() {
    	super();
    	singleton = this;
    	initialize();
    	layout();
    	setProperties();
    }
	
	public static SiteMessageDialog get() {
        return singleton;
    }
    
	private void initialize() {
        message = new TextArea();
        icon = new Image();
        closeLink = new Hyperlink();
        messageHP = new HorizontalPanel();
        closeLink.addClickListener(this);
	}
    
	private void layout() {
		this.add(new BorderPanel(messageHP, 5, 0, 0, 0));
		this.add(closeLink);
		messageHP.add(new BorderPanel(icon, 0, 10, 0, 5));
		messageHP.add(message);
		this.setCellHorizontalAlignment(closeLink, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	private void setProperties() {
		this.setVisible(false);
		messageHP.setBorderWidth(0);
		messageHP.setSpacing(0);
		this.setBorderWidth(0);
		this.setSpacing(0);
		this.setHeight("33");
		this.addStyleName("site-message-dialog");
		this.setStyleName("site-message-dialog");
		message.setHeight("27");
		message.addStyleName("site-message");
		message.setStyleName("site-message");
		closeLink.addStyleName("site-message");
		closeLink.setStyleName("site-message");
		Img.ref().info().applyTo(icon);
		closeLink.setText(Trans.constants().Close());	
	}
	
	public void setMessage(String message) {
		this.message.setText(message);
		this.setVisible(true);
		timer.schedule(TIMEVISIBLE);
	}

    public void setMessageError(String message) {
    	Img.ref().error().applyTo(icon);
		this.setMessage(message);
	}
	
	public void setMessageVeryImp(String message) {
		Img.ref().important().applyTo(icon);
		this.setMessage(message);
	}
	
	public void setMessageImp(String message) {
		Img.ref().emblemImportant().applyTo(icon);
		this.setMessage(message);
	}
	
	public void setMessageInfo(String message) {
		Img.ref().info().applyTo(icon);
		this.setMessage(message);
	}

    public void onClick(Widget sender) {
        if  (sender == closeLink) {
            this.setVisible(false);
        }
    }
    
    public void adjustWidth(int windowWidth) {
    	int messageWidth = windowWidth * 60 / 100 - 3;
    	this.setWidth("" + messageWidth);
    	message.setWidth("" + (messageWidth - 16 - 40));
    }
}
