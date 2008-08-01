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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.signal.Slot;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public interface ContextItems {

    public void canCreate(String typeName, String label, Slot<?> slot);

    public void canCreate(String typeName, String label, String eventName);

    public View getView();

    public void registerType(String typeName, AbstractImagePrototype pageWhite);

    public void setControlsVisible(boolean visible);

    public void setParentTreeVisible(boolean visible);

    public void showContainer(StateToken state, ContainerDTO container, AccessRightsDTO rights);

}
