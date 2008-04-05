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
 * Copyright 2008 Google Inc.
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
 */
package org.ourproject.kune.platf.client.ui.gwtcustom;

import java.util.Iterator;

import org.ourproject.kune.platf.client.ui.SplitterListener;
import org.ourproject.kune.platf.client.ui.SplitterListenerCollection;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract base class for {@link HorizontalSplitPanel} and
 * {@link VerticalSplitPanel}.
 */
abstract class CustomSplitPanel extends Panel {

    /**
     * Sets an elements positioning to absolute.
     * 
     * @param elem
     *                the element
     */
    static void addAbsolutePositoning(final Element elem) {
        DOM.setStyleAttribute(elem, "position", "absolute");
    }

    /**
     * Adds clipping to an element.
     * 
     * @param elem
     *                the element
     */
    static void addClipping(final Element elem) {
        DOM.setStyleAttribute(elem, "overflow", "hidden");
    }

    /**
     * Adds as-needed scrolling to an element.
     * 
     * @param elem
     *                the element
     */
    static void addScrolling(final Element elem) {
        DOM.setStyleAttribute(elem, "overflow", "auto");
    }

    /**
     * Sizes and element to consume the full area of its parent using the CSS
     * properties left, right, top, and bottom. This method is used for all
     * browsers except IE6/7.
     * 
     * @param elem
     *                the element
     */
    static void expandToFitParentUsingCssOffsets(final Element elem) {
        String zeroSize = "0px";

        addAbsolutePositoning(elem);
        setLeft(elem, zeroSize);
        setRight(elem, zeroSize);
        setTop(elem, zeroSize);
        setBottom(elem, zeroSize);
    }

    /**
     * Sizes an element to consume the full areas of its parent using 100% width
     * and height. This method is used on IE6/7 where CSS offsets don't work
     * reliably.
     * 
     * @param elem
     *                the element
     */
    static void expandToFitParentUsingPercentages(final Element elem) {
        String zeroSize = "0px";
        String fullSize = "100%";

        addAbsolutePositoning(elem);
        setTop(elem, zeroSize);
        setLeft(elem, zeroSize);
        setWidth(elem, fullSize);
        setHeight(elem, fullSize);
    }

    /**
     * Returns the offsetHeight element property.
     * 
     * @param elem
     *                the element
     * @return the offsetHeight property
     */
    static int getOffsetHeight(final Element elem) {
        return DOM.getElementPropertyInt(elem, "offsetHeight");
    }

    /**
     * Returns the offsetWidth element property.
     * 
     * @param elem
     *                the element
     * @return the offsetWidth property
     */
    static int getOffsetWidth(final Element elem) {
        return DOM.getElementPropertyInt(elem, "offsetWidth");
    }

    /**
     * Adds zero or none CSS values for padding, margin and border to prevent
     * stylesheet overrides. Returns the element for convenience to support
     * builder pattern.
     * 
     * @param elem
     *                the element
     * @return the element
     */
    static Element preventBoxStyles(final Element elem) {
        DOM.setIntStyleAttribute(elem, "padding", 0);
        DOM.setIntStyleAttribute(elem, "margin", 0);
        DOM.setStyleAttribute(elem, "border", "none");
        return elem;
    }

    /**
     * Convenience method to set bottom offset of an element.
     * 
     * @param elem
     *                the element
     * @param size
     *                a CSS length value for bottom
     */
    static void setBottom(final Element elem, final String size) {
        DOM.setStyleAttribute(elem, "bottom", size);
    }

    /**
     * Sets the elements css class name.
     * 
     * @param elem
     *                the element
     * @param className
     *                the class name
     */
    static void setClassname(final Element elem, final String className) {
        DOM.setElementProperty(elem, "className", className);
    }

