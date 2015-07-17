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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;

import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.res.KuneIcon;

/**
 * The Class AbstractBSMenuItemGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractBSMenuItemGui extends AbstractBSChildGuiItem {

  protected ComplexAnchorListItem item;
  protected final CommonResources res = CommonResources.INSTANCE;

  protected abstract void configureClickListener();

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    final String id = descriptor.getId();
    item = createMenuItem();
    if (id != null) {
      item.ensureDebugId(id);
    }
    child = item;
    super.create(descriptor);
    configureItemFromProperties();
    configureClickListener();
    return this;
  }


  protected void closeParentMenu(final ClickEvent event) {
    try {
      getParentMenu(descriptor).hide();
    } catch (final ClassCastException e) {
      Log.error("Failed to close parent widget" + descriptor.getParent());
    }
  }

  protected abstract ComplexAnchorListItem createMenuItem();

  private Widget createShortCut(final KeyStroke key, final String style) {
    return new BSMenuItemShortcut(key, style);
  }

  protected AbstractBSMenuGui getParentMenu(final GuiActionDescrip descriptor) {
    return ((AbstractBSMenuGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
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
  public void setIconResource(final ImageResource icon) {
    item.setIconResource(icon);
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
    final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
    if (key != null) {
      item.setShortcut(createShortCut(key, "oc-mshortcut-hidden").toString()
          + createShortCut(key, "oc-mshortcut").toString());
    }
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
