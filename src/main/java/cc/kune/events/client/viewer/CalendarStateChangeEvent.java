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
package cc.kune.events.client.viewer;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class CalendarStateChangeEvent extends
    GwtEvent<CalendarStateChangeEvent.CalendarStateChangeHandler> {

  public interface CalendarStateChangeHandler extends EventHandler {
    public void onCalendarStateChange(CalendarStateChangeEvent event);
  }

  public interface HasCalendarStateChangeHandlers extends HasHandlers {
    HandlerRegistration addCalendarStateChangeHandler(CalendarStateChangeHandler handler);
  }

  private static final Type<CalendarStateChangeHandler> TYPE = new Type<CalendarStateChangeHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new CalendarStateChangeEvent());
  }

  public static Type<CalendarStateChangeHandler> getType() {
    return TYPE;
  }

  public CalendarStateChangeEvent() {
  }

  @Override
  protected void dispatch(final CalendarStateChangeHandler handler) {
    handler.onCalendarStateChange(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<CalendarStateChangeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "CalendarStateChangeEvent[" + "]";
  }
}
