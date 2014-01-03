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
package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiChildBinding.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class GuiChildBinding extends AbstractGuiBinding {

  /** The child. */
  protected UIObject child;
  
  /** The parent. */
  protected ParentWidget parent;

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiBinding#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    final int position = descriptor.getPosition();
    if (descriptor.isChild()) {
      // A menu item is a child, a toolbar separator, also. A button can
      // be a child of a toolbar or not
      parent = ((ParentWidget) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
      if (parent == null) {
        throw new UIException("To add a item you need to add its parent before. Item: " + descriptor);
      }
      if (child != null) {
        // Sometimes (menu/toolbar separators), there is no Widget to
        // add/insert
        if (position == GuiActionDescrip.NO_POSITION) {
          parent.add(child);
        } else {
          parent.insert(position, child);
        }
      }
    }
    return super.create(descriptor);
  }
}