    /**
     * Convenience method to set the height of an element.
     * 
     * @param elem
     *                the element
     * @param height
     *                a CSS length value for the height
     */
    static void setHeight(final Element elem, final String height) {
        DOM.setStyleAttribute(elem, "height", height);
    }

    /**
     * Convenience method to set the left offset of an element.
     * 
     * @param elem
     *                the element
     * @param left
     *                a CSS length value for left
     */
    static void setLeft(final Element elem, final String left) {
        DOM.setStyleAttribute(elem, "left", left);
    }

    /**
     * Convenience method to set the right offset of an element.
     * 
     * @param elem
     *                the element
     * @param right
     *                a CSS length value for right
     */
    static void setRight(final Element elem, final String right) {
        DOM.setStyleAttribute(elem, "right", right);
    }

    /**
     * Convenience method to set the top offset of an element.
     * 
     * @param elem
     *                the element
     * @param top
     *                a CSS length value for top
     */
    static void setTop(final Element elem, final String top) {
        DOM.setStyleAttribute(elem, "top", top);
    }

    /**
     * Convenience method to set the width of an element.
     * 
     * @param elem
     *                the element
     * @param width
     *                a CSS length value for the width
     */
    static void setWidth(final Element elem, final String width) {
        DOM.setStyleAttribute(elem, "width", width);
    }

    // The enclosed widgets.
    private final Widget[] widgets = new Widget[2];

    // The elements containing the widgets.
    private final Element[] elements = new Element[2];

    // The element that acts as the splitter.
    private final Element splitElem;

    // Indicates whether drag resizing is active.
    private boolean isResizing = false;

    private ChangeListenerCollection changeListeners;

    private SplitterListenerCollection splitterListeners;

    /**
     * Initializes the split panel.
     * 
     * @param mainElem
     *                the root element for the split panel
     * @param splitElem
     *                the element that acts as the splitter
     * @param headElem
     *                the element to contain the top or left most widget
     * @param tailElem
     *                the element to contain the bottom or right most widget
     */
    CustomSplitPanel(final Element mainElem, final Element splitElem, final Element headElem, final Element tailElem) {
        setElement(mainElem);
        this.splitElem = splitElem;
        elements[0] = headElem;
        elements[1] = tailElem;
        sinkEvents(Event.MOUSEEVENTS);
    }

    @Override
    public void add(final Widget w) {
        if (getWidget(0) == null) {
            setWidget(0, w);
        } else if (getWidget(1) == null) {
            setWidget(1, w);
        } else {
            throw new IllegalStateException("A Splitter can only contain two Widgets.");
        }
    }

    /**
     * Indicates whether the split panel is being resized.
     * 
     * @return <code>true</code> if the user is dragging the splitter,
     *         <code>false</code> otherwise
     */
    public boolean isResizing() {
        return isResizing;
    }

    public Iterator<Widget> iterator() {
        return WidgetIterators.createWidgetIterator(this, widgets);
    }

