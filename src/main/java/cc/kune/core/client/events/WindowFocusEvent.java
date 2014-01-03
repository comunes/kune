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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowFocusEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WindowFocusEvent extends GwtEvent<WindowFocusEvent.WindowFocusHandler> {

  /**
   * The Interface HasWindowFocusHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasWindowFocusHandlers extends HasHandlers {

    /**
     * Adds the window focus handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addWindowFocusHandler(WindowFocusHandler handler);
  }

  /**
   * The Interface WindowFocusHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface WindowFocusHandler extends EventHandler {

    /**
     * On window focus.
     * 
     * @param event
     *          the event
     */
    public void onWindowFocus(WindowFocusEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<WindowFocusHandler> TYPE = new Type<WindowFocusHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param hasFocus
   *          the has focus
   */
  public static void fire(final HasHandlers source, final boolean hasFocus) {
    source.fireEvent(new WindowFocusEvent(hasFocus));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<WindowFocusHandler> getType() {
    return TYPE;
  }

  /** The has focus. */
  boolean hasFocus;

  /**
   * Instantiates a new window focus event.
   */
  protected WindowFocusEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new window focus event.
   * 
   * @param hasFocus
   *          the has focus
   */
  public WindowFocusEvent(final boolean hasFocus) {
    this.hasFocus = hasFocus;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final WindowFocusHandler handler) {
    handler.onWindowFocus(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final WindowFocusEvent other = (WindowFocusEvent) obj;
    if (hasFocus != other.hasFocus) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<WindowFocusHandler> getAssociatedType() {
    return TYPE;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Boolean.valueOf(hasFocus).hashCode();
    return hashCode;
  }

  /**
   * Checks if is checks for focus.
   * 
   * @return true, if is checks for focus
   */
  public boolean isHasFocus() {
    return hasFocus;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "WindowFocusEvent[" + hasFocus + "]";
  }
}
