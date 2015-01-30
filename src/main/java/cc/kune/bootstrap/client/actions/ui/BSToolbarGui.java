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
package cc.kune.bootstrap.client.actions.ui;

import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarCollapseButton;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.constants.NavbarPosition;

import cc.kune.bootstrap.client.ui.ComplexNavbarNav;
import cc.kune.common.client.actions.ui.AbstractBasicGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * The Class BSToolbarGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSToolbarGui extends AbstractBasicGuiItem {

  private ComplexNavbarNav navbarNav;

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
    String id = descriptor.getId();
    if ("undefined".equals(id) || TextUtils.empty(id)) {
      id = HTMLPanel.createUniqueId();
    }

    final Navbar navbar = new Navbar();
    navbar.addStyleName("k-toolbar-navbar");
    final NavbarHeader header = new NavbarHeader();
    final NavbarCollapseButton navbarCollapseButton = new NavbarCollapseButton();
    navbarCollapseButton.setDataTarget("#" + id);
    header.add(navbarCollapseButton);
    navbar.add(header);
    final NavbarCollapse navbarCollapse = new NavbarCollapse();
    navbarCollapse.setId(id);
    navbar.add(navbarCollapse);

    final NavbarPosition position = (NavbarPosition) descriptor.getValue(ToolbarDescriptor.POSITION);
    if (position != null) {
      navbar.setPosition(position);
    }

    navbarNav = new ComplexNavbarNav();
    navbarCollapse.add(navbarNav);

    initWidget(navbar);
    configureItemFromProperties();
    descriptor.putValue(ParentWidget.PARENT_UI, navbarNav);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return true;
  }

}
