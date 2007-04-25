package org.ourproject.kune.client.ui;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

public class RolloverImage extends Image {
	private String url = null;
	private String urlOver = null;
	
	public RolloverImage() {
		super();
	}
	
    public RolloverImage(String url, String urlOver) {
    	this.setUrl(url, urlOver);
    }

    public void setNormalUrl() {
    	this.setUrl(url);
    }
    
    public void setOverUrl() {
    	this.setUrl(urlOver);
    }
    
    public void setUrl(String url, String urlOver) {
    	this.setUrl(url);
    	this.url = url;
    	this.urlOver = urlOver;
    	
    	this.addMouseListener(new MouseListenerAdapter() {
    	    public void onMouseLeave(Widget sender) {
    	    	((RolloverImage) sender).setNormalUrl();
    	    }
    	    public void onMouseEnter(Widget sender) {
    	    	((RolloverImage) sender).setOverUrl();
    	    }
    	});
    }
}
