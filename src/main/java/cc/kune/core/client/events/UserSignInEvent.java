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
 * The Class UserSignInEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSignInEvent extends GwtEvent<UserSignInEvent.UserSignInHandler> {

  /**
   * The Interface HasUserSignInHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasUserSignInHandlers extends HasHandlers {

    /**
     * Adds the user sign in handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addUserSignInHandler(UserSignInHandler handler);
  }

  /**
   * The Interface UserSignInHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface UserSignInHandler extends EventHandler {

    /**
     * On user sign in.
     * 
     * @param event
     *          the event
     */
    public void onUserSignIn(UserSignInEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<UserSignInHandler> TYPE = new Type<UserSignInHandler>();

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<UserSignInHandler> getType() {
    return TYPE;
  }

  /** The password. */
  private final String password;

  /** The user info. */
  private final cc.kune.core.shared.dto.UserInfoDTO userInfo;

  /**
   * Instantiates a new user sign in event.
   * 
   * @param userInfo
   *          the user info
   * @param password
   *          the password
   */
  public UserSignInEvent(final cc.kune.core.shared.dto.UserInfoDTO userInfo, final String password) {
    this.userInfo = userInfo;
    this.password = password;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final UserSignInHandler handler) {
    handler.onUserSignIn(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      final UserSignInEvent o = (UserSignInEvent) other;
      return true && ((o.userInfo == null && this.userInfo == null) || (o.userInfo != null && o.userInfo.equals(this.userInfo)));
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<UserSignInHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the password.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the user info.
   * 
   * @return the user info
   */
  public cc.kune.core.shared.dto.UserInfoDTO getUserInfo() {
    return userInfo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (userInfo == null ? 1 : userInfo.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "UserSignInEvent[" + userInfo + "]";
  }

}
