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

import org.ourproject.kune.client.Trans;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * This is the class for the logo of a group or a user. By default, we use a Label instead of image until the user configure the logo.
 * 
 */
public class EntityLogo extends Composite {
	public final static String DEFFONTSIZE = "189%";
	public final static String MEDFONTSIZE = "152%";
	public final static String MINFONTSIZE = "122%";
	
	public final static int DEFLOGOWIDTH = 468;
	public final static int DEFLOGOHEIGHT = 60;
		
    private VerticalPanel defTextLogoVP = null;
	private Label defTextLogoLabel = null;
    private Hyperlink defTextPutYourLogoHL = null;
    private Image logoImage = null;
    
    public EntityLogo() {
		this("");
    }
    
    public EntityLogo(String title) {
		initialize();
		layout();
		setProperties();
    	this.initWidget(defTextLogoVP);
    	setDefaultText(title);
    }
    
    private void initialize() {
    	defTextLogoVP = new VerticalPanel();
        defTextLogoLabel = new Label();
        defTextPutYourLogoHL = new Hyperlink();
        logoImage = new Image();
    }
    
    private void layout() {
    	defTextLogoVP.add(defTextLogoLabel);
    	defTextLogoVP.add(defTextPutYourLogoHL);
    }
    
    private void setProperties() {
    	defTextLogoVP.setHeight(""+  DEFLOGOHEIGHT);
    	defTextLogoVP.setWidth("" + DEFLOGOWIDTH);
    	defTextLogoVP.setBorderWidth(0);
    	defTextLogoVP.setCellHorizontalAlignment(defTextPutYourLogoHL, HasHorizontalAlignment.ALIGN_RIGHT);
    	defTextLogoVP.setSpacing(0);
    	defTextPutYourLogoHL.setText(Trans.constants().PutYourLogoHere());
        // TODO: link to configure the logo
    	defTextLogoVP.addStyleName("def-logo-panel");
        defTextLogoVP.addStyleName("def-logo-panel");
    	defTextLogoLabel.addStyleName("def-logo-text");
        defTextLogoLabel.addStyleName("def-logo-text");
        defTextPutYourLogoHL.addStyleName("put-you-logo");
    	defTextPutYourLogoHL.setStyleName("put-you-logo");
    }
    
	public void setDefaultText(String title) {
        defTextLogoLabel.setText(title);
//      While 1.4 stabilizes:
//        DOM.setStyleAttribute(defTextLogoLabel.getElement(), "fontSize", DEFFONTSIZE);
//        if (title!="") {
            //SiteMessageDialog.get().setMessageInfo("Logo Height: " + defTextLogoVP.getOffsetHeight());
//            SiteMessageDialog.get().setMessageInfo("Logo Height: " + DOM.getIntAttribute(defTextLogoVP.getElement(), "clientHeight"));
//        }
//        if (defTextLogoVP.getOffsetHeight() > DEFLOGOHEIGHT) {
//        	DOM.setStyleAttribute(defTextLogoLabel.getElement(), "fontSize", MEDFONTSIZE);
//        }
//        if (defTextLogoVP.getOffsetHeight() > DEFLOGOHEIGHT) {
//        	DOM.setStyleAttribute(defTextLogoLabel.getElement(), "fontSize", MINFONTSIZE);
//        }
    }

    public void setLogo(String url) {
    	logoImage.setUrl(url);
    	// TODO: limit size
    	this.initWidget(logoImage);
    }
}
