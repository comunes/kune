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
package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ToolChangedEvent extends GwtEvent<ToolChangedEvent.ToolChangedHandler> { 

  public interface HasToolChangedHandlers extends HasHandlers {
    HandlerRegistration addToolChangedHandler(ToolChangedHandler handler);
  }

  public interface ToolChangedHandler extends EventHandler {
    public void onToolChanged(ToolChangedEvent event);
  }

  private static final Type<ToolChangedHandler> TYPE = new Type<ToolChangedHandler>();

  public static void fire(HasHandlers source, java.lang.String previousTool, java.lang.String newTool) {
    source.fireEvent(new ToolChangedEvent(previousTool, newTool));
  }

  public static Type<ToolChangedHandler> getType() {
    return TYPE;
  }

  java.lang.String previousTool;
  java.lang.String newTool;

  public ToolChangedEvent(java.lang.String previousTool, java.lang.String newTool) {
    this.previousTool = previousTool;
    this.newTool = newTool;
  }

  protected ToolChangedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<ToolChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getPreviousTool() {
    return previousTool;
  }

  public java.lang.String getNewTool() {
    return newTool;
  }

  @Override
  protected void dispatch(ToolChangedHandler handler) {
    handler.onToolChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ToolChangedEvent other = (ToolChangedEvent) obj;
    if (previousTool == null) {
      if (other.previousTool != null)
        return false;
    } else if (!previousTool.equals(other.previousTool))
      return false;
    if (newTool == null) {
      if (other.newTool != null)
        return false;
    } else if (!newTool.equals(other.newTool))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (previousTool == null ? 1 : previousTool.hashCode());
    hashCode = (hashCode * 37) + (newTool == null ? 1 : newTool.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ToolChangedEvent["
                 + previousTool
                 + ","
                 + newTool
    + "]";
  }
}
