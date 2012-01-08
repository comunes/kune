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

import cc.kune.core.shared.domain.utils.StateToken;

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

  public static void fire(final HasHandlers source, final StateToken previousToken,
      final StateToken newToken) {
    source.fireEvent(new ToolChangedEvent(previousToken, newToken));
  }

  public static Type<ToolChangedHandler> getType() {
    return TYPE;
  }

  private StateToken newToken;
  private StateToken previousToken;

  protected ToolChangedEvent() {
    // Possibly for serialization.
  }

  public ToolChangedEvent(final StateToken previousToken, final StateToken newToken) {
    this.previousToken = previousToken;
    this.newToken = newToken;
  }

  @Override
  protected void dispatch(final ToolChangedHandler handler) {
    handler.onToolChanged(this);
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
    final ToolChangedEvent other = (ToolChangedEvent) obj;
    if (newToken == null) {
      if (other.newToken != null) {
        return false;
      }
    } else if (!newToken.equals(other.newToken)) {
      return false;
    }
    if (previousToken == null) {
      if (other.previousToken != null) {
        return false;
      }
    } else if (!previousToken.equals(other.previousToken)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<ToolChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public StateToken getNewToken() {
    return newToken;
  }

  public java.lang.String getNewTool() {
    return newToken != null ? newToken.getTool() : null;
  }

  public StateToken getPreviousToken() {
    return previousToken;
  }

  public java.lang.String getPreviousTool() {
    return previousToken != null ? previousToken.getTool() : null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((newToken == null) ? 0 : newToken.hashCode());
    result = prime * result + ((previousToken == null) ? 0 : previousToken.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "ToolChangedEvent[" + getPreviousTool() + "," + getNewTool() + "]";
  }
}
