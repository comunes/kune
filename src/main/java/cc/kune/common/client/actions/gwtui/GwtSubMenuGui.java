/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.user.client.ui.MenuItem;

public class GwtSubMenuGui extends AbstractGwtMenuGui {

  private IconLabel iconLabel;
  private MenuItem item;

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    super.create(descriptor);
    item = new MenuItem("", menu);
    iconLabel = new IconLabel("");
    configureItemFromProperties();
    final AbstractGwtMenuGui parentMenu = ((AbstractGwtMenuGui) descriptor.getParent().getValue(
        PARENT_UI));
    final int position = descriptor.getPosition();
    if (position == GuiActionDescrip.NO_POSITION) {
      parentMenu.add(item);
    } else {
      parentMenu.insert(position, item);
    }
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    return this;
  }

  private void layout() {
    item.setHTML(iconLabel.toString());
  }

  @Override
  public void setEnabled(final boolean enabled) {
    item.setVisible(enabled);
  }

  @Override
  public void setIconBackground(final String backgroundColor) {
    iconLabel.setLeftIconBackground(backgroundColor);
    layout();
  }

  @Override
  public void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    layout();
  }

  @Override
  public void setIconUrl(final String url) {
    iconLabel.setRightIconUrl(url);
    layout();
  }

  @Override
  public void setText(final String text) {
    iconLabel.setText(text, descriptor.getDirection());
    layout();
  }

  @Override
  public void setToolTipText(final String tooltip) {
    item.setTitle(tooltip);
  }

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
    iconLabel.setVisible(visible);
    layout();
  }
}
