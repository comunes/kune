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
package cc.kune.chat.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ToggleShowChatDialogEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToggleShowChatDialogEvent extends
    GwtEvent<ToggleShowChatDialogEvent.ToggleShowChatDialogHandler> {

  /**
   * The Interface HasToggleShowChatDialogHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasToggleShowChatDialogHandlers extends HasHandlers {
    
    /**
     * Adds the toggle show chat dialog handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addToggleShowChatDialogHandler(ToggleShowChatDialogHandler handler);
  }

  /**
   * The Interface ToggleShowChatDialogHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ToggleShowChatDialogHandler extends EventHandler {
    
    /**
     * On toggle show chat dialog.
     *
     * @param event the event
     */
    public void onToggleShowChatDialog(ToggleShowChatDialogEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ToggleShowChatDialogHandler> TYPE = new Type<ToggleShowChatDialogHandler>();

  /**
   * Fire.
   *
   * @param source the source
   * @param show the show
   */
  public static void fire(final HasHandlers source, final boolean show) {
    source.fireEvent(new ToggleShowChatDialogEvent());
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<ToggleShowChatDialogHandler> getType() {
    return TYPE;
  }

  /**
   * Instantiates a new toggle show chat dialog event.
   */
  protected ToggleShowChatDialogEvent() {
    // Possibly for serialization.
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final ToggleShowChatDialogHandler handler) {
    handler.onToggleShowChatDialog(this);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ToggleShowChatDialogHandler> getAssociatedType() {
    return TYPE;
  }

  /* (non-Javadoc)
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ToggleShowChatDialogEvent";
  }
}
