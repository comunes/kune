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
 * The Class UserSignInOrSignOutEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSignInOrSignOutEvent extends
    GwtEvent<UserSignInOrSignOutEvent.UserSignInOrSignOutHandler> {

  /**
   * The Interface HasUserSignInOrSignOutHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasUserSignInOrSignOutHandlers extends HasHandlers {

    /**
     * Adds the user sign in or sign out handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addUserSignInOrSignOutHandler(UserSignInOrSignOutHandler handler);
  }

  /**
   * The Interface UserSignInOrSignOutHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface UserSignInOrSignOutHandler extends EventHandler {

    /**
     * On user sign in or sign out.
     * 
     * @param event
     *          the event
     */
    public void onUserSignInOrSignOut(UserSignInOrSignOutEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<UserSignInOrSignOutHandler> TYPE = new Type<UserSignInOrSignOutHandler>();

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<UserSignInOrSignOutHandler> getType() {
    return TYPE;
  }

  /** The loggedin. */
  private final boolean loggedin;

  /**
   * Instantiates a new user sign in or sign out event.
   * 
   * @param loggedin
   *          the loggedin
   */
  public UserSignInOrSignOutEvent(final boolean loggedin) {
    this.loggedin = loggedin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final UserSignInOrSignOutHandler handler) {
    handler.onUserSignInOrSignOut(this);
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
    final UserSignInOrSignOutEvent other = (UserSignInOrSignOutEvent) obj;
    if (loggedin != other.loggedin) {
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
  public Type<UserSignInOrSignOutHandler> getAssociatedType() {
    return TYPE;
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
    result = prime * result + (loggedin ? 1231 : 1237);
    return result;
  }

  /**
   * Checks if is logged.
   * 
   * @return true, if is logged
   */
  public boolean isLogged() {
    return loggedin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "UserSignInOrSignOutEvent[" + loggedin + "]";
  }

}
