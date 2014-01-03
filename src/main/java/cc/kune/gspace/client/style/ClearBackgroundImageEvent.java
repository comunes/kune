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
package cc.kune.gspace.client.style;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ClearBackgroundImageEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ClearBackgroundImageEvent extends
    GwtEvent<ClearBackgroundImageEvent.ClearBackgroundImageHandler> {

  /**
   * The Interface ClearBackgroundImageHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ClearBackgroundImageHandler extends EventHandler {

    /**
     * On clear back image.
     * 
     * @param event
     *          the event
     */
    public void onClearBackImage(ClearBackgroundImageEvent event);
  }

  /**
   * The Interface HasClearBackgroundImageHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasClearBackgroundImageHandlers extends HasHandlers {

    /**
     * Adds the clear back image handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addClearBackImageHandler(ClearBackgroundImageHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<ClearBackgroundImageHandler> TYPE = new Type<ClearBackgroundImageHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   */
  public static void fire(final HasHandlers source) {
    source.fireEvent(new ClearBackgroundImageEvent());
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<ClearBackgroundImageHandler> getType() {
    return TYPE;
  }

  /**
   * Instantiates a new clear background image event.
   */
  public ClearBackgroundImageEvent() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final ClearBackgroundImageHandler handler) {
    handler.onClearBackImage(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ClearBackgroundImageHandler> getAssociatedType() {
    return TYPE;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ClearBackgroundImageEvent[]";
  }
}
