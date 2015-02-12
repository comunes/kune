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
package cc.kune.polymer.client.actions.ui;

import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class PolymerToolbarSeparatorGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PoToolbarSeparatorGui extends AbstractBasicGuiItem {

  /** The widget. */
  private Widget widget;

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
    final PoToolbarGui toolbar = ((PoToolbarGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
    if (toolbar == null) {
      throw new UIException("To add a toolbar separator you need to add the toolbar before. Item: "
          + descriptor);
    }
    final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();

    switch (type) {
    case fill:
      widget = new HTMLPanel("");
      widget.getElement().setAttribute("flex", "");
      break;
    case spacer:
      widget = new Label(" ");
    case separator:
      // We'll not use | separator in polymer gui
      widget = new Label("");
      break;
    default:
      break;
    }
    toolbar.add(widget);
    widget.addStyleName("k-toolbar-separator");
    configureItemFromProperties();
    return this;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    widget.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
