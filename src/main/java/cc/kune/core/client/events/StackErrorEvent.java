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

public class StackErrorEvent extends GwtEvent<StackErrorEvent.StackErrorHandler> { 

  public interface HasStackErrorHandlers extends HasHandlers {
    HandlerRegistration addStackErrorHandler(StackErrorHandler handler);
  }

  public interface StackErrorHandler extends EventHandler {
    public void onStackError(StackErrorEvent event);
  }

  private static final Type<StackErrorHandler> TYPE = new Type<StackErrorHandler>();

  public static void fire(HasHandlers source, java.lang.Throwable exception) {
    source.fireEvent(new StackErrorEvent(exception));
  }

  public static Type<StackErrorHandler> getType() {
    return TYPE;
  }

  java.lang.Throwable exception;

  public StackErrorEvent(java.lang.Throwable exception) {
    this.exception = exception;
  }

  protected StackErrorEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<StackErrorHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.Throwable getException() {
    return exception;
  }

  @Override
  protected void dispatch(StackErrorHandler handler) {
    handler.onStackError(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StackErrorEvent other = (StackErrorEvent) obj;
    if (exception == null) {
      if (other.exception != null)
        return false;
    } else if (!exception.equals(other.exception))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (exception == null ? 1 : exception.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StackErrorEvent["
                 + exception
    + "]";
  }
}
