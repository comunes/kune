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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>Wrap a Widget with some margins; useful for layouts</p>
 * 
 * @author Vicente J. Ruiz Jurado (vjrj@ourproject.org)
 *
 */
public class BorderPanel extends SimplePanel {
	public BorderPanel() {
		this(null);
	}

	public BorderPanel(Widget widget) {
		setWidgetMargin(widget, 0, 0, 0, 0);
	}

	public BorderPanel(Widget widget, int border) {
		setWidgetMargin(widget, border, border, border, border);
	}

	public BorderPanel(Widget widget, int vertical, int horizontal) {
        setWidgetMargin(widget, vertical, horizontal, vertical, horizontal);
	}

	public BorderPanel(Widget widget, int top, int right, int bottom, int left) {
        setWidgetMargin(widget, top, right, bottom, left);
	}
	
	/**
	 * <p>Set propierties of <code>BorderPanel</code> (widget, margins)</p>
	 * 
	 * @param widget Widget to wrap
	 * @param top top margin
	 * @param right right margin
	 * @param bottom bottom margin
	 * @param left left margin
	 */
	public void setWidgetMargin(Widget widget, int top, int right, int bottom, int left) {
		setMargins(top, right, bottom, left);
		add(widget);
	}
	
	/**
	 * <p>Set margins in div</p>
	 * 
	 * @param top margin-top css attribute
	 * @param right margin-right css attribute
	 * @param bottom margin-bottom css attribute
	 * @param left margin-left css attribute
	 */
	public void setMargins(int top, int right, int bottom, int left) {
		DOM.setStyleAttribute(getElement(), "marginTop", "" + top);
		DOM.setStyleAttribute(getElement(), "marginRight", "" + right);
		DOM.setStyleAttribute(getElement(), "marginBottom", "" + bottom);
		DOM.setStyleAttribute(getElement(), "marginLeft", "" + left);
	}
}
