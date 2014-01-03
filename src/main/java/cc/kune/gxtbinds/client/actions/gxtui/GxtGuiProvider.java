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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GxtGuiProvider.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GxtGuiProvider {

  /**
   * Instantiates a new gxt gui provider.
   * 
   * @param guiProvider
   *          the gui provider
   * @param gxtSubMenuGui
   *          the gxt sub menu gui
   * @param gxtMenuGui
   *          the gxt menu gui
   * @param gxtMenuItemGui
   *          the gxt menu item gui
   * @param gxtMenuSeparatorGui
   *          the gxt menu separator gui
   * @param gxtPushButtonGui
   *          the gxt push button gui
   * @param gxtButtonGui
   *          the gxt button gui
   * @param gwtIconLabelGui
   *          the gwt icon label gui
   * @param gxtToolbarGui
   *          the gxt toolbar gui
   * @param gxtToolbarSeparatorGui
   *          the gxt toolbar separator gui
   * @param gxtMenuTitleItemGui
   *          the gxt menu title item gui
   */
  @Inject
  public GxtGuiProvider(final GuiProvider guiProvider, final Provider<GxtSubMenuGui> gxtSubMenuGui,
      final Provider<GxtMenuGui> gxtMenuGui, final Provider<GxtMenuItemGui> gxtMenuItemGui,
      final Provider<GxtMenuSeparatorGui> gxtMenuSeparatorGui,
      final Provider<GxtPushButtonGui> gxtPushButtonGui, final Provider<GxtButtonGui> gxtButtonGui,
      final Provider<GwtIconLabelGui> gwtIconLabelGui, final Provider<GxtToolbarGui> gxtToolbarGui,
      final Provider<GxtToolbarSeparatorGui> gxtToolbarSeparatorGui,
      final Provider<GxtMenuTitleItemGui> gxtMenuTitleItemGui) {

    guiProvider.register(SubMenuDescriptor.class, gxtSubMenuGui);
    guiProvider.register(MenuDescriptor.class, gxtMenuGui);
    guiProvider.register(MenuRadioItemDescriptor.class, gxtMenuItemGui);
    guiProvider.register(MenuCheckItemDescriptor.class, gxtMenuItemGui);
    guiProvider.register(MenuTitleItemDescriptor.class, gxtMenuTitleItemGui);
    guiProvider.register(MenuItemDescriptor.class, gxtMenuItemGui);
    guiProvider.register(MenuSeparatorDescriptor.class, gxtMenuSeparatorGui);
    guiProvider.register(PushButtonDescriptor.class, gxtPushButtonGui);
    guiProvider.register(ButtonDescriptor.class, gxtButtonGui);
    guiProvider.register(IconLabelDescriptor.class, gwtIconLabelGui);
    guiProvider.register(ToolbarDescriptor.class, gxtToolbarGui);
    guiProvider.register(ToolbarSeparatorDescriptor.class, gxtToolbarSeparatorGui);

  }
}
