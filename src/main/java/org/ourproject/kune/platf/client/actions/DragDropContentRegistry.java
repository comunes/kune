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
package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

public class DragDropContentRegistry {

    private final ArrayList<String> draggables;
    private final ArrayList<String> droppables;

    public DragDropContentRegistry() {
        draggables = new ArrayList<String>();
        droppables = new ArrayList<String>();
    }

    public boolean isDraggable(final String typeId, final boolean administrable) {
        return administrable && draggables.contains(typeId);
    }

    public boolean isDroppable(final String typeId, final boolean administrable) {
        return administrable && droppables.contains(typeId);
    }

    public void registerDraggableType(final String type) {
        draggables.add(type);
    }

    public void registerDroppableType(final String type) {
        droppables.add(type);
    }

}
