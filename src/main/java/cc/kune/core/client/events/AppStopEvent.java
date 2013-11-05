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

public class AppStopEvent extends GwtEvent<AppStopEvent.AppStopHandler> {

  public interface AppStopHandler extends EventHandler {
    public void onAppStop(AppStopEvent event);
  }

  public interface HasAppStopHandlers extends HasHandlers {
    HandlerRegistration addAppStopHandler(AppStopHandler handler);
  }

  private static final Type<AppStopHandler> TYPE = new Type<AppStopHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new AppStopEvent());
  }

  public static Type<AppStopHandler> getType() {
    return TYPE;
  }

  public AppStopEvent() {
  }

  @Override
  protected void dispatch(final AppStopHandler handler) {
    handler.onAppStop(this);
  }

  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      return true;
    }
    return false;
  }

  @Override
  public Type<AppStopHandler> getAssociatedType() {
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
    return "AppStopEvent[" + "]";
  }

}
