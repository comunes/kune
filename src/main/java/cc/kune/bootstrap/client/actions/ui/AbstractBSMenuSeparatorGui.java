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

import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.Widget;

/**
 * The Class AbstractMenuSeparatorGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractBSMenuSeparatorGui extends AbstractBasicGuiItem {

  protected Widget separator;

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
    final AbstractBSMenuGui menu = ((AbstractBSMenuGui) descriptor.getParent().getValue(
        ParentWidget.PARENT_UI));
    if (menu == null) {
      throw new UIException("To add a menu separator you need to add the menu before. Item: "
          + descriptor);
    }
    separator = createSeparator();
    menu.add(separator);
    configureItemFromProperties();
    return menu;
  }

  protected abstract Widget createSeparator();

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  protected void setEnabled(final boolean enabled) {
    separator.setVisible(enabled);
  }

  @Override
  public void setVisible(final boolean visible) {
    separator.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
