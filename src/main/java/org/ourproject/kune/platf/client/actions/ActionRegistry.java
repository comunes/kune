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

import java.util.HashMap;

import cc.kune.core.shared.domain.utils.AccessRights;

import com.allen_sauer.gwt.log.client.Log;

public class ActionRegistry<T> {
    private static final String GENERIC = "kgenacts";

    private final HashMap<String, ActionCollection<T>> actions;

    public ActionRegistry() {
        actions = new HashMap<String, ActionCollection<T>>();
    }

    public void addAction(final ActionDescriptor<T> action) {
        addAction(action, GENERIC);
    }

    public void addAction(final ActionDescriptor<T> action, final String... typeIds) {
        assert action != null;
        for (final String typeId : typeIds) {
            String text = action.getText();
            Log.debug("Registering action " + (text == null ? "with icon" : "'" + text + "'") + " for " + typeId);
            final ActionCollection<T> actionColl = getActions(typeId);
            actionColl.add(action);
        }
    }

    public ActionItemCollection<T> getCurrentActions(final T item, final boolean isLogged, final AccessRights rights,
            final boolean toolbarItems) {
        return getCurrentActions(item, GENERIC, isLogged, rights, toolbarItems);
    }

    public ActionItemCollection<T> getCurrentActions(final T item, final String typeId, final boolean isLogged,
            final AccessRights rights, final boolean toolbarItems) {
        final ActionItemCollection<T> collection = new ActionItemCollection<T>();

        for (final ActionDescriptor<T> action : getActions(typeId)) {
            if (mustAdd(isLogged, rights, action)) {
                if (toolbarItems) {
                    if (action instanceof ActionToolbarButtonDescriptor<?>
                            || action instanceof ActionToolbarMenuDescriptor<?>) {
                        collection.add(new ActionItem<T>(action, item));
                    }
                } else {
                    if (action instanceof ActionMenuItemDescriptor<?>
                            || action instanceof ActionToolbarMenuAndItemDescriptor<?>
                            || action instanceof ActionToolbarButtonAndItemDescriptor<?>) {
                        collection.add(new ActionItem<T>(action, item));
                    }
                }
            }
        }
        return collection;
    }

    public void removeAction(final ActionDescriptor<T> action) {
        removeAction(GENERIC, action);
    }

    public void removeAction(final String typeId, final ActionDescriptor<T> action) {
        actions.get(typeId).remove(action);
    }

    public int size() {
        return actions.size();
    }

    private ActionCollection<T> getActions(final String typeId) {
        ActionCollection<T> actionColl = actions.get(typeId);
        if (actionColl == null) {
            actionColl = new ActionCollection<T>();
            actions.put(typeId, actionColl);
        }
        return actionColl;
    }

    private boolean mustAdd(final boolean isLogged, final AccessRights rights, final ActionDescriptor<T> action) {
        if (action.mustBeAuthenticated()) {
            if (!isLogged) {
                return false;
            }
        }
        switch (action.getAccessRol()) {
        case Administrator:
            return rights.isAdministrable();
        case Editor:
            return rights.isEditable();
        case Viewer:
        default:
            return rights.isVisible();
        }
    }
}