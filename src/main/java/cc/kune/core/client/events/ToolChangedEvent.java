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

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolChangedEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolChangedEvent extends GwtEvent<ToolChangedEvent.ToolChangedHandler> {

  /**
   * The Interface HasToolChangedHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasToolChangedHandlers extends HasHandlers {

    /**
     * Adds the tool changed handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addToolChangedHandler(ToolChangedHandler handler);
  }

  /**
   * The Interface ToolChangedHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ToolChangedHandler extends EventHandler {

    /**
     * On tool changed.
     * 
     * @param event
     *          the event
     */
    public void onToolChanged(ToolChangedEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ToolChangedHandler> TYPE = new Type<ToolChangedHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param previousToken
   *          the previous token
   * @param newToken
   *          the new token
   */
  public static void fire(final HasHandlers source, final StateToken previousToken,
      final StateToken newToken) {
    source.fireEvent(new ToolChangedEvent(previousToken, newToken));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<ToolChangedHandler> getType() {
    return TYPE;
  }

  /** The new token. */
  private StateToken newToken;

  /** The previous token. */
  private StateToken previousToken;

  /**
   * Instantiates a new tool changed event.
   */
  protected ToolChangedEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new tool changed event.
   * 
   * @param previousToken
   *          the previous token
   * @param newToken
   *          the new token
   */
  public ToolChangedEvent(final StateToken previousToken, final StateToken newToken) {
    this.previousToken = previousToken;
    this.newToken = newToken;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final ToolChangedHandler handler) {
    handler.onToolChanged(this);
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
    final ToolChangedEvent other = (ToolChangedEvent) obj;
    if (newToken == null) {
      if (other.newToken != null) {
        return false;
      }
    } else if (!newToken.equals(other.newToken)) {
      return false;
    }
    if (previousToken == null) {
      if (other.previousToken != null) {
        return false;
      }
    } else if (!previousToken.equals(other.previousToken)) {
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
  public Type<ToolChangedHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the new token.
   * 
   * @return the new token
   */
  public StateToken getNewToken() {
    return newToken;
  }

  /**
   * Gets the new tool.
   * 
   * @return the new tool
   */
  public java.lang.String getNewTool() {
    return newToken != null ? newToken.getTool() : null;
  }

  /**
   * Gets the previous token.
   * 
   * @return the previous token
   */
  public StateToken getPreviousToken() {
    return previousToken;
  }

  /**
   * Gets the previous tool.
   * 
   * @return the previous tool
   */
  public java.lang.String getPreviousTool() {
    return previousToken != null ? previousToken.getTool() : null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((newToken == null) ? 0 : newToken.hashCode());
    result = prime * result + ((previousToken == null) ? 0 : previousToken.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ToolChangedEvent[" + getPreviousTool() + "," + getNewTool() + "]";
  }
}
