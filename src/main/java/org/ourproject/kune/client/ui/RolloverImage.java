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
        this.imageOver.applyTo(this);
    }
    
    public void setOverImg() {
    	this.imageNormal.applyTo(this);
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
