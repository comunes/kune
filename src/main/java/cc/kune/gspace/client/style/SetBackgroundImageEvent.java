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
 * The Class SetBackgroundImageEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SetBackgroundImageEvent extends GwtEvent<SetBackgroundImageEvent.SetBackgroundImageHandler> {

  /**
   * The Interface HasSetBackgroundImageHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasSetBackgroundImageHandlers extends HasHandlers {

    /**
     * Adds the set back image handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addSetBackImageHandler(SetBackgroundImageHandler handler);
  }

  /**
   * The Interface SetBackgroundImageHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SetBackgroundImageHandler extends EventHandler {

    /**
     * On set back image.
     * 
     * @param event
     *          the event
     */
    public void onSetBackImage(SetBackgroundImageEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<SetBackgroundImageHandler> TYPE = new Type<SetBackgroundImageHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param token
   *          the token
   */
  public static void fire(final HasHandlers source,
      final cc.kune.core.shared.domain.utils.StateToken token) {
    source.fireEvent(new SetBackgroundImageEvent(token));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<SetBackgroundImageHandler> getType() {
    return TYPE;
  }

  /** The token. */
  cc.kune.core.shared.domain.utils.StateToken token;

  /**
   * Instantiates a new sets the background image event.
   */
  protected SetBackgroundImageEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new sets the background image event.
   * 
   * @param token
   *          the token
   */
  public SetBackgroundImageEvent(final cc.kune.core.shared.domain.utils.StateToken token) {
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
  protected void dispatch(final SetBackgroundImageHandler handler) {
    handler.onSetBackImage(this);
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
    final SetBackgroundImageEvent other = (SetBackgroundImageEvent) obj;
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
  public Type<SetBackgroundImageHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the token.
   * 
   * @return the token
   */
  public cc.kune.core.shared.domain.utils.StateToken getToken() {
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
    hashCode = (hashCode * 37) + (token == null ? 1 : token.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "SetBackImageEvent[" + token + "]";
  }
}
