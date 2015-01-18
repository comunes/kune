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
package cc.kune.bootstrap.client.actions.gwtui;

import cc.kune.bootstrap.client.smartmenus.SmartMenusBundle;
import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.gwtui.GwtMenuSeparatorGui;
import cc.kune.common.client.actions.gwtui.GwtMenuTitleItemGui;
import cc.kune.common.client.actions.gwtui.GwtSubMenuGui;
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

import com.google.gwt.core.client.ScriptInjector;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The Class BootstrapGuiProvider.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BootstrapGuiProvider {

  /**
   * Instantiates a new gwtbootstrap gui provider.
   */
  @Inject
  public BootstrapGuiProvider(final GuiProvider guiProvider,
      final Provider<GwtSubMenuGui> gwtSubMenuGui, final Provider<BSMenuGui> menuGui,
      final Provider<BSMenuItemGui> bsMenuItemGui,
      final Provider<GwtMenuSeparatorGui> gwtMenuSeparatorGui,
      final Provider<BSPushButtonGui> pushButtonGui, final Provider<BSButtonGui> buttonGui,
      final Provider<BSLabelGui> labelGui, final Provider<GwtIconLabelGui> gwtIconLabelGui,
      final Provider<BSToolbarGui> toolbarGui,
      final Provider<BSToolbarSeparatorGui> toolbarSeparatorGui,
      final Provider<GwtMenuTitleItemGui> gwtMenuTitleItemGui) {

    guiProvider.register(SubMenuDescriptor.class, gwtSubMenuGui);
    guiProvider.register(MenuDescriptor.class, menuGui);
    guiProvider.register(MenuRadioItemDescriptor.class, bsMenuItemGui);
    guiProvider.register(MenuCheckItemDescriptor.class, bsMenuItemGui);
    guiProvider.register(MenuTitleItemDescriptor.class, gwtMenuTitleItemGui);
    guiProvider.register(MenuItemDescriptor.class, bsMenuItemGui);
    guiProvider.register(MenuSeparatorDescriptor.class, gwtMenuSeparatorGui);
    guiProvider.register(PushButtonDescriptor.class, pushButtonGui);
    guiProvider.register(ButtonDescriptor.class, buttonGui);
    guiProvider.register(IconLabelDescriptor.class, gwtIconLabelGui);
    guiProvider.register(LabelDescriptor.class, labelGui);
    guiProvider.register(ToolbarDescriptor.class, toolbarGui);
    guiProvider.register(ToolbarSeparatorDescriptor.class, toolbarSeparatorGui);

    ScriptInjector.fromString(SmartMenusBundle.INSTANCE.smartmenus().getText()).setWindow(
        ScriptInjector.TOP_WINDOW).inject();

    ScriptInjector.fromString(SmartMenusBundle.INSTANCE.smartmenusBootstrap().getText()).setWindow(
        ScriptInjector.TOP_WINDOW).inject();

  }

}
