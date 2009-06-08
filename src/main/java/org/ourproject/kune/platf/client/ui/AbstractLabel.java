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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public interface AbstractLabel {

    HandlerRegistration addClickHandler(final ClickHandler handler);

    HandlerRegistration addDoubleClickHandler(final DoubleClickHandler handler);

    HandlerRegistration addMouseDownHandler(final MouseDownHandler handler);

    HandlerRegistration addMouseMoveHandler(final MouseMoveHandler handler);

    HandlerRegistration addMouseOutHandler(final MouseOutHandler handler);

    HandlerRegistration addMouseOverHandler(final MouseOverHandler handler);

    HandlerRegistration addMouseUpHandler(final MouseUpHandler handler);

    HandlerRegistration addMouseWheelHandler(final MouseWheelHandler handler);

    void addStyleDependentName(String string);

    Element getElement();

    String getText();

    void onBrowserEvent(final Event event);

    void removeStyleDependentName(String string);

    void setColor(final String color);

    void setStylePrimaryName(String string);

    void setText(final String text);

    void setTitle(final String title);

    void setVisible(boolean visible);

}