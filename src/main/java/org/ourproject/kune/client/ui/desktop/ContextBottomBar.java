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

import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class ContextBottomBar extends HorizontalPanel {
	private Label bottomLabel = null;
    private RoundedPanel rn = null;
    
	public ContextBottomBar() {
		super();
		initialize();
		layout();
		setProperties();
	}

	private void initialize() {
		bottomLabel = new Label(); 
		rn = new RoundedPanel(RoundedPanel.BOTTOMLEFT);
	}

	private void layout() {
		this.add(rn);
		rn.add(bottomLabel);
	}

	private void setProperties() {
		this.setSpacing(0);
		this.setBorderWidth(0);
		this.setWidth("100%");
		bottomLabel.setWidth("100%");		
		bottomLabel.setStyleName("context-bottombar");
		bottomLabel.setHeight("24");
        this.setCellVerticalAlignment(bottomLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        rn.setCornerStyleName("context-bottombar-outer");
    }

	public void setText(String title) {
		bottomLabel.setText(title);
	}
}
