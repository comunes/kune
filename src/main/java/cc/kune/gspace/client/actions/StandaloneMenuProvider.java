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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

import com.google.inject.Provider;

/**
 * The Class StandAloneMenuProvider is a way to create a kind of singletons for
 * menu entries. This can be register in a list of Providers<GuiDescriptor> but
 * in fact only references one item and can be used to select the parent of some
 * menu items
 */
public abstract class StandaloneMenuProvider implements Provider<MenuDescriptor> {

  private final AbstractStandaloneMenu menu;

  public StandaloneMenuProvider(final AbstractStandaloneMenu menu) {
    this.menu = menu;
  }

  @Override
  public MenuDescriptor get() {
    return menu;
  }

}
