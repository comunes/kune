/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

package cc.kune.sandbox.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class WrappedFlowPanel extends ComplexPanel implements InsertPanel.ForIsWidget {
  public static WrappedFlowPanel wrap(final Element element) {
    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    final WrappedFlowPanel flow = new WrappedFlowPanel();
    flow.setElement(element);

    // Mark it attached and remember it for cleanup.
    flow.onAttach();
    RootPanel.detachOnWindowClose(flow);

    return flow;
  }

  public static WrappedFlowPanel wrap(final String id) {
    return wrap(DOM.getElementById(id));
  }

  /**
   * Adds a new child widget to the panel.
   *
   * @param w
   *          the widget to be added
   */
  @Override
  public void add(final Widget w) {
    add(w, getElement());
  }

  @Override
  public void clear() {
    getElement().removeAllChildren();
  }

  @Override
  public void insert(final IsWidget w, final int beforeIndex) {
    insert(asWidgetOrNull(w), beforeIndex);
  }

  /**
   * Inserts a widget before the specified index.
   *
   * @param w
   *          the widget to be inserted
   * @param beforeIndex
   *          the index before which it will be inserted
   * @throws IndexOutOfBoundsException
   *           if <code>beforeIndex</code> is out of range
   */
  @Override
  public void insert(final Widget w, final int beforeIndex) {
    insert(w, getElement(), beforeIndex, true);
  }
}
