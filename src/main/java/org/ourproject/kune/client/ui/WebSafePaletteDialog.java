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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

public class WebSafePaletteDialog extends PopupPanel {
	
	private static WebSafePaletteDialog singleton;
	
    private static final int ROWS = 18;
    
    private static final int COLS = 12;
	
    Grid paletteGrid = null;
    
    boolean colorSelected = false;
    
    String color = null;
    
    public WebSafePaletteDialog() {
    	super(true, true);
    	singleton = this;
        initialize();
        layout();
        setProperties();
    }
    
	public static WebSafePaletteDialog get() {
        return singleton;
    }
	
    private void initialize() {
    	paletteGrid = new Grid(ROWS, COLS);
    }
    
    private void layout() {
        this.setWidget(paletteGrid);
    }
    
    private void setProperties() {
    	paletteGrid.setVisible(false);
        // Put color values in the grid cells.
    	for (int row = 0; row < ROWS; ++row) {
    		for (int col = 0; col < COLS; ++col) {
    			paletteGrid.getCellFormatter().setWidth(row, col, "12px");
    			paletteGrid.getCellFormatter().setHeight(row, col, "10px");
    			paletteGrid.setText(row, col, " ");
    			DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row,col),
    					"backgroundColor", getColor(row, col));
    		}
    	}
    	
    	paletteGrid.addStyleName("web-safe-palette");
    	
        paletteGrid.addTableListener(new TableListener() {
            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                color = getColor(row, col);
                colorSelected = true;
                hide();
            }
        });
    }
    
    private String getColor(int row, int col) {
    	String color = null;
    	int pd = (row*12+col);
		int da = (pd)/6;
		int ra = (pd)%6;
		int aa = (da-ra/6);
		int db = (aa)/6;
		int rb = (aa)%6;
		int rc = (db-rb/6)%6;
		color = "rgb(" + ra*51 + ", " + rc*51 + ", " + rb*51 +")";
    	return color;
    }
    
    public void show(int left, int top) {
    	color = "";
    	colorSelected = false;
    	paletteGrid.setVisible(true);
    	this.show();
    	this.setPopupPosition(left, top);
    }
    
    public boolean isColorSelected() {
    	return colorSelected;
    }

    public String getColorSelected() {
    	return color;
    }
}
	