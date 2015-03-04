/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.constants.IconType;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractBSChildGuiItem extends AbstractChildGuiItem {

  public AbstractBSChildGuiItem() {
    super();
  }

  public AbstractBSChildGuiItem(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(java.lang.Object)
   */
  @Override
  protected void setIcon(final Object icon) {
    if (icon instanceof IconType) {
      final UIObject widget = descriptor.isChild() ? child : getWidget();
      if (widget instanceof HasIcon) {
        ((HasIcon) widget).setIcon((IconType) icon);
      }
    } else {
      super.setIcon(icon);
    }
  }
}
