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
package cc.kune.gspace.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentEntityChangedEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CurrentEntityChangedEvent extends
    GwtEvent<CurrentEntityChangedEvent.CurrentEntityChangedHandler> {

  /**
   * The Interface CurrentEntityChangedHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface CurrentEntityChangedHandler extends EventHandler {

    /**
     * On current logo changed.
     * 
     * @param event
     *          the event
     */
    public void onCurrentLogoChanged(CurrentEntityChangedEvent event);
  }

  /**
   * The Interface HasCurrentLogoChangedHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasCurrentLogoChangedHandlers extends HasHandlers {

    /**
     * Adds the current logo changed handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addCurrentLogoChangedHandler(CurrentEntityChangedHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<CurrentEntityChangedHandler> TYPE = new Type<CurrentEntityChangedHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  public static void fire(final HasHandlers source, final String shortName, final String longName) {
    source.fireEvent(new CurrentEntityChangedEvent(shortName, longName));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<CurrentEntityChangedHandler> getType() {
    return TYPE;
  }

  /** The long name. */
  private final String longName;

  /** The short name. */
  private final String shortName;

  /**
   * Instantiates a new current entity changed event.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  public CurrentEntityChangedEvent(final String shortName, final String longName) {
    this.shortName = shortName;
    this.longName = longName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final CurrentEntityChangedHandler handler) {
    handler.onCurrentLogoChanged(this);
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
  public Type<CurrentEntityChangedHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the long name.
   * 
   * @return the long name
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return shortName;
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
    return "CurrentEntityChangedEvent [shortName=" + shortName + ", longName=" + longName + "]";
  }
}
