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
 * The Class AccessRightsChangedEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AccessRightsChangedEvent extends
    GwtEvent<AccessRightsChangedEvent.AccessRightsChangedHandler> {

  /**
   * The Interface AccessRightsChangedHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface AccessRightsChangedHandler extends EventHandler {

    /**
     * On access rights changed.
     * 
     * @param event
     *          the event
     */
    public void onAccessRightsChanged(AccessRightsChangedEvent event);
  }

  /**
   * The Interface HasAccessRightsChangedHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasAccessRightsChangedHandlers extends HasHandlers {

    /**
     * Adds the access rights changed handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addAccessRightsChangedHandler(AccessRightsChangedHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<AccessRightsChangedHandler> TYPE = new Type<AccessRightsChangedHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param previousRights
   *          the previous rights
   * @param currentRights
   *          the current rights
   */
  public static void fire(final HasHandlers source,
      final cc.kune.core.shared.domain.utils.AccessRights previousRights,
      final cc.kune.core.shared.domain.utils.AccessRights currentRights) {
    source.fireEvent(new AccessRightsChangedEvent(previousRights, currentRights));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<AccessRightsChangedHandler> getType() {
    return TYPE;
  }

  /** The current rights. */
  cc.kune.core.shared.domain.utils.AccessRights currentRights;

  /** The previous rights. */
  cc.kune.core.shared.domain.utils.AccessRights previousRights;

  /**
   * Instantiates a new access rights changed event.
   */
  protected AccessRightsChangedEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new access rights changed event.
   * 
   * @param previousRights
   *          the previous rights
   * @param currentRights
   *          the current rights
   */
  public AccessRightsChangedEvent(final cc.kune.core.shared.domain.utils.AccessRights previousRights,
      final cc.kune.core.shared.domain.utils.AccessRights currentRights) {
    this.previousRights = previousRights;
    this.currentRights = currentRights;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final AccessRightsChangedHandler handler) {
    handler.onAccessRightsChanged(this);
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
    final AccessRightsChangedEvent other = (AccessRightsChangedEvent) obj;
    if (previousRights == null) {
      if (other.previousRights != null) {
        return false;
      }
    } else if (!previousRights.equals(other.previousRights)) {
      return false;
    }
    if (currentRights == null) {
      if (other.currentRights != null) {
        return false;
      }
    } else if (!currentRights.equals(other.currentRights)) {
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
  public Type<AccessRightsChangedHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the current rights.
   * 
   * @return the current rights
   */
  public cc.kune.core.shared.domain.utils.AccessRights getCurrentRights() {
    return currentRights;
  }

  /**
   * Gets the previous rights.
   * 
   * @return the previous rights
   */
  public cc.kune.core.shared.domain.utils.AccessRights getPreviousRights() {
    return previousRights;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (previousRights == null ? 1 : previousRights.hashCode());
    hashCode = (hashCode * 37) + (currentRights == null ? 1 : currentRights.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "AccessRightsChangedEvent[" + previousRights + "," + currentRights + "]";
  }
}
