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
 */
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.ui.FlowToolbar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class GwtComplexToolbar.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtComplexToolbar extends Composite implements IsWidget {

  /**
   * The Enum FlowDir.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  private enum FlowDir {
    
    /** The left. */
    left, 
 /** The right. */
 right
  }
  
  /** The current flow. */
  private FlowDir currentFlow;
  
  /** The toolbar. */
  private final FlowToolbar toolbar;

  /**
   * Instantiates a new gwt complex toolbar.
   */
  public GwtComplexToolbar() {
    toolbar = new FlowToolbar();
    currentFlow = FlowDir.left;
    initWidget(toolbar);
  }

  /**
   * Adds the.
   *
   * @param item the item
   */
  protected void add(final AbstractGuiItem item) {
    item.addStyleName(getFlow());
    toolbar.add(item);
  }

  /**
   * Adds the.
   *
   * @param uiObject the ui object
   */
  public void add(final UIObject uiObject) {
    uiObject.addStyleName(getFlow());
    toolbar.add((Widget) uiObject);
  }

  /**
   * Adds the fill.
   *
   * @return the widget
   */
  public Widget addFill() {
    currentFlow = FlowDir.right;
    return toolbar.addFill();
  }

  /**
   * Adds the separator.
   *
   * @return the widget
   */
  public Widget addSeparator() {
    return toolbar.addSeparator();
  }

  /**
   * Adds the spacer.
   *
   * @return the widget
   */
  public Widget addSpacer() {
    return toolbar.addSpacer();
  }

  /**
   * Gets the flow.
   *
   * @return the flow
   */
  private String getFlow() {
    switch (currentFlow) {
    case left:
      return "oc-floatleft";
    case right:
    default:
      return "oc-floatright";
    }
  }

  /**
   * Insert.
   *
   * @param item the item
   * @param position the position
   */
  protected void insert(final AbstractGuiItem item, final int position) {
    item.addStyleName(getFlow());
    toolbar.insert(item, position);
  }

  /**
   * Insert.
   *
   * @param uiObject the ui object
   * @param position the position
   */
  public void insert(final UIObject uiObject, final int position) {
    uiObject.addStyleName(getFlow());
    toolbar.insert((Widget) uiObject, position);
  }

  /**
   * Set the blank style.
   */
  public void setCleanStyle() {
    toolbar.setBlankStyle();
  }

  /**
   * Set the normal grey style.
   */
  public void setNormalStyle() {
    toolbar.setNormalStyle();
  }

  /**
   * Set the blank style.
   */
  public void setTranspStyle() {
    toolbar.setTranspStyle();
  }

}
