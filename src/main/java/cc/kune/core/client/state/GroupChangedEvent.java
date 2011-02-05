/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class GroupChangedEvent extends GwtEvent<GroupChangedEvent.GroupChangedHandler> { 

  public interface HasGroupChangedHandlers extends HasHandlers {
    HandlerRegistration addGroupChangedHandler(GroupChangedHandler handler);
  }

  public interface GroupChangedHandler extends EventHandler {
    public void onGroupChanged(GroupChangedEvent event);
  }

  private static final Type<GroupChangedHandler> TYPE = new Type<GroupChangedHandler>();

  public static void fire(HasHandlers source, java.lang.String previousGroup, java.lang.String newGroup) {
    source.fireEvent(new GroupChangedEvent(previousGroup, newGroup));
  }

  public static Type<GroupChangedHandler> getType() {
    return TYPE;
  }

  java.lang.String previousGroup;
  java.lang.String newGroup;

  public GroupChangedEvent(java.lang.String previousGroup, java.lang.String newGroup) {
    this.previousGroup = previousGroup;
    this.newGroup = newGroup;
  }

  protected GroupChangedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<GroupChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getPreviousGroup() {
    return previousGroup;
  }

  public java.lang.String getNewGroup() {
    return newGroup;
  }

  @Override
  protected void dispatch(GroupChangedHandler handler) {
    handler.onGroupChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GroupChangedEvent other = (GroupChangedEvent) obj;
    if (previousGroup == null) {
      if (other.previousGroup != null)
        return false;
    } else if (!previousGroup.equals(other.previousGroup))
      return false;
    if (newGroup == null) {
      if (other.newGroup != null)
        return false;
    } else if (!newGroup.equals(other.newGroup))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (previousGroup == null ? 1 : previousGroup.hashCode());
    hashCode = (hashCode * 37) + (newGroup == null ? 1 : newGroup.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GroupChangedEvent["
                 + previousGroup
                 + ","
                 + newGroup
    + "]";
  }
}
