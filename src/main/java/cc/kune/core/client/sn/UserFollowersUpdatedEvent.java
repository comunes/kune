/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.sn;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class UserFollowersUpdatedEvent extends GwtEvent<UserFollowersUpdatedEvent.UserFollowersUpdatedHandler> { 

  int followers;

  protected UserFollowersUpdatedEvent() {
    // Possibly for serialization.
  }

  public UserFollowersUpdatedEvent(int followers) {
    this.followers = followers;
  }

  public static void fire(HasHandlers source, int followers) {
    UserFollowersUpdatedEvent eventInstance = new UserFollowersUpdatedEvent(followers);
    source.fireEvent(eventInstance);
  }

  public static void fire(HasHandlers source, UserFollowersUpdatedEvent eventInstance) {
    source.fireEvent(eventInstance);
  }

  public interface HasUserFollowersUpdatedHandlers extends HasHandlers {
    HandlerRegistration addUserFollowersUpdatedHandler(UserFollowersUpdatedHandler handler);
  }

  public interface UserFollowersUpdatedHandler extends EventHandler {
    public void onUserFollowersUpdated(UserFollowersUpdatedEvent event);
  }

  private static final Type<UserFollowersUpdatedHandler> TYPE = new Type<UserFollowersUpdatedHandler>();

  public static Type<UserFollowersUpdatedHandler> getType() {
    return TYPE;
  }

  @Override
  public Type<UserFollowersUpdatedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserFollowersUpdatedHandler handler) {
    handler.onUserFollowersUpdated(this);
  }

  public int getFollowers(){
    return followers;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    UserFollowersUpdatedEvent other = (UserFollowersUpdatedEvent) obj;
    if (followers != other.followers)
        return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + new Integer(followers).hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "UserFollowersUpdatedEvent["
                 + followers
    + "]";
  }
}
