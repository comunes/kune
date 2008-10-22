/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;

public class IconHyperlink extends Widget implements SourcesClickEvents, AbstractLabel {

    private Element anchorElem;
    private ClickListenerCollection clickListeners;
    private ClickListenerCollection doubleClickListeners;
    private MouseListenerCollection mouseListeners;
    private String targetHistoryToken;
    private final Element link;
    private final Element icon;

    public IconHyperlink(final AbstractImagePrototype image) {
        setElement(DOM.createDiv());
        DOM.appendChild(getElement(), anchorElem = DOM.createAnchor());
        sinkEvents(Event.ONCLICK | Event.ONDBLCLICK | Event.MOUSEEVENTS);
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

    public void addDoubleClickListener(final ClickListener listener) {
        if (doubleClickListeners == null) {
            doubleClickListeners = new ClickListenerCollection();
        }
        doubleClickListeners.add(listener);
    }

    public String getTargetHistoryToken() {
        return targetHistoryToken;
    }

    public void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONDBLCLICK:
            if (doubleClickListeners != null) {
                doubleClickListeners.fireClick(this);
            }
            DOM.eventPreventDefault(event);
            break;
        case Event.ONCLICK:
            if (clickListeners != null) {
                clickListeners.fireClick(this);
            }
            History.newItem(targetHistoryToken);
            DOM.eventPreventDefault(event);
            break;
        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEUP:
        case Event.ONMOUSEMOVE:
        case Event.ONMOUSEOVER:
        case Event.ONMOUSEOUT:
            if (mouseListeners != null) {
                mouseListeners.fireMouseEvent(this, event);
            }
            break;
        }
    }

    public void removeClickListener(final ClickListener listener) {
        if (clickListeners != null) {
            clickListeners.remove(listener);
        }
    }

    public void removeDoubleClickListener(final ClickListener listener) {
        if (doubleClickListeners != null) {
            doubleClickListeners.remove(listener);
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

    public void setTitle(final String title) {
        // DOM.setElementAttribute(anchorElem, "ext:qtip", title);
        KuneUiUtils.setQuickTip(icon, title);
        KuneUiUtils.setQuickTip(anchorElem, title);
        // KuneUiUtils.setQuickTip(getElement(), title);
    }

    public void addMouseListener(final MouseListener listener) {
        if (mouseListeners == null) {
            mouseListeners = new MouseListenerCollection();
        }
        mouseListeners.add(listener);
    }

    public String getText() {
        return DOM.getInnerText(link);
    }

    public void removeMouseListener(final MouseListener listener) {
        if (mouseListeners != null) {
            mouseListeners.remove(listener);
        }
    }

    public void setColor(final String color) {
        DOM.setStyleAttribute(link, "color", color);
    }

    public void setText(final String text) {
        setLabelText(text);
    }
}