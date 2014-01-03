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
package cc.kune.core.client.sitebar.spaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class SpaceSelectEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpaceSelectEvent extends GwtEvent<SpaceSelectEvent.SpaceSelectHandler> {

  /**
   * The Interface HasSpaceSelectHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasSpaceSelectHandlers extends HasHandlers {

    /**
     * Adds the space select handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addSpaceSelectHandler(SpaceSelectHandler handler);
  }

  /**
   * The Interface SpaceSelectHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SpaceSelectHandler extends EventHandler {

    /**
     * On space select.
     * 
     * @param event
     *          the event
     */
    public void onSpaceSelect(SpaceSelectEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<SpaceSelectHandler> TYPE = new Type<SpaceSelectHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param space
   *          the space
   */
  public static void fire(final HasHandlers source, final cc.kune.core.client.sitebar.spaces.Space space) {
    source.fireEvent(new SpaceSelectEvent(space));
  }

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param space
   *          the space
   * @param restoreToken
   *          the restore token
   */
  public static void fire(final HasHandlers source,
      final cc.kune.core.client.sitebar.spaces.Space space, final boolean restoreToken) {
    source.fireEvent(new SpaceSelectEvent(space, restoreToken));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<SpaceSelectHandler> getType() {
    return TYPE;
  }

  /** The restore token. */
  private boolean restoreToken;

  /** The space. */
  private cc.kune.core.client.sitebar.spaces.Space space;

  /**
   * Instantiates a new space select event.
   */
  protected SpaceSelectEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new space select event.
   * 
   * @param space
   *          the space
   */
  public SpaceSelectEvent(final cc.kune.core.client.sitebar.spaces.Space space) {
    this(space, false);
  }

  /**
   * Instantiates a new space select event.
   * 
   * @param space
   *          the space
   * @param restoreToken
   *          the restore token
   */
  public SpaceSelectEvent(final cc.kune.core.client.sitebar.spaces.Space space,
      final boolean restoreToken) {
    this.space = space;
    this.setRestoreToken(restoreToken);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final SpaceSelectHandler handler) {
    handler.onSpaceSelect(this);
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
    final SpaceSelectEvent other = (SpaceSelectEvent) obj;
    if (space == null) {
      if (other.space != null) {
        return false;
      }
    } else if (!space.equals(other.space)) {
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
  public Type<SpaceSelectHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the space.
   * 
   * @return the space
   */
  public cc.kune.core.client.sitebar.spaces.Space getSpace() {
    return space;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (space == null ? 1 : space.hashCode());
    return hashCode;
  }

  /**
   * Sets the restore token.
   * 
   * @param restoreToken
   *          the new restore token
   */
  public void setRestoreToken(final boolean restoreToken) {
    this.restoreToken = restoreToken;
  }

  /**
   * Should restore token.
   * 
   * @return true, if successful
   */
  public boolean shouldRestoreToken() {
    return restoreToken;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "SpaceSelectEvent[" + space + "]";
  }
}
