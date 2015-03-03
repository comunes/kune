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

package cc.kune.common.client.ui;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class WrappedFlowPanel extends Composite implements InsertPanel.ForIsWidget {

  public static WrappedFlowPanel wrap(final Element element) {
    final HTMLPanel parent = HTMLPanel.wrap(element);
    final WrappedFlowPanel child = new WrappedFlowPanel();
    parent.add(child);
    return child;
  }

  public static WrappedFlowPanel wrap(final String id) {
    try {
      final Element element = DOM.getElementById(id);
      if (element == null) {
        throw new UIException("Errow wrapping id " + id);
      }
      return wrap(element);
    }
  }
  private final FlowPanel flow;

  public WrappedFlowPanel() {
    flow = new FlowPanel();
    initWidget(flow);
  }

  @Override
  public void add(final IsWidget w) {
    flow.add(w);
  }

  @Override
  public void add(final Widget w) {
    flow.add(w);
  }

  public void clear() {
    flow.clear();
  }

  @Override
  public Widget getWidget(final int index) {
    return flow.getWidget(index);
  }

  @Override
  public int getWidgetCount() {
    return flow.getWidgetCount();
  }

  @Override
  public int getWidgetIndex(final IsWidget child) {
    return flow.getWidgetIndex(child);
  }

  @Override
  public int getWidgetIndex(final Widget child) {
    return flow.getWidgetIndex(child);
  }

  @Override
  public void insert(final IsWidget w, final int beforeIndex) {
    flow.insert(w, beforeIndex);

  }

  @Override
  public void insert(final Widget w, final int beforeIndex) {
    flow.insert(w, beforeIndex);
  }

  @Override
  public boolean remove(final int index) {
    return flow.remove(index);
  }

}
