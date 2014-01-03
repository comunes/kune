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
 * The Class InboxUnreadUpdatedEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InboxUnreadUpdatedEvent extends GwtEvent<InboxUnreadUpdatedEvent.InboxUnreadUpdatedHandler> {

  /**
   * The Interface HasInboxUnreadUpdatedHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasInboxUnreadUpdatedHandlers extends HasHandlers {

    /**
     * Adds the inbox unread updated handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addInboxUnreadUpdatedHandler(InboxUnreadUpdatedHandler handler);
  }

  /**
   * The Interface InboxUnreadUpdatedHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface InboxUnreadUpdatedHandler extends EventHandler {

    /**
     * On inbox unread updated.
     * 
     * @param event
     *          the event
     */
    public void onInboxUnreadUpdated(InboxUnreadUpdatedEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<InboxUnreadUpdatedHandler> TYPE = new Type<InboxUnreadUpdatedHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param count
   *          the count
   * @param greater
   *          the greater
   */
  public static void fire(final HasHandlers source, final int count, final boolean greater) {
    source.fireEvent(new InboxUnreadUpdatedEvent(count, greater));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<InboxUnreadUpdatedHandler> getType() {
    return TYPE;
  }

  /** The count. */
  int count;

  /** The greater. */
  boolean greater;

  /**
   * Instantiates a new inbox unread updated event.
   */
  protected InboxUnreadUpdatedEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new inbox unread updated event.
   * 
   * @param count
   *          the count
   * @param greater
   *          the greater
   */
  public InboxUnreadUpdatedEvent(final int count, final boolean greater) {
    this.count = count;
    this.greater = greater;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final InboxUnreadUpdatedHandler handler) {
    handler.onInboxUnreadUpdated(this);
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
    final InboxUnreadUpdatedEvent other = (InboxUnreadUpdatedEvent) obj;
    if (count != other.count) {
      return false;
    }
    if (greater != other.greater) {
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
  public Type<InboxUnreadUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the count.
   * 
   * @return the count
   */
  public int getCount() {
    return count;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Integer.valueOf(count).hashCode();
    hashCode = (hashCode * 37) + Boolean.valueOf(greater).hashCode();
    return hashCode;
  }

  /**
   * Checks if is greater.
   * 
   * @return true, if is greater
   */
  public boolean isGreater() {
    return greater;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "InboxUnreadUpdatedEvent[" + count + "," + greater + "]";
  }
}
