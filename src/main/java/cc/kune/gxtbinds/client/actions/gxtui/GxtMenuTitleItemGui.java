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
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;

import com.extjs.gxt.ui.client.widget.menu.HeaderMenuItem;

public class GxtMenuTitleItemGui extends AbstractChildGuiItem {

  private HeaderMenuItem item;

  public GxtMenuTitleItemGui() {
    super();
  }

  public GxtMenuTitleItemGui(final MenuItemDescriptor descriptor) {
    super(descriptor);

  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    item = new HeaderMenuItem("");

    final String id = descriptor.getId();
    if (id != null) {
      item.ensureDebugId(id);
    }
    child = item;
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  public HeaderMenuItem getItem() {
    return item;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setVisible(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
  }

  @Override
  public void setIconBackground(final String back) {
  }

  @Override
  protected void setIconStyle(final String style) {
    item.addStyleName(style);
  }

  @Override
  public void setIconUrl(final String url) {
    throw new NotImplementedException();
  }

  @Override
  protected void setText(final String text) {
    if (text != null) {
      item.setText(text);
    }
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

  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return false;
  }
}
