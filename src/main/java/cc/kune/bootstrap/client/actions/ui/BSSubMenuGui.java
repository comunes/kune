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
package cc.kune.bootstrap.client.actions.ui;

import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.bootstrap.client.ui.DropDownSubmenu;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class BSSubMenuGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSSubMenuGui extends AbstractChildGuiItem implements AbstractBSMenuGui { // HasMenuItem

  private ComplexAnchorListItem anchorList;

  /** The parent menu. */
  private ParentWidget parentMenu;

  @Override
  public void add(final UIObject uiObject) {
    // anchorList.add((Widget) uiObject);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui#create(cc.kune.common
   * .client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    super.create(descriptor);

    anchorList = new ComplexAnchorListItem("");
    final DropDownSubmenu submenu = new DropDownSubmenu();
    anchorList.add(submenu);
    configureItemFromProperties();
    parentMenu = ((ParentWidget) descriptor.getParent().getValue(PARENT_UI));
    final int position = descriptor.getPosition();
    if (position == GuiActionDescrip.NO_POSITION) {
      parentMenu.add(anchorList);
    } else {
      parentMenu.insert(position, anchorList);
    }
    descriptor.putValue(ParentWidget.PARENT_UI, submenu);
    descriptor.putValue(MenuItemDescriptor.UI, this);
    return this;
  }

  @Override
  public void insert(final int position, final UIObject widget) {
    anchorList.insert((Widget) widget, position);

  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    anchorList.setEnabled(enabled);
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
    anchorList.setIcon(icon);

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackColor(final String backgroundColor) {
    anchorList.setIconBackColor(backgroundColor);

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang
   * .String)
   */
  @Override
  public void setIconStyle(final String style) {
    anchorList.setIconStyle(style);

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
    anchorList.setIconUrl(url);

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    // TODO: , descriptor.getDirection()
    anchorList.setText(text);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang
   * .String)
   */
  @Override
  public void setToolTipText(final String tooltip) {
    if (TextUtils.notEmpty(tooltip)) {
      Tooltip.to(anchorList, tooltip);
      // anchorList.setTitle(tooltip);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    anchorList.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see cc.kune.common.client.actions.gwtui.HasMenuItem#getMenuItem()
  // */
  // @Override
  // public MenuItem getMenuItem() {
  // return item;
  // }

  @Override
  public void show() {
    // TODO
    // parentMenu.show();
    // ((MenuDescriptor) descriptor.getParent()).selectMenu((MenuItemDescriptor)
    // descriptor);
  }
}
