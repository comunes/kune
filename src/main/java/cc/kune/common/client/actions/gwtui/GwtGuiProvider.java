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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
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
 * The Class GwtGuiProvider.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GwtGuiProvider {

  /**
   * Instantiates a new gwt gui provider.
   *
   * @param guiProvider the gui provider
   * @param gwtSubMenuGui the gwt sub menu gui
   * @param gwtMenuGui the gwt menu gui
   * @param gwtMenuItemGui the gwt menu item gui
   * @param gwtMenuSeparatorGui the gwt menu separator gui
   * @param gwtPushButtonGui the gwt push button gui
   * @param gwtButtonGui the gwt button gui
   * @param gwtLabelGui the gwt label gui
   * @param gwtIconLabelGui the gwt icon label gui
   * @param gwtToolbarGui the gwt toolbar gui
   * @param gwtToolbarSeparatorGui the gwt toolbar separator gui
   * @param gwtMenuTitleItemGui the gwt menu title item gui
   */
  @Inject
  public GwtGuiProvider(final GuiProvider guiProvider, final Provider<GwtSubMenuGui> gwtSubMenuGui,
      final Provider<GwtMenuGui> gwtMenuGui, final Provider<GwtMenuItemGui> gwtMenuItemGui,
      final Provider<GwtMenuSeparatorGui> gwtMenuSeparatorGui,
      final Provider<GwtPushButtonGui> gwtPushButtonGui, final Provider<GwtButtonGui> gwtButtonGui,
      final Provider<GwtLabelGui> gwtLabelGui, final Provider<GwtIconLabelGui> gwtIconLabelGui,
      final Provider<GwtToolbarGui> gwtToolbarGui,
      final Provider<GwtToolbarSeparatorGui> gwtToolbarSeparatorGui,
      final Provider<GwtMenuTitleItemGui> gwtMenuTitleItemGui) {

    guiProvider.register(SubMenuDescriptor.class, gwtSubMenuGui);
    guiProvider.register(MenuDescriptor.class, gwtMenuGui);
    guiProvider.register(MenuRadioItemDescriptor.class, gwtMenuItemGui);
    guiProvider.register(MenuCheckItemDescriptor.class, gwtMenuItemGui);
    guiProvider.register(MenuTitleItemDescriptor.class, gwtMenuTitleItemGui);
    guiProvider.register(MenuItemDescriptor.class, gwtMenuItemGui);
    guiProvider.register(MenuSeparatorDescriptor.class, gwtMenuSeparatorGui);
    guiProvider.register(PushButtonDescriptor.class, gwtPushButtonGui);
    guiProvider.register(ButtonDescriptor.class, gwtButtonGui);
    guiProvider.register(IconLabelDescriptor.class, gwtIconLabelGui);
    guiProvider.register(LabelDescriptor.class, gwtLabelGui);
    guiProvider.register(ToolbarDescriptor.class, gwtToolbarGui);
    guiProvider.register(ToolbarSeparatorDescriptor.class, gwtToolbarSeparatorGui);
  }

}
