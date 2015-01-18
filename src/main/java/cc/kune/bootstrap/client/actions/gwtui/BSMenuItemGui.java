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
package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.res.KuneIcon;

/**
 * The Class GwtMenuItemGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSMenuItemGui extends AbstractChildGuiItem {

  private AnchorListItem item;

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    item = new AnchorListItem();
    child = item;
    return this;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setIconBackColor(final String backgroundColor) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setIconStyle(final String style) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setIconUrl(final String url) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void setText(final String text) {
    item.setText(text);
  }

}
