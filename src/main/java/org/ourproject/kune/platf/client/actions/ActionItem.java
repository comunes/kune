/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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


import com.allen_sauer.gwt.log.client.Log;

/**
 * And action description and a item (for instance a StateToken, a XmmpURI) over
 * the action takes place
 * 
 * @param <T>
 */
public class ActionItem<T> {

    ActionDescriptor<T> action;
    T item;

    public ActionItem(final ActionDescriptor<T> action, final T item) {
        this.action = action;
        this.item = item;
    }

    public ActionDescriptor<T> getAction() {
        return action;
    }

    public T getItem() {
        return item;
    }

    public boolean mustBeAdded() {
        return action.mustBeAdded(getItem());
    }

    public boolean mustBeEnabled() {
        return action.mustBeEnabled(getItem());
    }

    public boolean mustBePressed() {
        if (action instanceof ActionToolbarPushButtonDescriptor<?>) {
            final ActionPressedCondition<T> mustIniPressed = ((ActionToolbarPushButtonDescriptor<T>) action).getMustInitialyPressed();
            return mustIniPressed == null ? false : mustIniPressed.mustBePressed(getItem());
        } else {
            Log.error("This action is not a push button");
            return false;
        }
    }
}
