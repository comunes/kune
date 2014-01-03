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
 * The Class SpaceConfEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpaceConfEvent extends GwtEvent<SpaceConfEvent.SpaceConfHandler> {

  /**
   * The Interface HasSpaceConfHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasSpaceConfHandlers extends HasHandlers {

    /**
     * Adds the space conf handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addSpaceConfHandler(SpaceConfHandler handler);
  }

  /**
   * The Interface SpaceConfHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SpaceConfHandler extends EventHandler {

    /**
     * On space conf.
     * 
     * @param event
     *          the event
     */
    public void onSpaceConf(SpaceConfEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<SpaceConfHandler> TYPE = new Type<SpaceConfHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param space
   *          the space
   * @param token
   *          the token
   */
  public static void fire(final HasHandlers source,
      final cc.kune.core.client.sitebar.spaces.Space space, final String token) {
    source.fireEvent(new SpaceConfEvent(space, token));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<SpaceConfHandler> getType() {
    return TYPE;
  }

  /** The space. */
  private cc.kune.core.client.sitebar.spaces.Space space;

  /** The token. */
  private String token;

  /**
   * Instantiates a new space conf event.
   */
  protected SpaceConfEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new space conf event.
   * 
   * @param space
   *          the space
   * @param token
   *          the token
   */
  public SpaceConfEvent(final cc.kune.core.client.sitebar.spaces.Space space, final String token) {
    this.space = space;
    this.token = token;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final SpaceConfHandler handler) {
    handler.onSpaceConf(this);
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
    final SpaceConfEvent other = (SpaceConfEvent) obj;
    if (space == null) {
      if (other.space != null) {
        return false;
      }
    } else if (!space.equals(other.space)) {
      return false;
    }
    if (token == null) {
      if (other.token != null) {
        return false;
      }
    } else if (!token.equals(other.token)) {
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
  public Type<SpaceConfHandler> getAssociatedType() {
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

  /**
   * Gets the token.
   * 
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (space == null ? 1 : space.hashCode())
        + (token == null ? 1 : token.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "SpaceConfEvent[" + space + "/" + token + "]";
  }
}
