/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.shortcuts.GlobalShortcutRegisterDefault;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class SampleGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
    bind(GxtGuiProvider.class).in(Singleton.class);
    bind(GwtGuiProvider.class).in(Singleton.class);
    bind(GlobalShortcutRegister.class).to(GlobalShortcutRegisterDefault.class).in(Singleton.class);
  }
}
