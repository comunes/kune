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
package cc.kune.gxtbinds.client;

import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtButtonGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtMenuGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtMenuItemGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtMenuSeparatorGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtPushButtonGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtSubMenuGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtToolbarGui;
import cc.kune.gxtbinds.client.actions.gxtui.GxtToolbarSeparatorGui;

import com.google.gwt.inject.client.AbstractGinModule;

// TODO: Auto-generated Javadoc
/**
 * The Class GxtGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GxtGinModule extends AbstractGinModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    bind(GxtSubMenuGui.class);
    bind(GxtMenuGui.class);
    bind(GxtMenuItemGui.class);
    bind(GxtMenuSeparatorGui.class);
    bind(GxtPushButtonGui.class);
    bind(GxtButtonGui.class);
    bind(GwtIconLabelGui.class);
    bind(GxtToolbarGui.class);
    bind(GxtToolbarSeparatorGui.class);
  }

}
