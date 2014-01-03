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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.res.KuneIcon;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GxtToolbarGui.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GxtToolbarGui extends AbstractGuiItem implements ParentWidget {

  /** The toolbar. */
  private ToolBar toolbar;

  /**
   * Instantiates a new gxt toolbar gui.
   */
  public GxtToolbarGui() {
    super();
  }

  /**
   * Instantiates a new gxt toolbar gui.
   * 
   * @param descriptor
   *          the descriptor
   */
  public GxtToolbarGui(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.ParentWidget#add(com.google.gwt.user.client
   * .ui.UIObject)
   */
  @Override
  public void add(final UIObject uiObject) {
    toolbar.add((Component) uiObject);
  }

  // public FillToolItem addFill() {
  // final FillToolItem item = new FillToolItem();
  // toolbar.add(item);
  // return item;
  // }
  //
  // public SeparatorToolItem addSeparator() {
  // final SeparatorToolItem item = new SeparatorToolItem();
  // toolbar.add(item);
  // return item;
  // }
  //
  // public LabelToolItem addSpacer() {
  // final LabelToolItem item = new LabelToolItem();
  // toolbar.add(item);
  // return item;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#create(cc.kune.common.
   * client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    toolbar = new ToolBar();
    initWidget(toolbar);
    configureItemFromProperties();
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.ParentWidget#insert(int,
   * com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void insert(final int position, final UIObject uiObject) {
    toolbar.insert((Component) uiObject, position);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  protected void setEnabled(final boolean enabled) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common
   * .shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackground(final String back) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang
   * .String)
   */
  @Override
  protected void setIconStyle(final String style) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String
   * )
   */
  @Override
  public void setIconUrl(final String url) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  protected void setText(final String text) {
  }

}
