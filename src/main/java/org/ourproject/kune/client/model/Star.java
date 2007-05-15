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

import org.ourproject.kune.client.Img;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author vjrj
 *
 */
public class Star {
    public static final int GREY = 0;
    public static final int YELLOW = 1;
    
    public final static Star[] CLEAR = {new Star(), new Star(), new Star(), new Star(), new Star()};
    
    // private Image image;
    private AbstractImagePrototype image;
    
    public Star() {
    	image = Img.ref().starGrey();
    }
    
    public AbstractImagePrototype  getImg() {
    	return image;
    }
    
    public Star(float rate) {
    	if (rate == 1) image = Img.ref().starYellow();
    	else if (rate == 0) image = Img.ref().starGrey();
    	else {
            int rateTrucated = (int) rate;
            int rateDecimal = ((int) ((rate - rateTrucated) * 10)) * 10;
            if (rateDecimal == 10) image = Img.ref().star10();
            else if (rateDecimal == 20) image = Img.ref().star20();
            else if (rateDecimal == 30) image = Img.ref().star30();
            else if (rateDecimal == 40) image = Img.ref().star40();
            else if (rateDecimal == 50) image = Img.ref().star50();
            else if (rateDecimal == 60) image = Img.ref().star60();
            else if (rateDecimal == 70) image = Img.ref().star70();
            else if (rateDecimal == 80) image = Img.ref().star80();
            else if (rateDecimal == 90) image = Img.ref().star90();
        }
    }
}
