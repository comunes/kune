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
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.user.client.ui.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * The Class GwtSubMenuGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtSubMenuGui extends AbstractGwtMenuGui implements HasMenuItem {

  /** The icon label. */
  private IconLabel iconLabel;
  
  /** The item. */
  private MenuItem item;
  
  /** The parent menu. */
  private AbstractGwtMenuGui parentMenu;

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    super.create(descriptor);
    item = new MenuItem("", menu);
    iconLabel = new IconLabel("");
    configureItemFromProperties();
    parentMenu = ((AbstractGwtMenuGui) descriptor.getParent().getValue(PARENT_UI));
    final int position = descriptor.getPosition();
    if (position == GuiActionDescrip.NO_POSITION) {
      parentMenu.add(item);
    } else {
      parentMenu.insert(position, item);
    }
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    descriptor.putValue(MenuItemDescriptor.UI, this);
    return this;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.gwtui.HasMenuItem#getMenuItem()
   */
  @Override
  public MenuItem getMenuItem() {
    return item;
  }

  /**
   * Layout.
   */
  private void layout() {
    item.setHTML(iconLabel.toString());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common.shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    iconLabel.setLeftIconFont(icon);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java.lang.String)
   */
  @Override
  public void setIconBackground(final String backgroundColor) {
    iconLabel.setLeftIconBackground(backgroundColor);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang.String)
   */
  @Override
  public void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String)
   */
  @Override
  public void setIconUrl(final String url) {
    iconLabel.setRightIconUrl(url);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    iconLabel.setText(text, descriptor.getDirection());
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang.String)
   */
  @Override
  public void setToolTipText(final String tooltip) {
    if (TextUtils.notEmpty(tooltip)) {
      item.setTitle(tooltip);
    }
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
    iconLabel.setVisible(visible);
    // FIXME the ">" still visible when hidden
    // This does not works:
    // item.getElement().getStyle().setVisibility(
    // visible ? Visibility.VISIBLE : Visibility.HIDDEN);
    // item.getElement().getParentElement().getStyle().setVisibility(
    // visible ? Visibility.VISIBLE : Visibility.HIDDEN);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui#show()
   */
  @Override
  protected void show() {
    parentMenu.show();
    ((MenuDescriptor) descriptor.getParent()).selectMenu((MenuItemDescriptor) descriptor);
  }
}
