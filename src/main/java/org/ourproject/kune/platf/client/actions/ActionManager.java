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

import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener0;

public class ActionManager {

    public ActionManager() {
    }

    public void doAction(final ActionItem<?> actionItem) {
        final ActionDescriptor<?> action = actionItem.getAction();
        final Object item = actionItem.getItem();
        if (action.mustBeConfirmed()) {
            NotifyUser.askConfirmation(action.getConfirmationTitle(), action.getConfirmationText(), new Listener0() {
                public void onEvent() {
                    fire(action, item);
                }
            }, new Listener0() {
                public void onEvent() {
                    action.fireOnNotConfirmed(item);
                }
            });
        } else {
            fire(action, item);
        }
    }

    private void fire(final ActionDescriptor<?> action, final Object parameter) {
        action.fireOnPerformCall(parameter);
    }

}
