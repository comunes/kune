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
 */
package org.ourproject.kune.platf.client.shortcuts;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ChangebleObject;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.Shortcut;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class GlobalShortcutRegister extends InputMap {

    private final NativePreviewHandler eventHandler;
    private HandlerRegistration handlerReg;

    public GlobalShortcutRegister() {
        super();
        eventHandler = new NativePreviewHandler() {
            public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPrev) {
                if (nativeEventPrev.getTypeInt() == Event.ONKEYDOWN) {
                    final NativeEvent nativeEvent = nativeEventPrev.getNativeEvent();
                    final Event event = Event.as(nativeEvent);
                    final AbstractAction action = get(Shortcut.getShortcut(event.getCtrlKey(), event.getAltKey(),
                            event.getShiftKey(), event.getShiftKey(), (char) event.getKeyCode()));
                    if (action != null) {
                        Log.debug("Not propagating event");
                        nativeEvent.stopPropagation();
                        action.actionPerformed(new ActionEvent(nativeEvent.getCurrentEventTarget(), event));
                        nativeEventPrev.cancel();
                    }
                }
            }
        };
    }

    public void disable() {
        if (handlerReg != null) {
            handlerReg.removeHandler();
        }
    }

    public void enable() {
        handlerReg = Event.addNativePreviewHandler(eventHandler);
    }

    @Override
    public void put(final KeyStroke keystroke, final AbstractAction action) {
        final ChangebleObject previous = super.get(keystroke);
        if (previous != null) {
            Log.warn("Shortcut already registed by: " + previous + "(overriding)");
        }
        super.put(keystroke, action);
    }
}
