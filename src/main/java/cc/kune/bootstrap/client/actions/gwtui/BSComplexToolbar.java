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
package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Navbar;

import cc.kune.common.client.actions.ui.AbstractGuiItem;

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
public class BSComplexToolbar extends Composite implements IsWidget {

  /** The toolbar. */
  private final Navbar toolbar;

  /**
   * Instantiates a new gwt complex toolbar.
   */
  public BSComplexToolbar() {
    toolbar = new Navbar();
    initWidget(toolbar);
  }

  /**
   * Adds the.
   *
   * @param item
   *          the item
   */
  protected void add(final AbstractGuiItem item) {
    item.addStyleName(getFlow());
    toolbar.add(item);
  }

  /**
   * Adds the.
   *
   * @param uiObject
   *          the ui object
   */
  public void add(final UIObject uiObject) {
    uiObject.addStyleName(getFlow());
    toolbar.add((Widget) uiObject);
  }

  public Widget addFill() {
    final Label emptyLabel = new Label("");
    emptyLabel.addStyleName("oc-floatright");
    // emptyLabel.setWidth("100%");
    this.add(emptyLabel);
    return emptyLabel;
  }

  public Widget addSeparator() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("k-tb-sep");
    emptyLabel.addStyleName("oc-tb-sep");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  public Widget addSpacer() {
    final Label emptyLabel = new Label("");
    emptyLabel.setStyleName("oc-tb-spacer");
    emptyLabel.addStyleName("oc-floatleft");
    this.add(emptyLabel);
    return emptyLabel;
  }

  /**
   * Gets the flow.
   *
   * @return the flow
   */
  private String getFlow() {
    // TODO
    return "";
  }

  /**
   * Insert.
   *
   * @param item
   *          the item
   * @param position
   *          the position
   */
  protected void insert(final AbstractGuiItem item, final int position) {
    item.addStyleName(getFlow());
    toolbar.insert(item, position);
  }

  /**
   * Insert.
   *
   * @param uiObject
   *          the ui object
   * @param position
   *          the position
   */
  public void insert(final UIObject uiObject, final int position) {
    uiObject.addStyleName(getFlow());
    toolbar.insert((Widget) uiObject, position);
  }

  /**
   * Set the blank style.
   */
  public void setCleanStyle() {
    // FIXME
  }

  /**
   * Set the normal grey style.
   */
  public void setNormalStyle() {
    // FIXME
  }

  /**
   * Set the blank style.
   */
  public void setTranspStyle() {
    // FIXME
  }

}
