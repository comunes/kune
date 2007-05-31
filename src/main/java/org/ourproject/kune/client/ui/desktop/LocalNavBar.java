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

import org.ourproject.kune.client.ui.RoundedPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class LocalNavBar extends VerticalPanel implements ClickListener {
	public final static int NONE = -1;
	
	private int currentItem = NONE;
	
    public LocalNavBar() {
    	super();
        initialize();
        layout();
        setProperties();
    }
    
    private void initialize() {
    }
    
    private void layout()  {
    }
    
    private void setProperties() {
        this.setBorderWidth(0);
    	this.setSpacing(0);
    	this.setWidth("100%");
    }
    
    public void addItem(String name, String url) {
        Hyperlink hl = new Hyperlink(name, url);
		RoundedPanel menuItem = new RoundedPanel(hl, RoundedPanel.RIGHT, "kune-menu-item-not-selected");
		menuItem.setWidth("100%");
		this.add(menuItem);
		hl.addClickListener(this);
    }
    
    public int getItemsCount() {
        return this.getWidgetCount();
    }

    public void selectItem(int item) {
    	this.verifyExist(item);
    	if (currentItem != NONE) {
    		((RoundedPanel)this.getWidget(currentItem)).setCornerStyleName("kune-menu-item-not-selected");
        }
    	((RoundedPanel)this.getWidget(item)).setCornerStyleName("kune-menu-item-selected");
    	this.currentItem = item;
    }
    
    private void verifyExist(int item) {
    	if (item >= getItemsCount()) {
    		throw new IllegalArgumentException("LocalNavBar Item Not found");
    	}
    }
    
    public void deleteItem(int item) {
    	this.verifyExist(item);
        this.deleteItem(item);
        if (currentItem == item) {
            currentItem = NONE ;
        }
    }
    
    public int getCurrentItem() {
        return this.currentItem;
    }
    
    public void onClick(Widget sender) {
    	Hyperlink hl = new Hyperlink();
        for (int i = 0; i < getItemsCount(); i++) {
        	hl = ((Hyperlink) ((RoundedPanel) this.getWidget(i)).getWidget());
        	if (hl == sender) {
        		selectItem(i);
        		return;
        	}
        }
    }
}