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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.user.client.ui.MenuItemSeparator;

public class GwtMenuSeparatorGui extends AbstractGuiItem {

  private MenuItemSeparator separator;

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    final AbstractGwtMenuGui menu = ((AbstractGwtMenuGui) descriptor.getParent().getValue(
        ParentWidget.PARENT_UI));
    if (menu == null) {
      throw new UIException("To add a menu separator you need to add the menu before. Item: "
          + descriptor);
    }
    separator = menu.addSeparator();
    return menu;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    separator.setVisible(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
  }

  @Override
  protected void setIconBackground(final String backgroundColor) {
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
    separator.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }
}
