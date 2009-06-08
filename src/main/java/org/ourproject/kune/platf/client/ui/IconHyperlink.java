/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
import com.google.gwt.user.client.ui.Widget;

public class IconHyperlink extends Widget implements AbstractLabel, HasClickHandlers, HasDoubleClickHandlers,
        HasAllMouseHandlers {

    private Element anchorElem;

    private String targetHistoryToken;
    private final Element link;
    private final Element icon;

    public IconHyperlink(final AbstractImagePrototype image) {
        setElement(DOM.createDiv());
        DOM.appendChild(getElement(), anchorElem = DOM.createAnchor());
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
            setLabelHtmlImpl(text);
        } else {
            setLabelTextImpl(text);
        }
        setTargetHistoryTokenImpl(targetHistoryToken);
    }

    public IconHyperlink(final AbstractImagePrototype image, final String text, final String targetHistoryToken) {
        this(image);
        setLabelTextImpl(text);
        setTargetHistoryTokenImpl(targetHistoryToken);
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

    public String getTargetHistoryToken() {
        return targetHistoryToken;
    }

    public String getText() {
        return DOM.getInnerText(link);
    }

    public void setColor(final String color) {
        DOM.setStyleAttribute(link, "color", color);
    }

    public void setImage(final AbstractImagePrototype image) {
        DOM.setInnerHTML(icon, image.getHTML());
    }

    public void setLabelHTML(final String html) {
        setLabelHtmlImpl(html);
    }

    public void setLabelText(final String text) {
        setLabelTextImpl(text);
    }

    public void setTargetHistoryToken(final String targetHistoryToken) {
        setTargetHistoryTokenImpl(targetHistoryToken);
    }

    public void setText(final String text) {
        setLabelText(text);
    }

    @Override
    public void setTitle(final String title) {
        KuneUiUtils.setQuickTip(icon, title);
        KuneUiUtils.setQuickTip(anchorElem, title);
    }

    private void setLabelHtmlImpl(final String html) {
        DOM.setInnerHTML(link, html);
    }

    private void setLabelTextImpl(final String text) {
        DOM.setInnerText(link, text);
    }

    private void setTargetHistoryTokenImpl(final String targetHistoryToken) {
        this.targetHistoryToken = targetHistoryToken;
        DOM.setElementProperty(anchorElem, "href", "#" + targetHistoryToken);
    }
}