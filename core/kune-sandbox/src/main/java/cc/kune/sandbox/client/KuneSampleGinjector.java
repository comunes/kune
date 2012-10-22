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

package cc.kune.sandbox.client;

import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.notify.UserNotifierPopup;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.HasRTL;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;


@GinModules(KuneSampleGinModule.class)
public interface KuneSampleGinjector extends Ginjector {
  GuiProvider getGuiProvider();
  Toolbar getToolbar();
  UserNotifierPopup getUserNotifierPopup();
  GlobalShortcutRegister getGlobalShortcutRegister();
  HasRTL getHasRTL();
}
