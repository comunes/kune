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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class UserSignOutEvent extends GwtEvent<UserSignOutEvent.UserSignOutHandler> {

  public interface HasUserSignOutHandlers extends HasHandlers {
    HandlerRegistration addUserSignOutHandler(UserSignOutHandler handler);
  }

  public interface UserSignOutHandler extends EventHandler {
    public void onUserSignOut(UserSignOutEvent event);
  }

  private static final Type<UserSignOutHandler> TYPE = new Type<UserSignOutHandler>();

  public static void fire(final HasEventBus source) {
    source.fireEvent(new UserSignOutEvent());
  }

  public static Type<UserSignOutHandler> getType() {
    return TYPE;
  }

  public UserSignOutEvent() {
  }

  @Override
  protected void dispatch(final UserSignOutHandler handler) {
    handler.onUserSignOut(this);
  }

  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      return true;
    }
    return false;
  }

  @Override
  public Type<UserSignOutHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "UserSignOutEvent[" + "]";
  }

}
