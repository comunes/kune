/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.common.client.shortcuts;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ChangeableObject;
import cc.kune.common.client.actions.InputMap;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.log.Log;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalShortcutRegisterDefault.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GlobalShortcutRegisterDefault extends InputMap implements GlobalShortcutRegister {

  /** The event handler. */
  private final NativePreviewHandler eventHandler;
  
  /** The handler reg. */
  private HandlerRegistration handlerReg;

  /**
   * Instantiates a new global shortcut register default.
   */
  public GlobalShortcutRegisterDefault() {
    super();
    eventHandler = new NativePreviewHandler() {
      @Override
      public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPrev) {
        if (nativeEventPrev.getTypeInt() == Event.ONKEYDOWN) {
          final NativeEvent nativeEvent = nativeEventPrev.getNativeEvent();
          final Event event = Event.as(nativeEvent);
          AbstractAction action = get(KeyStroke.getKeyStroke(event.getKeyCode(), 0));
          if (action == null) {
            action = get(Shortcut.getShortcut(event.getCtrlKey(), event.getAltKey(),
                event.getShiftKey(), event.getShiftKey(), (char) event.getKeyCode()));
          }
          if (action != null) {
            Log.debug("Not propagating event for action: " + action.getValue(Action.NAME));
            nativeEvent.stopPropagation();
            action.actionPerformed(new ActionEvent(nativeEvent.getCurrentEventTarget(), event));
            nativeEventPrev.cancel();
          }
        }
      }
    };
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.shortcuts.GlobalShortcutRegister#disable()
   */
  @Override
  public void disable() {
    if (handlerReg != null) {
      handlerReg.removeHandler();
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.shortcuts.GlobalShortcutRegister#enable()
   */
  @Override
  public void enable() {
    enableImpl();
  }

  /**
   * Enable impl.
   */
  private void enableImpl() {
    handlerReg = Event.addNativePreviewHandler(eventHandler);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.InputMap#put(cc.kune.common.client.actions.KeyStroke, cc.kune.common.client.actions.AbstractAction)
   */
  @Override
  public void put(final KeyStroke keystroke, final AbstractAction action) {
    final ChangeableObject previous = super.get(keystroke);
    if (previous != null) {
      Log.info("Shortcut already registed by: " + previous + "(overriding)");
    }
    super.put(keystroke, action);
  }
}
