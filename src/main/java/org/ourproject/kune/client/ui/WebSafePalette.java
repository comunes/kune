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
	
/**
 * 
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 *
 */	
public class WebSafePalette extends PopupPanel {
	
    private static final int ROWS = 18;
    
    private static final int COLS = 12;
    
    private static final String COLORS[] = {"0", "3", "6", "9", "C", "F"};
	
    Grid paletteGrid = null;
    
    boolean colorSelected = false;
    
    String color = null;
    
    public WebSafePalette() {
    	super(true, true);
        initialize();
        layout();
        setProperties();
    }
	
    private void initialize() {
    	paletteGrid = new Grid(ROWS, COLS);
    }
    
    private void layout() {
        this.setWidget(paletteGrid);
    }
    
    private void setProperties() {
    	paletteGrid.setVisible(false);
    	paletteGrid.setCellSpacing(1);
        // Put color values in the grid cells
    	
    	int row;
    	int col;
    	int n = 0;
    	for (int a = 0; a < COLORS.length; a++) {
    		for (int b = 0; b < COLORS.length; b++) {
    			for (int c = 0; c < COLORS.length; c++) {
                    row = n / COLS;
    				col = n % COLS;
    				String currentColor = "#" + COLORS[c] + COLORS[a] + COLORS[b]; 
        			paletteGrid.getCellFormatter().setWidth(row, col, "12px");
        			paletteGrid.getCellFormatter().setHeight(row, col, "10px");
        			paletteGrid.setText(row, col, " ");
        			DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row, col),
        					"backgroundColor", currentColor);
        			n++;
    			}
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
    	int pd = (row * COLS + col);
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
    	super.show();
    	super.setPopupPosition(left, top);
    }
    
    public boolean isColorSelected() {
    	return colorSelected;
    }

    public String getColorSelected() {
    	return color;
    }
}
	