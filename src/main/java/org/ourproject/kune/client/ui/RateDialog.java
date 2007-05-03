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

import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.RateListener;
import org.ourproject.kune.client.model.Star;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 * Rate widget (view part of MVC)
 * 
 * @author Vicente J. Ruiz Jurado
 *
 */
public class RateDialog extends Composite implements RateView {
    private Rate rate = null;
    private Grid rateGrid = null;
    private Image[] starImg;
    private Label rateDesc = null;
    private BorderPanel rateDescBorder = null;
    
    RateListener listener = new RateListener() {
        public void onRateChange(Star[] stars, int byUsers) {
            setRate(stars);
        	setByUsers(byUsers);
        }};

    public RateDialog(Rate rate) {
		initialize();
		layout();
		setProperties();
    	this.rate = rate;
    	this.rate.addRateListener(listener);
        this.rate.reflesh();
    }
    
    private void initialize() {
    	rateGrid = new Grid(1, 6);
    	starImg = new Image[5];
		for (int i = 0; i < 5; i++) {
			starImg[i] = new Image(Star.GREY);
		}
		rateDesc = new Label();
		rateDescBorder = new BorderPanel(rateDesc, 0, 0, 0, 5);
    }
    
    private void layout() {
        initWidget(rateGrid);
		for (int i = 0; i < 5; i++) {
            rateGrid.setWidget(0, i, starImg[i]);
		}
		rateGrid.setWidget(0, 5, rateDescBorder);
    }
    
    private void setProperties() {
        rateGrid.setCellPadding(0);
        rateGrid.setCellSpacing(0);
        rateGrid.setBorderWidth(0);       	
    	rateDesc.addStyleName("rateit-star-label");
		rateDesc.setStyleName("rateit-star-label");
    }
    
    public void setRate(Star stars[]) {
        for (int i = 0; i < 5; i++) {
            starImg[i].setUrl(stars[i].getUrl());
        }
    }
    
    public void setByUsers(int byUsers) {
        if (byUsers == 0) {
        	rateDesc.setText("(" + Trans.constants().NotRated() + ")");
        }
        else if (byUsers == 1) {
        	rateDesc.setText("(" + byUsers + " " + Trans.constants().user() + ")");
        }
        else {
        	rateDesc.setText("(" + byUsers + " " + Trans.constants().users() + ")");
        }
    }
}
