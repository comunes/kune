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
package cc.kune.common.client.notify;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressShowEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ProgressShowEvent extends GwtEvent<ProgressShowEvent.ProgressShowHandler> {

  /**
   * The Interface HasProgressShowHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasProgressShowHandlers extends HasHandlers {
    
    /**
     * Adds the progress show handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addProgressShowHandler(ProgressShowHandler handler);
  }

  /**
   * The Interface ProgressShowHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ProgressShowHandler extends EventHandler {
    
    /**
     * On progress show.
     *
     * @param event the event
     */
    public void onProgressShow(ProgressShowEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ProgressShowHandler> TYPE = new Type<ProgressShowHandler>();

  /**
   * Fire.
   *
   * @param source the source
   * @param message the message
   */
  public static void fire(final HasHandlers source, final java.lang.String message) {
    source.fireEvent(new ProgressShowEvent(message));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<ProgressShowHandler> getType() {
    return TYPE;
  }

  /** The message. */
  private final java.lang.String message;

  /**
   * Instantiates a new progress show event.
   */
  public ProgressShowEvent() {
    this("");
  }

  /**
   * Instantiates a new progress show event.
   *
   * @param message the message
   */
  public ProgressShowEvent(final java.lang.String message) {
    this.message = message;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final ProgressShowHandler handler) {
    handler.onProgressShow(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      final ProgressShowEvent o = (ProgressShowEvent) other;
      return true && ((o.message == null && this.message == null) || (o.message != null && o.message.equals(this.message)));
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ProgressShowHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public java.lang.String getMessage() {
    return message;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (message == null ? 1 : message.hashCode());
    return hashCode;
  }

  /* (non-Javadoc)
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ProgressShowEvent[" + message + "]";
  }

}
