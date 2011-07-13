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

public class WindowFocusEvent extends GwtEvent<WindowFocusEvent.WindowFocusHandler> {

  public interface HasWindowFocusHandlers extends HasHandlers {
    HandlerRegistration addWindowFocusHandler(WindowFocusHandler handler);
  }

  public interface WindowFocusHandler extends EventHandler {
    public void onWindowFocus(WindowFocusEvent event);
  }

  private static final Type<WindowFocusHandler> TYPE = new Type<WindowFocusHandler>();

  public static void fire(final HasHandlers source, final boolean hasFocus) {
    source.fireEvent(new WindowFocusEvent(hasFocus));
  }

  public static Type<WindowFocusHandler> getType() {
    return TYPE;
  }

  boolean hasFocus;

  protected WindowFocusEvent() {
    // Possibly for serialization.
  }

  public WindowFocusEvent(final boolean hasFocus) {
    this.hasFocus = hasFocus;
  }

  @Override
  protected void dispatch(final WindowFocusHandler handler) {
    handler.onWindowFocus(this);
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
    final WindowFocusEvent other = (WindowFocusEvent) obj;
    if (hasFocus != other.hasFocus) {
      return false;
    }
    return true;
  }

  @Override
  public Type<WindowFocusHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Boolean.valueOf(hasFocus).hashCode();
    return hashCode;
  }

  public boolean isHasFocus() {
    return hasFocus;
  }

  @Override
  public String toString() {
    return "WindowFocusEvent[" + hasFocus + "]";
  }
}
