/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.actions.AttachToExtensionPointAction;
import org.ourproject.kune.platf.client.actions.ClearExtensionPointAction;
import org.ourproject.kune.platf.client.actions.DetachFromExtensionPointAction;
import org.ourproject.kune.platf.client.actions.GotoAction;
import org.ourproject.kune.platf.client.actions.GotoContainerAction;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class PlatformClientModule implements ClientModule {
    public void configure(final Register register) {
        register.addAction(PlatformEvents.ATTACH_TO_EXT_POINT, new AttachToExtensionPointAction());
        register.addAction(PlatformEvents.DETACH_FROM_EXT_POINT, new DetachFromExtensionPointAction());
        register.addAction(PlatformEvents.CLEAR_EXT_POINT, new ClearExtensionPointAction());
        register.addAction(PlatformEvents.GOTO, new GotoAction());
        register.addAction(PlatformEvents.GOTO_CONTAINER, new GotoContainerAction());
    }
}
