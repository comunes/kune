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

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

public class RolloverImage extends Image {
	AbstractImagePrototype imageNormal;
	AbstractImagePrototype imageOver;
	
	public RolloverImage() {
		super();
	}
	
    public RolloverImage(AbstractImagePrototype image, AbstractImagePrototype imageOver) {
    	this.setImages(image, imageOver);
    }
    
    public void setNormalImg() {
        this.imageNormal.applyTo(this);
    }
    
    public void setOverImg() {
    	this.imageOver.applyTo(this);
    }
    
    public void setImages(AbstractImagePrototype image, AbstractImagePrototype imageOver) {
    	this.imageNormal = image;
    	this.imageOver = imageOver;
    	
    	this.imageNormal.applyTo(this);
    	
    	this.addMouseListener(new MouseListenerAdapter() {
    	    public void onMouseLeave(Widget sender) {
    	    	((RolloverImage) sender).setNormalImg();
    	    }
    	    public void onMouseEnter(Widget sender) {
    	    	((RolloverImage) sender).setOverImg();
    	    }
    	});
    }
    
}
