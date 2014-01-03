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
package cc.kune.common.client.ui;

import cc.kune.common.client.tooltip.Tooltip;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class DottedTabPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DottedTabPanel extends Composite {

  /**
   * The Interface DottedTabPanelUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface DottedTabPanelUiBinder extends UiBinder<Widget, DottedTabPanel> {
  }

  /** The ui binder. */
  private static DottedTabPanelUiBinder uiBinder = GWT.create(DottedTabPanelUiBinder.class);

  /** The tab panel. */
  @UiField
  TabLayoutPanel tabPanel;

  /**
   * Instantiates a new dotted tab panel.
   *
   * @param width the width
   * @param height the height
   */
  public DottedTabPanel(final String width, final String height) {
    initWidget(uiBinder.createAndBindUi(this));
    tabPanel.setSize(width, height);
  }

  /**
   * Adds the tab.
   *
   * @param view the view
   * @param tooltip the tooltip
   */
  public void addTab(final IsWidget view, final String tooltip) {
    final DottedTab tab = new DottedTab();
    Tooltip.to(tab, tooltip);
    tabPanel.add(view, tab);
  }

  /**
   * Blink current tab.
   */
  public void blinkCurrentTab() {
    final Widget tab = tabPanel.getTabWidget(tabPanel.getSelectedIndex());
    new BlinkAnimation(tab.getParent(), 350).animate(5);
  }

  /**
   * Gets the widget index.
   *
   * @param view the view
   * @return the widget index
   */
  public int getWidgetIndex(final IsWidget view) {
    return tabPanel.getWidgetIndex(view.asWidget());
  }

  /**
   * Insert tab.
   *
   * @param view the view
   * @param tooltip the tooltip
   * @param beforeIndex the before index
   */
  public void insertTab(final IsWidget view, final String tooltip, final int beforeIndex) {
    final DottedTab tab = new DottedTab();
    Tooltip.to(tab, tooltip);
    tabPanel.insert(view.asWidget(), tab, beforeIndex);
  }

  /**
   * Removes the tab.
   *
   * @param view the view
   */
  public void removeTab(final IsWidget view) {
    tabPanel.remove(view.asWidget());
  }

  /**
   * Select tab.
   *
   * @param index the index
   */
  public void selectTab(final int index) {
    tabPanel.selectTab(index);
  }
}
