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
 * The Class RenameContentEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RenameContentEvent extends GwtEvent<RenameContentEvent.RenameContentHandler> {

  /**
   * The Interface HasRenameEventHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasRenameEventHandlers extends HasHandlers {

    /**
     * Adds the rename event handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addRenameEventHandler(RenameContentHandler handler);
  }

  /**
   * The Interface RenameContentHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface RenameContentHandler extends EventHandler {

    /**
     * On rename event.
     * 
     * @param event
     *          the event
     */
    public void onRenameEvent(RenameContentEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<RenameContentHandler> TYPE = new Type<RenameContentHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param token
   *          the token
   * @param oldName
   *          the old name
   * @param newName
   *          the new name
   */
  public static void fire(final HasHandlers source,
      final cc.kune.core.shared.domain.utils.StateToken token, final java.lang.String oldName,
      final java.lang.String newName) {
    source.fireEvent(new RenameContentEvent(token, oldName, newName));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<RenameContentHandler> getType() {
    return TYPE;
  }

  /** The new name. */
  java.lang.String newName;

  /** The old name. */
  java.lang.String oldName;

  /** The token. */
  cc.kune.core.shared.domain.utils.StateToken token;

  /**
   * Instantiates a new rename content event.
   */
  protected RenameContentEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new rename content event.
   * 
   * @param token
   *          the token
   * @param oldName
   *          the old name
   * @param newName
   *          the new name
   */
  public RenameContentEvent(final cc.kune.core.shared.domain.utils.StateToken token,
      final java.lang.String oldName, final java.lang.String newName) {
    this.token = token;
    this.oldName = oldName;
    this.newName = newName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final RenameContentHandler handler) {
    handler.onRenameEvent(this);
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
    final RenameContentEvent other = (RenameContentEvent) obj;
    if (token == null) {
      if (other.token != null) {
        return false;
      }
    } else if (!token.equals(other.token)) {
      return false;
    }
    if (oldName == null) {
      if (other.oldName != null) {
        return false;
      }
    } else if (!oldName.equals(other.oldName)) {
      return false;
    }
    if (newName == null) {
      if (other.newName != null) {
        return false;
      }
    } else if (!newName.equals(other.newName)) {
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
  public Type<RenameContentHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the new name.
   * 
   * @return the new name
   */
  public java.lang.String getNewName() {
    return newName;
  }

  /**
   * Gets the old name.
   * 
   * @return the old name
   */
  public java.lang.String getOldName() {
    return oldName;
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
    hashCode = (hashCode * 37) + (oldName == null ? 1 : oldName.hashCode());
    hashCode = (hashCode * 37) + (newName == null ? 1 : newName.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "RenameEventEvent[" + token + "," + oldName + "," + newName + "]";
  }
}
