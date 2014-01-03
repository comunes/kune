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
 * The Class ProgressHideEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ProgressHideEvent extends GwtEvent<ProgressHideEvent.ProgressHideHandler> {

  /**
   * The Interface HasProgressHideHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasProgressHideHandlers extends HasHandlers {
    
    /**
     * Adds the progress hide handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addProgressHideHandler(ProgressHideHandler handler);
  }

  /**
   * The Interface ProgressHideHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ProgressHideHandler extends EventHandler {
    
    /**
     * On progress hide.
     *
     * @param event the event
     */
    public void onProgressHide(ProgressHideEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ProgressHideHandler> TYPE = new Type<ProgressHideHandler>();

  /**
   * Fire.
   *
   * @param source the source
   */
  public static void fire(final HasHandlers source) {
    source.fireEvent(new ProgressHideEvent());
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<ProgressHideHandler> getType() {
    return TYPE;
  }

  /**
   * Instantiates a new progress hide event.
   */
  public ProgressHideEvent() {
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final ProgressHideHandler handler) {
    handler.onProgressHide(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ProgressHideHandler> getAssociatedType() {
    return TYPE;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  /* (non-Javadoc)
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ProgressHideEvent[" + "]";
  }

}
