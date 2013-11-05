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
package cc.kune.gspace.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class CurrentEntityChangedEvent extends
    GwtEvent<CurrentEntityChangedEvent.CurrentEntityChangedHandler> {

  public interface CurrentEntityChangedHandler extends EventHandler {
    public void onCurrentLogoChanged(CurrentEntityChangedEvent event);
  }

  public interface HasCurrentLogoChangedHandlers extends HasHandlers {
    HandlerRegistration addCurrentLogoChangedHandler(CurrentEntityChangedHandler handler);
  }

  private static final Type<CurrentEntityChangedHandler> TYPE = new Type<CurrentEntityChangedHandler>();
  private final String shortName;
  private final String longName;

  public static void fire(final HasHandlers source, String shortName, String longName) {
    source.fireEvent(new CurrentEntityChangedEvent(shortName, longName));
  }

  public static Type<CurrentEntityChangedHandler> getType() {
    return TYPE;
  }

  public CurrentEntityChangedEvent(String shortName, String longName) {
    this.shortName = shortName;
    this.longName = longName;
  }

  @Override
  protected void dispatch(final CurrentEntityChangedHandler handler) {
    handler.onCurrentLogoChanged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<CurrentEntityChangedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "CurrentEntityChangedEvent [shortName=" + shortName + ", longName=" + longName + "]";
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }
}
