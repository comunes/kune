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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public interface ContextItemsView extends View {
    void clear();

    void addItem(String name, String type, String event, boolean editable);

    void selectItem(int index);

    void setControlsVisible(boolean isVisible);

    void setCurrentName(String name);

    void setParentButtonEnabled(boolean isEnabled);

    void setParentTreeVisible(boolean b);

    void registerType(String typeName, AbstractImagePrototype image);

    void addCommand(String typeName, String label, String eventName);

    void showCreationField(String typeName);

    void setAbsolutePath(ContainerSimpleDTO[] absolutePath);

}
