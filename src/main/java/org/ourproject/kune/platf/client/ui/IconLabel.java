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
 \*/
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWordWrap;
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

public class IconLabel extends Widget implements HasClickHandlers, HasDoubleClickHandlers, HasAllMouseHandlers,
        HasHorizontalAlignment, HasText, HasWordWrap, AbstractLabel {

    private HorizontalAlignmentConstant horzAlign;

    private Element icon;

    private final Element textLabel;

    /**
     * Creates an empty label with a icon.
     * 
     * @param image
     *            the icon to add
     */
    public IconLabel(final AbstractImagePrototype image, final boolean leftIcon) {
        setElement(DOM.createDiv());
        setIconImpl(image);
        textLabel = DOM.createSpan();
        if (leftIcon) {
            setStyleName("kune-IconLabel-l");
            DOM.appendChild(getElement(), icon);
            DOM.appendChild(getElement(), textLabel);
        } else {
            setStyleName("kune-IconLabel-r");
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
        setTextImpl(text);
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
        setWordWrapImpl(wordWrap);
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
        setTextImpl(text);
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
        setWordWrapImpl(wordWrap);
    }

    public HandlerRegistration addClickHandler(final ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addDoubleClickHandler(final DoubleClickHandler handler) {
        return addDomHandler(handler, DoubleClickEvent.getType());
    }

    public HandlerRegistration addMouseDownHandler(final MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
    }

    public HandlerRegistration addMouseMoveHandler(final MouseMoveHandler handler) {
        return addDomHandler(handler, MouseMoveEvent.getType());
    }

    public HandlerRegistration addMouseOutHandler(final MouseOutHandler handler) {
        return addDomHandler(handler, MouseOutEvent.getType());
    }

    public HandlerRegistration addMouseOverHandler(final MouseOverHandler handler) {
        return addDomHandler(handler, MouseOverEvent.getType());
    }

    public HandlerRegistration addMouseUpHandler(final MouseUpHandler handler) {
        return addDomHandler(handler, MouseUpEvent.getType());
    }

    public HandlerRegistration addMouseWheelHandler(final MouseWheelHandler handler) {
        return addDomHandler(handler, MouseWheelEvent.getType());
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

    public void setColor(final String color) {
        DOM.setStyleAttribute(textLabel, "color", color);
    }

    public void setHorizontalAlignment(final HorizontalAlignmentConstant align) {
        horzAlign = align;
        DOM.setStyleAttribute(textLabel, "textAlign", align.getTextAlignString());
    }

    public void setIcon(final AbstractImagePrototype image) {
        setIconImpl(image);
    }

    public void setStyleNameToText(final String styleName) {
        DOM.setElementProperty(textLabel.<com.google.gwt.user.client.Element> cast(), "className", styleName);
    }

    public void setText(final String text) {
        setTextImpl(text);
    }

    @Override
    public void setTitle(final String title) {
        KuneUiUtils.setQuickTip(icon, title);
        KuneUiUtils.setQuickTip(textLabel, title);
    }

    public void setWordWrap(final boolean wrap) {
        setWordWrapImpl(wrap);
    }

    private Element getImageElement(final AbstractImagePrototype image) {
        return image.createImage().getElement();
    }

    private void setIconImpl(final AbstractImagePrototype image) {
        icon = getImageElement(image);
    }

    private void setTextImpl(final String text) {
        DOM.setInnerText(textLabel, text);
    }

    private void setWordWrapImpl(final boolean wrap) {
        DOM.setStyleAttribute(textLabel, "whiteSpace", wrap ? "normal" : "nowrap");
    }

}