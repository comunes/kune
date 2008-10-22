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
package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter<T> implements ActionToolbar<T> {
    private final ActionToolbarView<T> view;

    public ActionToolbarPresenter(final ActionToolbarView<T> toolbar) {
        this.view = toolbar;
    }

    public void attach() {
        view.attach();
    }

    public void clear() {
        view.clear();
    }

    public void detach() {
        view.detach();
    }

    public void disableMenusAndClearButtons() {
        view.clear();
    }

    public void setActions(final ActionItemCollection<T> actions) {
        for (final ActionItem<T> actionItem : actions) {
            final ActionDescriptor<T> action = actionItem.getAction();
            if (isToolbarMenu(action)) {
                view.addMenuAction(actionItem, actionItem.checkEnabling());
            } else {
                if (isToolbarButton(action)) {
                    view.addButtonAction(actionItem, actionItem.checkEnabling());
                } else {
                    Log.error("Code error: Not an ActionMenuDescriptor or ActionButtonDescriptor: " + action.getText());
                }
            }
        }
    }

    public void setEnableAction(final ActionToolbarDescriptor<T> action, final boolean enable) {
        if (isToolbarMenu(action)) {
            view.setMenuEnable(action, enable);
        } else {
            if (isToolbarButton(action)) {
                view.setButtonEnable(action, enable);
            }
        }

    }

    private boolean isToolbarButton(final ActionDescriptor<T> action) {
        return action instanceof ActionToolbarButtonDescriptor
                || action instanceof ActionToolbarButtonAndItemDescriptor;
    }

    private boolean isToolbarMenu(final ActionDescriptor<T> action) {
        return action instanceof ActionToolbarMenuDescriptor || action instanceof ActionToolbarMenuAndItemDescriptor;
    }
}
