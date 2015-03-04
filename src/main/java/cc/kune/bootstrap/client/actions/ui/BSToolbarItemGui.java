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

import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.res.KuneIcon;

public class BSToolbarItemGui extends AbstractBSChildGuiItem {

  private ComplexAnchorListItem item;

  @Override
  protected void addStyle(final String style) {
    item.addStyleName(style);
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    item = new ComplexAnchorListItem();
    item.addClickHandler(clickHandlerChildDefautl);
    if (descriptor.isChild()) {
      child = item;
    } else {
      initWidget(item);
    }
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
    item.setIcon(icon);
  }

  @Override
  protected void setIconBackColor(final String backgroundColor) {
    item.setIconBackColor(backgroundColor);
  }

  @Override
  protected void setIconStyle(final String style) {
    item.setIconStyle(style);

  }

  @Override
  public void setIconUrl(final String url) {
    item.setIconUrl(url);

  }

  @Override
  protected void setText(final String text) {
    item.setText(text);

  }

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }

}
