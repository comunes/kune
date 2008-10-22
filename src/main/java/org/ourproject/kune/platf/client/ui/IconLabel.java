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
/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Adapted by Vicente J. Ruiz Jurado for kune
 */
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget that contains a icon and arbitrary text, <i>not</i> interpreted as
 * HTML.
 * 
 * <h3>CSS Style Rules</h3> <ul class='css'> <li>.kune-IconLabel { }</li> </ul>
 * 
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.HTMLExample}
 * </p>
 */

public class IconLabel extends Widget implements SourcesClickEvents, SourcesMouseEvents, SourcesMouseWheelEvents,
        HasHorizontalAlignment, HasText, HasWordWrap, AbstractLabel {

    private ClickListenerCollection clickListeners;
    private ClickListenerCollection doubleClickListeners;
    private HorizontalAlignmentConstant horzAlign;
    private MouseListenerCollection mouseListeners;
    private MouseWheelListenerCollection mouseWheelListeners;
    private final Element icon;
    private final Element textLabel;

    /**
     * Creates an empty label with a icon.
     * 
     * @param image
     *            the icon to add
     */
    private IconLabel(final AbstractImagePrototype image, final boolean leftIcon) {
        setElement(DOM.createDiv());
        sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS | Event.ONMOUSEWHEEL | Event.ONDBLCLICK);
        setStyleName("kune-IconLabel");
        icon = image.createImage().getElement();
        textLabel = DOM.createSpan();
        if (leftIcon) {
            DOM.appendChild(getElement(), icon);
            DOM.appendChild(getElement(), textLabel);
        } else {
            DOM.appendChild(getElement(), textLabel);
            DOM.appendChild(getElement(), icon);
        }
    }

    /**
     * Creates a label with the specified text and a icon in the left.
     * 
     * @param image
     *            the icon to add
     * @param text
     *            the new label's text
     */
    public IconLabel(final AbstractImagePrototype image, final String text) {
        this(image, true);
        setText(text);
    }

    /**
     * Creates a label with the specified text and a icon in the right.
     * 
     * @param image
     *            the icon to add
     * @param text
     *            the new label's text
     */
    public IconLabel(final String text, final AbstractImagePrototype image) {
        this(image, false);
        setText(text);
    }

    /**
     * Creates a label with the specified text and a icon in the right.
     * 
     * @param image
     *            the icon to add
     * @param text
     *            the new label's text
     * @param wordWrap
     *            <code>false</code> to disable word wrapping
     */
    public IconLabel(final String text, final AbstractImagePrototype image, final boolean wordWrap) {
        this(text, image);
        setWordWrap(wordWrap);
    }

    /**
     * Creates a label with the specified text and a icon in the left.
     * 
     * @param image
     *            the icon to add
     * @param text
     *            the new label's text
     * @param wordWrap
     *            <code>false</code> to disable word wrapping
     */
    public IconLabel(final AbstractImagePrototype image, final String text, final boolean wordWrap) {
        this(image, text);
        setWordWrap(wordWrap);
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

    public void addMouseListener(final MouseListener listener) {
        if (mouseListeners == null) {
            mouseListeners = new MouseListenerCollection();
        }
        mouseListeners.add(listener);
    }

    public void addMouseWheelListener(final MouseWheelListener listener) {
        if (mouseWheelListeners == null) {
            mouseWheelListeners = new MouseWheelListenerCollection();
        }
        mouseWheelListeners.add(listener);
    }

    public HorizontalAlignmentConstant getHorizontalAlignment() {
        return horzAlign;
    }

    public String getText() {
        return DOM.getInnerText(textLabel);
    }

    public boolean getWordWrap() {
        return !DOM.getStyleAttribute(textLabel, "whiteSpace").equals("nowrap");
    }

    public void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
            if (clickListeners != null) {
                clickListeners.fireClick(this);
            }
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

        case Event.ONMOUSEWHEEL:
            if (mouseWheelListeners != null) {
                mouseWheelListeners.fireMouseWheelEvent(this, event);
            }
            break;
        case Event.ONDBLCLICK:
            if (doubleClickListeners != null) {
                doubleClickListeners.fireClick(this);
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

    public void removeMouseListener(final MouseListener listener) {
        if (mouseListeners != null) {
            mouseListeners.remove(listener);
        }
    }

    public void removeMouseWheelListener(final MouseWheelListener listener) {
        if (mouseWheelListeners != null) {
            mouseWheelListeners.remove(listener);
        }
    }

    public void setHorizontalAlignment(final HorizontalAlignmentConstant align) {
        horzAlign = align;
        DOM.setStyleAttribute(textLabel, "textAlign", align.getTextAlignString());
    }

    public void setText(final String text) {
        DOM.setInnerText(textLabel, text);
    }

    public void setWordWrap(final boolean wrap) {
        DOM.setStyleAttribute(textLabel, "whiteSpace", wrap ? "normal" : "nowrap");
    }

    public void setColor(final String color) {
        DOM.setStyleAttribute(textLabel, "color", color);
    }

    public void setTitle(final String title) {
        KuneUiUtils.setQuickTip(icon, title);
        KuneUiUtils.setQuickTip(textLabel, title);
    }

}