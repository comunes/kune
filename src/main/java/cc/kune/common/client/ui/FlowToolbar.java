/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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

// TODO: Auto-generated Javadoc
/**
 * The Class FlowToolbar.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FlowToolbar extends Composite implements AbstractToolbar, ForIsWidget {

  /** The child panel. */
  private final FlowPanel childPanel;

  /**
   * Instantiates a new flow toolbar.
   */
  public FlowToolbar() {
    super();
    childPanel = new FlowPanel();
    initWidget(childPanel);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#add(com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void add(final Widget widget) {
    childPanel.add(widget);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#add(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant)
   */
  @Override
  public void add(final Widget widget, final VerticalAlignmentConstant valign) {
    childPanel.add(widget);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#addFill()
   */
  @Override
  public Widget addFill() {
    final Label emptyLabel = new Label("");
    emptyLabel.addStyleName("oc-floatright");
    // emptyLabel.setWidth("100%");
    this.add(emptyLabel);
    return emptyLabel;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#addSeparator()
   */
  @Override
  public Widget addSeparator() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("k-tb-sep");
    emptyLabel.addStyleName("oc-tb-sep");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#addSpacer()
   */
  @Override
  public Widget addSpacer() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("oc-tb-spacer");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  /**
   * Clear.
   */
  public void clear() {
    childPanel.clear();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.IndexedPanel#getWidget(int)
   */
  @Override
  public Widget getWidget(final int index) {
    return childPanel.getWidget(index);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetCount()
   */
  @Override
  public int getWidgetCount() {
    return childPanel.getWidgetCount();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.IndexedPanel.ForIsWidget#getWidgetIndex(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public int getWidgetIndex(final IsWidget child) {
    return childPanel.getWidgetIndex(child);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetIndex(com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public int getWidgetIndex(final Widget child) {
    return childPanel.getWidgetIndex(child);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#insert(com.google.gwt.user.client.ui.Widget, int)
   */
  @Override
  public void insert(final Widget widget, final int position) {
    childPanel.insert(widget, position);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.IndexedPanel#remove(int)
   */
  @Override
  public boolean remove(final int index) {
    return childPanel.remove(index);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#remove(com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public boolean remove(final Widget widget) {
    return childPanel.remove(widget);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.AbstractToolbar#removeAll()
   */
  @Override
  public void removeAll() {
    childPanel.clear();
  }

  /**
   * Sets the basic style.
   */
  private void setBasicStyle() {
    setStyleName("x-toolbar-FIXME");
    addStyleName("x-panel-FIXME");
  }

  /**
   * Set the blank style.
   */
  @Override
  public void setBlankStyle() {
    setBasicStyle();
    addStyleName("oc-blank-toolbar");
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
   */
  @Override
  public void setHeight(final String height) {
    childPanel.setHeight(height);
  }

  /**
   * Set the normal grey style.
   */
  @Override
  public void setNormalStyle() {
    setBasicStyle();
    addStyleName("oc-tb-bottom-line");
  }

  /**
   * Set the transparent style.
   */
  public void setTranspStyle() {
    setBasicStyle();
    addStyleName("oc-transp");
  }
}
