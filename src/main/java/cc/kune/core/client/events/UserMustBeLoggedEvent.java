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

public class UserMustBeLoggedEvent extends GwtEvent<UserMustBeLoggedEvent.UserMustBeLoggedHandler> {

  public interface HasUserMustBeLoggedHandlers extends HasHandlers {
    HandlerRegistration addUserMustBeLoggedHandler(UserMustBeLoggedHandler handler);
  }

  public interface UserMustBeLoggedHandler extends EventHandler {
    public void onUserMustBeLogged(UserMustBeLoggedEvent event);
  }

  private static final Type<UserMustBeLoggedHandler> TYPE = new Type<UserMustBeLoggedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new UserMustBeLoggedEvent());
  }

  public static Type<UserMustBeLoggedHandler> getType() {
    return TYPE;
  }

  public UserMustBeLoggedEvent() {
  }

  @Override
  protected void dispatch(final UserMustBeLoggedHandler handler) {
    handler.onUserMustBeLogged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<UserMustBeLoggedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "UserMustBeLoggedEvent[" + "]";
  }
}
