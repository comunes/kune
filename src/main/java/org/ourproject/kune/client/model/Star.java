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
package org.ourproject.kune.client.model;

/**
 * @author vjrj
 *
 */
public class Star {
    public final static String YELLOW = "images/star-yellow.png";
    public final static String GREY = "images/star-grey.png";
    public final static String SMALL_YELLOW = "images/star-small-yellow.png";
    public final static String VSMALL_YELLOW = "images/star-vsmall-yellow.png";
    public final static String STAR_PERC = "images/star-";
    public final static Star[] CLEAR = {new Star("images/star-grey.png"), 
    	new Star("images/star-grey.png"), new Star("images/star-grey.png"),
    	new Star("images/star-grey.png"), new Star("images/star-grey.png")};
    
    private String url;
    
    public Star() {
    	this.url = Star.GREY;
    }
    
    public Star(String url) {
    	this.setUrl(url);
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
    	return this.url;
    }
    
    public Star(float rate) {
    	if (rate == 1) this.url = Star.YELLOW;
    	else if (rate == 0) this.url = Star.GREY;
    	else {
            int rateTrucated = (int) rate;
            int rateDecimal = ((int) ((rate - rateTrucated) * 10)) * 10;
            setUrl(Star.STAR_PERC + rateDecimal + ".png");
        }
    }
}