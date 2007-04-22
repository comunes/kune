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


import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class Wizard extends DeckPanel implements ClickListener {
	
	public final static String DEFAULTHEIGHT = "300";

	public final static String DEFAULTWIDTH = "400";
	
	public Wizard(){
        super();
        setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
	}
	
    public void add(String title, Widget content) {
        this.add(title, content, true, true, true, true);
    }
    
    public void add(String title, Widget content, boolean backActive, boolean nextFinishActive, boolean cancelActive, boolean setButtonFinish) {
        WizardPage page = new WizardPage(title, content, backActive, nextFinishActive, cancelActive, setButtonFinish);
        add((Widget) page);
        page.addClickListener(this);
        if (pages() == 1) {
        	showWidget(0);
        }
    }
    
    public WizardPage current() {
    	return (WizardPage) this.getWidget(getVisibleWidget());
    }
    
    public int pages() {
    	return getWidgetCount();
    }
    
    public void next() {
    	int nextPage = getVisibleWidget() + 1;
    	if (nextPage < pages()) {
    		showWidget(nextPage);
    	}
    }
    
    public void back() {
        int backPage = getVisibleWidget() - 1;
        if (backPage >= 0) {
        	showWidget(backPage);
        }
    }
    
    public void selectPage(int num) {
    	showWidget(num);
    }
    
    public void onClick(Widget sender) {
    	WizardPage page = new WizardPage();
        for (int i = 0; i < pages(); i++) {
        	page = (WizardPage) getWidget(i);
            if (page.arrowBackButton == sender) {
                selectPage(i-1);
                return;
            }
            if (page.arrowNextFinishButton == sender) {
            	if (page.isButtonFinishActive()) {
            		this.getParent().getParent().setVisible(false);
            	}
            	else {
                    selectPage(i+1);
            	}
                return;
            }
            if (page.cancelButton == sender) {
                this.getParent().getParent().setVisible(false);
                return;
            }
        }
    }
    
    public void setSize(int width, int height) {
    	WizardPage page = new WizardPage();
    	for (int i = 0; i < pages(); i++) {
        	page = (WizardPage) getWidget(i);
        	page.centerContentVP.setSize("" + (width - 60),
        			"" + (height - 110));
    	}
        super.setSize("" + (width - 30), "" + (height - 30));
    }
	
}