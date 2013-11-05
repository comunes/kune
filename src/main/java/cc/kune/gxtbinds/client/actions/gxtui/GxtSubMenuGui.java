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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class GxtSubMenuGui extends AbstractGxtMenuGui {

  private MenuItem item;

  public GxtSubMenuGui() {
    super();
  }

  public GxtSubMenuGui(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.create(descriptor);
    item = new MenuItem();
    item.setSubMenu(menu);
    final AbstractGxtMenuGui parentMenu = ((AbstractGxtMenuGui) descriptor.getParent().getValue(
        ParentWidget.PARENT_UI));
    final int position = descriptor.getPosition();
    if (position == GuiActionDescrip.NO_POSITION) {
      parentMenu.add(item);
    } else {
      parentMenu.insert(position, item);
    }
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    configureItemFromProperties();
    return this;
  }

  @Override
  public void setEnabled(final boolean enabled) {
    item.setVisible(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
  }

  @Override
  public void setIconStyle(final String style) {
    item.setIconStyle(style);
  }

  @Override
  public void setText(final String text) {
    item.setText(text);
  }

  @Override
  public void setToolTipText(final String tooltip) {
    if (TextUtils.notEmpty(tooltip)) {
      item.setToolTip(new GxtDefTooltip(tooltip));
    }
  }

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
  }

}
