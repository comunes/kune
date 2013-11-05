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
 \*/
package cc.kune.common.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.IndexedPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FlowToolbar extends Composite implements AbstractToolbar, ForIsWidget {

  private final FlowPanel childPanel;

  public FlowToolbar() {
    super();
    childPanel = new FlowPanel();
    initWidget(childPanel);
  }

  @Override
  public void add(final Widget widget) {
    childPanel.add(widget);
  }

  @Override
  public void add(final Widget widget, final VerticalAlignmentConstant valign) {
    childPanel.add(widget);
  }

  @Override
  public Widget addFill() {
    final Label emptyLabel = new Label("");
    emptyLabel.addStyleName("oc-floatright");
    // emptyLabel.setWidth("100%");
    this.add(emptyLabel);
    return emptyLabel;
  }

  @Override
  public Widget addSeparator() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("k-tb-sep");
    emptyLabel.addStyleName("oc-tb-sep");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  @Override
  public Widget addSpacer() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("oc-tb-spacer");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  public void clear() {
    childPanel.clear();
  }

  @Override
  public Widget getWidget(final int index) {
    return childPanel.getWidget(index);
  }

  @Override
  public int getWidgetCount() {
    return childPanel.getWidgetCount();
  }

  @Override
  public int getWidgetIndex(final IsWidget child) {
    return childPanel.getWidgetIndex(child);
  }

  @Override
  public int getWidgetIndex(final Widget child) {
    return childPanel.getWidgetIndex(child);
  }

  @Override
  public void insert(final Widget widget, final int position) {
    childPanel.insert(widget, position);
  }

  @Override
  public boolean remove(final int index) {
    return childPanel.remove(index);
  }

  @Override
  public boolean remove(final Widget widget) {
    return childPanel.remove(widget);
  }

  @Override
  public void removeAll() {
    childPanel.clear();
  }

  private void setBasicStyle() {
    setStyleName("x-toolbar-FIXME");
    addStyleName("x-panel-FIXME");
  }

  /**
   * Set the blank style
   */
  @Override
  public void setBlankStyle() {
    setBasicStyle();
    addStyleName("oc-blank-toolbar");
  }

  @Override
  public void setHeight(final String height) {
    childPanel.setHeight(height);
  }

  /**
   * Set the normal grey style
   */
  @Override
  public void setNormalStyle() {
    setBasicStyle();
    addStyleName("oc-tb-bottom-line");
  }

  /**
   * Set the transparent style
   */
  public void setTranspStyle() {
    setBasicStyle();
    addStyleName("oc-transp");
  }
}