    @Override
    public void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {

        case Event.ONMOUSEDOWN: {
            Element target = DOM.eventGetTarget(event);
            if (DOM.isOrHasChild(splitElem, target)) {
                startResizingFrom(DOM.eventGetClientX(event) - getAbsoluteLeft(), DOM.eventGetClientY(event)
                        - getAbsoluteTop());
                DOM.setCapture(getElement());
                DOM.eventPreventDefault(event);
                splitterListeners.fireStartResizing(this);
            }
            break;
        }

        case Event.ONMOUSEUP: {
            DOM.releaseCapture(getElement());
            if (isResizing) {
                changeListeners.fireChange(this);
                splitterListeners.fireStopResizing(this);
            }
            stopResizing();
            break;
        }

        case Event.ONMOUSEMOVE: {
            if (isResizing()) {
                assert DOM.getCaptureElement() != null;
                onSplitterResize(DOM.eventGetClientX(event) - getAbsoluteLeft(), DOM.eventGetClientY(event)
                        - getAbsoluteTop());
                DOM.eventPreventDefault(event);
                changeListeners.fireChange(this);
            }
            break;
        }

        }
    }

    public void addChangeListener(final ChangeListener listener) {
        if (changeListeners == null) {
            changeListeners = new ChangeListenerCollection();
        }
        changeListeners.add(listener);
    }

    public void removeChangeListener(final ChangeListener listener) {
        if (changeListeners != null) {
            changeListeners.remove(listener);
        }
    }

    public void addSplitterListener(final SplitterListener listener) {
        if (splitterListeners == null) {
            splitterListeners = new SplitterListenerCollection();
        }
        splitterListeners.add(listener);
    }

    public void removeSplitterListener(final SplitterListener listener) {
        if (splitterListeners != null) {
            splitterListeners.remove(listener);
        }
    }

    @Override
    public boolean remove(final Widget widget) {
        if (widgets[0] == widget) {
            setWidget(0, null);
            return true;
        } else if (widgets[1] == widget) {
            setWidget(1, null);
            return true;
        }
        return false;
    }

    /**
     * Moves the position of the splitter.
     * 
     * @param size
     *                the new size of the left region in CSS units (e.g. "10px",
     *                "1em")
     */
    public abstract void setSplitPosition(String size);

    /**
     * Gets the content element for the given index.
     * 
     * @param index
     *                the index of the element, only 0 and 1 are valid.
     * @return the element
     */
    protected Element getElement(final int index) {
        return elements[index];
    }

    /**
     * Gets the element that is acting as the splitter.
     * 
     * @return the element
     */
    protected Element getSplitElement() {
        return splitElem;
    }

    /**
     * Gets one of the contained widgets.
     * 
     * @param index
     *                the index of the widget, only 0 and 1 are valid.
     * @return the widget
     */
    protected Widget getWidget(final int index) {
        return widgets[index];
    }

    /**
     * <b>Affected Elements:</b>
     * <ul>
     * <li>-splitter = the container containing the splitter element.</li>
     * </ul>
     * 
     * @see UIObject#onEnsureDebugId(String)
     */
    @Override
    protected void onEnsureDebugId(final String baseID) {
        super.onEnsureDebugId(baseID);
        ensureDebugId(splitElem, baseID, "splitter");
    }

    /**
     * Sets one of the contained widgets.
     * 
     * @param index
     *                the index, only 0 and 1 are valid
     * @param w
     *                the widget
     */
    protected final void setWidget(final int index, final Widget w) {
        Widget oldWidget = widgets[index];

        // Validate.
        if (oldWidget == w) {
            return;
        }

        // Detach the new child.
        if (w != null) {
            w.removeFromParent();
        }

        // Remove the old child.
        if (oldWidget != null) {
            // Orphan old.
            orphan(oldWidget);
            // Physical detach old.
            DOM.removeChild(elements[index], oldWidget.getElement());
        }

        // Logical detach old / attach new.
        widgets[index] = w;

        if (w != null) {
            // Physical attach new.
            DOM.appendChild(elements[index], w.getElement());

            // Adopt new.
            adopt(w);
        }
    }

    /**
     * Called on each mouse drag event as the user is dragging the splitter.
     * 
     * @param x
     *                the x coordinate of the mouse relative to the panel's
     *                extent
     * @param y
     *                the y coordinate of the mosue relative to the panel's
     *                extent
     */
    abstract void onSplitterResize(int x, int y);

    /**
     * Called when the user starts dragging the splitter.
     * 
     * @param x
     *                the x coordinate of the mouse relative to the panel's
     *                extent
     * @param y
     *                the y coordinate of the mouse relative to the panel's
     *                extent
     */
    abstract void onSplitterResizeStarted(int x, int y);

    private void startResizingFrom(final int x, final int y) {
        isResizing = true;
        onSplitterResizeStarted(x, y);
    }

    private void stopResizing() {
        isResizing = false;
    }
}
