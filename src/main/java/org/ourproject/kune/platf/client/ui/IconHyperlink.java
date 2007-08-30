/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.ui;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;

public class IconHyperlink extends Widget implements SourcesClickEvents {

    private Element anchorElem;
    private ClickListenerCollection clickListeners;
    private String targetHistoryToken;
    private final Element link;
    private final Element icon;

    public IconHyperlink(final AbstractImagePrototype image) {
	setElement(DOM.createDiv());
	DOM.appendChild(getElement(), anchorElem = DOM.createAnchor());
	sinkEvents(Event.ONCLICK | Event.ONDBLCLICK);
	setStyleName("kune-IconHyperlink");
	icon = image.createImage().getElement();
	link = DOM.createSpan();
	DOM.appendChild(anchorElem, icon);
	DOM.appendChild(anchorElem, link);
    }

    public IconHyperlink(final AbstractImagePrototype image, final String text, final boolean asHTML,
	    final String targetHistoryToken) {
	this(image);
	if (asHTML) {
	    setLabelHTML(text);
	} else {
	    setLabelText(text);
	}
	setTargetHistoryToken(targetHistoryToken);
    }

    public IconHyperlink(final AbstractImagePrototype image, final String text, final String targetHistoryToken) {
	this(image);
	setLabelText(text);
	setTargetHistoryToken(targetHistoryToken);
    }

    public void addClickListener(final ClickListener listener) {
	if (clickListeners == null) {
	    clickListeners = new ClickListenerCollection();
	}
	clickListeners.add(listener);
    }

    public String getTargetHistoryToken() {
	return targetHistoryToken;
    }

    public void onBrowserEvent(final Event event) {
	if (DOM.eventGetType(event) == Event.ONCLICK) {
	    if (clickListeners != null) {
		clickListeners.fireClick(this);
	    }
	    History.newItem(targetHistoryToken);
	    DOM.eventPreventDefault(event);
	} else if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
	    FireLog.debug("Double click");
	}
    }

    public void removeClickListener(final ClickListener listener) {
	if (clickListeners != null) {
	    clickListeners.remove(listener);
	}
    }

    public void setLabelHTML(final String html) {
	DOM.setInnerHTML(link, html);
    }

    public void setTargetHistoryToken(final String targetHistoryToken) {
	this.targetHistoryToken = targetHistoryToken;
	DOM.setElementProperty(anchorElem, "href", "#" + targetHistoryToken);
    }

    public void setLabelText(final String text) {
	DOM.setInnerText(link, text);
    }

    public void setImage(final AbstractImagePrototype image) {
	DOM.setInnerHTML(icon, image.getHTML());
    }
}