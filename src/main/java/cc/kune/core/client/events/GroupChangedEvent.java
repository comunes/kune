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
 * The Class GroupChangedEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupChangedEvent extends GwtEvent<GroupChangedEvent.GroupChangedHandler> {

  /**
   * The Interface GroupChangedHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface GroupChangedHandler extends EventHandler {

    /**
     * On group changed.
     * 
     * @param event
     *          the event
     */
    public void onGroupChanged(GroupChangedEvent event);
  }

  /**
   * The Interface HasGroupChangedHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasGroupChangedHandlers extends HasHandlers {

    /**
     * Adds the group changed handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addGroupChangedHandler(GroupChangedHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<GroupChangedHandler> TYPE = new Type<GroupChangedHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param previousGroup
   *          the previous group
   * @param newGroup
   *          the new group
   */
  public static void fire(final HasHandlers source, final java.lang.String previousGroup,
      final java.lang.String newGroup) {
    source.fireEvent(new GroupChangedEvent(previousGroup, newGroup));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<GroupChangedHandler> getType() {
    return TYPE;
  }

  /** The new group. */
  java.lang.String newGroup;

  /** The previous group. */
  java.lang.String previousGroup;

  /**
   * Instantiates a new group changed event.
   */
  protected GroupChangedEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new group changed event.
   * 
   * @param previousGroup
   *          the previous group
   * @param newGroup
   *          the new group
   */
  public GroupChangedEvent(final java.lang.String previousGroup, final java.lang.String newGroup) {
    this.previousGroup = previousGroup;
    this.newGroup = newGroup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final GroupChangedHandler handler) {
    handler.onGroupChanged(this);
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
    final GroupChangedEvent other = (GroupChangedEvent) obj;
    if (previousGroup == null) {
      if (other.previousGroup != null) {
        return false;
      }
    } else if (!previousGroup.equals(other.previousGroup)) {
      return false;
    }
    if (newGroup == null) {
      if (other.newGroup != null) {
        return false;
      }
    } else if (!newGroup.equals(other.newGroup)) {
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
  public Type<GroupChangedHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the new group.
   * 
   * @return the new group
   */
  public java.lang.String getNewGroup() {
    return newGroup;
  }

  /**
   * Gets the previous group.
   * 
   * @return the previous group
   */
  public java.lang.String getPreviousGroup() {
    return previousGroup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (previousGroup == null ? 1 : previousGroup.hashCode());
    hashCode = (hashCode * 37) + (newGroup == null ? 1 : newGroup.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "GroupChangedEvent[" + previousGroup + "," + newGroup + "]";
  }
}
