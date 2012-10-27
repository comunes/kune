/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class InboxUnreadUpdatedEvent extends GwtEvent<InboxUnreadUpdatedEvent.InboxUnreadUpdatedHandler> {

  public interface HasInboxUnreadUpdatedHandlers extends HasHandlers {
    HandlerRegistration addInboxUnreadUpdatedHandler(InboxUnreadUpdatedHandler handler);
  }

  public interface InboxUnreadUpdatedHandler extends EventHandler {
    public void onInboxUnreadUpdated(InboxUnreadUpdatedEvent event);
  }

  private static final Type<InboxUnreadUpdatedHandler> TYPE = new Type<InboxUnreadUpdatedHandler>();

  public static void fire(final HasHandlers source, final int count, final boolean greater) {
    source.fireEvent(new InboxUnreadUpdatedEvent(count, greater));
  }

  public static Type<InboxUnreadUpdatedHandler> getType() {
    return TYPE;
  }

  int count;
  boolean greater;

  protected InboxUnreadUpdatedEvent() {
    // Possibly for serialization.
  }

  public InboxUnreadUpdatedEvent(final int count, final boolean greater) {
    this.count = count;
    this.greater = greater;
  }

  @Override
  protected void dispatch(final InboxUnreadUpdatedHandler handler) {
    handler.onInboxUnreadUpdated(this);
  }

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

  @Override
  public Type<InboxUnreadUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  public int getCount() {
    return count;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Integer.valueOf(count).hashCode();
    hashCode = (hashCode * 37) + Boolean.valueOf(greater).hashCode();
    return hashCode;
  }

  public boolean isGreater() {
    return greater;
  }

  @Override
  public String toString() {
    return "InboxUnreadUpdatedEvent[" + count + "," + greater + "]";
  }
}
