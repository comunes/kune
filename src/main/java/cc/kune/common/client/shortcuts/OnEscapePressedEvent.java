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
package cc.kune.common.client.shortcuts;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class OnEscapePressedEvent extends GwtEvent<OnEscapePressedEvent.OnEscapePressedHandler> {

  public interface HasOnEscapePressedHandlers extends HasHandlers {
    HandlerRegistration addOnEscapePressedHandler(OnEscapePressedHandler handler);
  }

  public interface OnEscapePressedHandler extends EventHandler {
    public void onOnEscapePressed(OnEscapePressedEvent event);
  }

  private static final Type<OnEscapePressedHandler> TYPE = new Type<OnEscapePressedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new OnEscapePressedEvent());
  }

  public static Type<OnEscapePressedHandler> getType() {
    return TYPE;
  }

  public OnEscapePressedEvent() {
  }

  @Override
  protected void dispatch(final OnEscapePressedHandler handler) {
    handler.onOnEscapePressed(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<OnEscapePressedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "OnEscapePressedEvent[" + "]";
  }
}
