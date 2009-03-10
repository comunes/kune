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
package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ActionToolbarPushButtonDescriptor;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter<T> implements ActionToolbar<T> {
    private final ActionToolbarView<T> view;

    public ActionToolbarPresenter(final ActionToolbarView<T> toolbar) {
        this.view = toolbar;
    }

    public void addActions(final ActionItemCollection<T> actions, ActionToolbarPosition position) {
        for (final ActionItem<T> actionItem : actions) {
            final ActionDescriptor<T> action = actionItem.getAction();
            if (actionItem.mustBeAdded()) {
                if (isToolbarMenu(action)) {
                    if (addInPosition(action, position)) {
                        view.addMenuAction(actionItem, actionItem.mustBeEnabled());
                    }
                } else {
                    if (isToolbarPushButton(action)) {
                        if (addInPosition(action, position)) {
                            view.addButtonAction(actionItem, actionItem.mustBeEnabled(), true,
                                    actionItem.mustBePressed());
                        }
                    } else if (isToolbarButton(action)) {
                        if (addInPosition(action, position)) {
                            view.addButtonAction(actionItem, actionItem.mustBeEnabled(), false, false);
                        }
                    } else {
                        Log.error("Code error: Not an ActionMenuDescriptor or ActionButtonDescriptor: "
                                + action.getText());
                    }
                }
            }
        }
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

    public int getLeftPosition(ActionDescriptor<T> action) {
        return view.getLeftPosition(action);
    }

    public int getTopPosition(ActionDescriptor<T> action) {
        return view.getTopPosition(action);
    }

    public ActionToolbarView<T> getView() {
        return view;
    }

    public void hideAllMenus() {
        view.hideAllMenus();
    }

    public void setButtonEnable(ActionDescriptor<T> action, boolean enable) {
        view.setButtonEnable(action, enable);
    }

    public void setCleanStyle() {
        view.setCleanStyle();
    }

    public void setNormalStyle() {
        view.setNormalStyle();
    }

    public void setParentMenuTitle(ActionToolbarMenuDescriptor<T> action, String origTitle, String origTooltip,
            String newTitle) {
        view.setParentMenuTitle(action, origTitle, origTooltip, newTitle);
    }

    public void setPushButtonPressed(ActionDescriptor<T> action, boolean pressed) {
        view.setPushButtonPressed(action, pressed);
    }

    private boolean addInPosition(ActionDescriptor<T> action, ActionToolbarPosition position) {
        if (position.equals(IN_ANY) || ((ActionToolbarDescriptor<T>) action).getActionPosition().equals(position)) {
            return true;
        }
        return false;
    }

    private boolean isToolbarButton(final ActionDescriptor<T> action) {
        return action instanceof ActionToolbarButtonDescriptor
                || action instanceof ActionToolbarButtonAndItemDescriptor;
    }

    private boolean isToolbarMenu(final ActionDescriptor<T> action) {
        return action instanceof ActionToolbarMenuDescriptor || action instanceof ActionToolbarMenuAndItemDescriptor;
    }

    private boolean isToolbarPushButton(ActionDescriptor<T> action) {
        return action instanceof ActionToolbarPushButtonDescriptor;
    }
}
