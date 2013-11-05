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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.user.client.ui.Widget;

public class GwtToolbarSeparatorGui extends AbstractGuiItem {

  private Widget widget;

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    final GwtToolbarGui toolbar = ((GwtToolbarGui) descriptor.getParent().getValue(
        ParentWidget.PARENT_UI));
    if (toolbar == null) {
      throw new UIException("To add a toolbar separator you need to add the toolbar before. Item: "
          + descriptor);
    }
    final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();
    switch (type) {
    case fill:
      widget = toolbar.addFill();
      break;
    case spacer:
      widget = toolbar.addSpacer();
      break;
    case separator:
      widget = toolbar.addSeparator();
      break;
    default:
      break;
    }
    configureItemFromProperties();
    return toolbar;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
  }

  @Override
  public void setIcon(final KuneIcon icon) {
  }

  @Override
  public void setIconBackground(final String backgroundColor) {
  }

  @Override
  protected void setIconStyle(final String style) {
  }

  @Override
  public void setIconUrl(final String url) {
  }

  @Override
  protected void setText(final String text) {
  }

  @Override
  public void setVisible(final boolean visible) {
    widget.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
