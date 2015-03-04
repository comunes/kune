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
package cc.kune.polymer.client.actions.ui;

import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class PoToolbarGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PoToolbarGui extends AbstractBasicGuiItem implements ParentWidget {

  private boolean shouldBeAdded;
  private HTMLPanel toolbar;

  @Override
  public void add(final UIObject uiObject) {
    toolbar.add((Widget) uiObject);
  }

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
    final String id = descriptor.getId();

    if ("undefined".equals(id) || TextUtils.empty(id)) {
      shouldBeAdded = true;
      toolbar = new HTMLPanel("");
      toolbar.getElement().setAttribute("horizontal", "");
      toolbar.getElement().setAttribute("layout", "");
    } else { // Already created (polymer) element
      shouldBeAdded = false;
      toolbar = HTMLPanel.wrap(DOM.getElementById(id));
    }

    initWidget(toolbar);
    configureItemFromProperties();
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    return this;
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    add(uiObject);
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return shouldBeAdded;
  }

}
