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
 * The Class StackErrorEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StackErrorEvent extends GwtEvent<StackErrorEvent.StackErrorHandler> {

  /**
   * The Interface HasStackErrorHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasStackErrorHandlers extends HasHandlers {

    /**
     * Adds the stack error handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addStackErrorHandler(StackErrorHandler handler);
  }

  /**
   * The Interface StackErrorHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface StackErrorHandler extends EventHandler {

    /**
     * On stack error.
     * 
     * @param event
     *          the event
     */
    public void onStackError(StackErrorEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<StackErrorHandler> TYPE = new Type<StackErrorHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param exception
   *          the exception
   */
  public static void fire(final HasHandlers source, final java.lang.Throwable exception) {
    source.fireEvent(new StackErrorEvent(exception));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<StackErrorHandler> getType() {
    return TYPE;
  }

  /** The exception. */
  java.lang.Throwable exception;

  /**
   * Instantiates a new stack error event.
   */
  protected StackErrorEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new stack error event.
   * 
   * @param exception
   *          the exception
   */
  public StackErrorEvent(final java.lang.Throwable exception) {
    this.exception = exception;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final StackErrorHandler handler) {
    handler.onStackError(this);
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
    final StackErrorEvent other = (StackErrorEvent) obj;
    if (exception == null) {
      if (other.exception != null) {
        return false;
      }
    } else if (!exception.equals(other.exception)) {
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
  public Type<StackErrorHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the exception.
   * 
   * @return the exception
   */
  public java.lang.Throwable getException() {
    return exception;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (exception == null ? 1 : exception.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "StackErrorEvent[" + exception + "]";
  }
}
