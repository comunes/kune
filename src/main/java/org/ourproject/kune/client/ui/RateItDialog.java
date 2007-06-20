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
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.RateItListener;
import org.ourproject.kune.client.model.Star;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

/**
 * Rate it widget (view part of MVC). You can click on the star to rate the element
 * 
 */
public class RateItDialog extends Composite implements ClickListener, RateItView {
	private Rate rate = null;
    private Grid rateGrid = null;
    private Image[] starImg;
    private Label rateDesc = null;
    private BorderPanel rateDescBorder = null;
    
    RateItListener listener = new RateItListener() {
        public void onRateItChange(Star[] stars, int rate) {
          setRate(stars);
          setDesc(rate);
        }
        public void onRateItChange(Star[] stars) {
            setRate(stars);
        }};

    public RateItDialog(Rate rate) {
		initialize();
		layout();
		setProperties();
    	this.rate = rate;
    	this.rate.addRateItListener(listener);
    	this.rate.reflesh(); // Maybe the user has already rated this element
    }
    
    private void initialize() {
    	rateGrid = new Grid(1, 6);
    	starImg = new Image[5];
		rateDesc = new Label();
		rateDescBorder = new BorderPanel(rateDesc, 0, 0, 0, 5);
		for (int i = 0; i < 5; i++) {
			starImg[i] = new Image();
			Img.ref().starGrey().applyTo(starImg[i]);
			starImg[i].addStyleName("rateit-star");
			starImg[i].setStyleName("rateit-star");
			starImg[i].addClickListener(this);
            starImg[i].addMouseListener(new MouseListenerAdapter() {
                public void onMouseLeave(Widget sender) {
                    rate.revertUserCurrentRate();
                }
                public void onMouseEnter(Widget sender) {
                	for (int i = 0; i < 5; i++) {
                		if (sender == starImg[i]) {
                            rate.starOver(i);
                        }
                	}
 
                }
            });
        }
    }
    
    private void layout() {
        initWidget(rateGrid);
		for (int i = 0; i < 5; i++) {
            rateGrid.setWidget(0, i, starImg[i]);
		}
		rateGrid.setWidget(0, 5, rateDescBorder);
		rateDesc.addStyleName("rateit-star-label");
		rateDesc.setStyleName("rateit-star-label");
    }
    
    private void setProperties() {
        rateGrid.setCellPadding(0);
        rateGrid.setCellSpacing(0);
        rateGrid.setBorderWidth(0);      
    }
    
    public void onClick(Widget sender) {    	
    	for (int i = 0; i < 5; i++) {
            if (sender == starImg[i]) {
            	rate.starClicked(i);
            }
    	}

    }

    /**
     * Set the star dialog with the correct images values 
     * @param stars with image urls
     */
    public void setRate(Star stars[]) {
        for (int i = 0; i < 5; i++) {
        	stars[i].getImg().applyTo(starImg[i]);
        }
    }
    
    public void clearRate() {
        for (int i = 0; i < 5; i++) {
        	Img.ref().starGrey().applyTo(starImg[i]);
        }
    }
    
    public void setDesc(int rateTruncated) {
    	if      (rateTruncated == 1) { rateDesc.setText(Trans.constants().Poor()); }
       	else if (rateTruncated == 2) { rateDesc.setText(Trans.constants().BelowAverage()); }
       	else if (rateTruncated == 3) { rateDesc.setText(Trans.constants().Average()); }
       	else if (rateTruncated == 4) { rateDesc.setText(Trans.constants().AboveAverage()); }
       	else if (rateTruncated == 5) { rateDesc.setText(Trans.constants().Excellent()); }
       	else { rateDesc.setText(""); }
    }
}
