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
package cc.kune.common.client;

import cc.kune.common.client.actions.gwtui.GwtButtonGui;
import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.gwtui.GwtMenuGui;
import cc.kune.common.client.actions.gwtui.GwtMenuItemGui;
import cc.kune.common.client.actions.gwtui.GwtMenuSeparatorGui;
import cc.kune.common.client.actions.gwtui.GwtPushButtonGui;
import cc.kune.common.client.actions.gwtui.GwtSubMenuGui;
import cc.kune.common.client.actions.gwtui.GwtToolbarGui;
import cc.kune.common.client.actions.gwtui.GwtToolbarSeparatorGui;

import com.google.gwt.inject.client.AbstractGinModule;

public class GwtGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(GwtSubMenuGui.class);
    bind(GwtMenuGui.class);
    bind(GwtMenuItemGui.class);
    bind(GwtMenuSeparatorGui.class);
    bind(GwtPushButtonGui.class);
    bind(GwtButtonGui.class);
    bind(GwtIconLabelGui.class);
    bind(GwtToolbarGui.class);
    bind(GwtToolbarSeparatorGui.class);
  }

}
