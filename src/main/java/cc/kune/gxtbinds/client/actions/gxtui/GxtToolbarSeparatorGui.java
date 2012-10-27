/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;

import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;

public class GxtToolbarSeparatorGui extends AbstractChildGuiItem {

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    final GxtToolbarGui toolbar = (GxtToolbarGui) parent;
    final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();
    switch (type) {
    case fill:
      child = new FillToolItem();
      break;
    case spacer:
      child = new LabelToolItem();
      break;
    case separator:
      child = new SeparatorToolItem();
      break;
    default:
      break;
    }
    super.create(descriptor);
    configureItemFromProperties();
    return toolbar;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    // do nothing
  }

  @Override
  public void setIconBackground(final String back) {
  }

  @Override
  protected void setIconStyle(final String style) {
    // do nothing
  }

  @Override
  public void setIconUrl(final String url) {
  }

  @Override
  protected void setText(final String text) {
    // do nothing
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
