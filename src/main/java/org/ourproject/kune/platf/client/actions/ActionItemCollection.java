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
 \*/
package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.allen_sauer.gwt.log.client.Log;

public class ActionItemCollection<T> extends ArrayList<ActionItem<T>> {

    private static final long serialVersionUID = 1127359948648860754L;

    @Override
    public boolean add(final ActionItem<T> actionItem) {
        ActionDescriptor<T> action = actionItem.getAction();
        int position = action.getPosition();
        if (position == ActionDescriptor.NO_POSITION) {
            super.add(actionItem);
        } else {
            try {
                super.add(position, actionItem);
                return true;
            } catch (IndexOutOfBoundsException e) {
                Log.error("Trying to add an action in a position out of bounds");
                super.add(actionItem);
            }
        }
        return false;
    }

    @Override
    public boolean addAll(final Collection<? extends ActionItem<T>> actionItems) {
        for (ActionItem<T> actionItem : actionItems) {
            add(actionItem);
        }
        return true;
    }
}
