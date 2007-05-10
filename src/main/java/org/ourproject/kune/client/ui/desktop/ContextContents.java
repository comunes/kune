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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class ContextContents extends Composite {
	private ScrollPanel contentSP = null;
	private VerticalPanel contentInnerVP = null;

	public ContextContents() {
		initialize();
		layout();
		setPropierties();
	}

	private void initialize() {
		contentSP = new ScrollPanel();
		contentInnerVP = new VerticalPanel();
	}

	private void layout() {
		initWidget(contentSP);
		contentSP.add(contentInnerVP);
	}

	private void setPropierties() {
        contentInnerVP.setBorderWidth(0);
        contentInnerVP.setSpacing(0);
        contentInnerVP.setStyleName("context-contents");
        contentInnerVP.addStyleName("context-contents");
	}

	public void add(Widget w) {
		contentInnerVP.add(w);
	}
	
	public void del(Widget w) {
		contentInnerVP.remove(w);
	}
		
	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth
				- contentSP.getAbsoluteLeft() - 278;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight
				- contentSP.getAbsoluteTop() - 30 - 24;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		contentSP.setSize("" + scrollWidth, "" + scrollHeight);
	}
}
