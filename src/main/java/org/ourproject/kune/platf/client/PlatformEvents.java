/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client;

public interface PlatformEvents {
    @Deprecated
    public static final String ATTACH_TO_EXT_POINT = "ws.AttachToExtensionPoint";
    public static final String ATTACH_TO_EXTENSIBLE_WIDGET = "ws.AttachToExtensibleWidget";
    public static final String DETACH_FROM_EXTENSIBLE_WIDGET = "ws.DetachToExtensibleWidget";
    public static final String CLEAR_EXTENSIBLE_WIDGET = "ws.ClearExtensionPoint";
    public static final String GOTO = "ws.Goto";
    public static final String GOTO_CONTAINER = "ws.GotoContainer";
}
