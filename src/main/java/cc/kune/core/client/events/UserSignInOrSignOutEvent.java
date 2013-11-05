/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public class UserSignInOrSignOutEvent extends
    GwtEvent<UserSignInOrSignOutEvent.UserSignInOrSignOutHandler> {

  public interface HasUserSignInOrSignOutHandlers extends HasHandlers {
    HandlerRegistration addUserSignInOrSignOutHandler(UserSignInOrSignOutHandler handler);
  }

  public interface UserSignInOrSignOutHandler extends EventHandler {
    public void onUserSignInOrSignOut(UserSignInOrSignOutEvent event);
  }

  private static final Type<UserSignInOrSignOutHandler> TYPE = new Type<UserSignInOrSignOutHandler>();

  public static Type<UserSignInOrSignOutHandler> getType() {
    return TYPE;
  }

  private final boolean loggedin;

  public UserSignInOrSignOutEvent(final boolean loggedin) {
    this.loggedin = loggedin;
  }

  @Override
  protected void dispatch(final UserSignInOrSignOutHandler handler) {
    handler.onUserSignInOrSignOut(this);
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
    final UserSignInOrSignOutEvent other = (UserSignInOrSignOutEvent) obj;
    if (loggedin != other.loggedin) {
      return false;
    }
    return true;
  }

  @Override
  public Type<UserSignInOrSignOutHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (loggedin ? 1231 : 1237);
    return result;
  }

  public boolean isLogged() {
    return loggedin;
  }

  @Override
  public String toString() {
    return "UserSignInOrSignOutEvent[" + loggedin + "]";
  }

}
