/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public class RenameContentEvent extends GwtEvent<RenameContentEvent.RenameContentHandler> {

  public interface HasRenameEventHandlers extends HasHandlers {
    HandlerRegistration addRenameEventHandler(RenameContentHandler handler);
  }

  public interface RenameContentHandler extends EventHandler {
    public void onRenameEvent(RenameContentEvent event);
  }

  private static final Type<RenameContentHandler> TYPE = new Type<RenameContentHandler>();

  public static void fire(final HasHandlers source,
      final cc.kune.core.shared.domain.utils.StateToken token, final java.lang.String oldName,
      final java.lang.String newName) {
    source.fireEvent(new RenameContentEvent(token, oldName, newName));
  }

  public static Type<RenameContentHandler> getType() {
    return TYPE;
  }

  java.lang.String newName;
  java.lang.String oldName;
  cc.kune.core.shared.domain.utils.StateToken token;

  protected RenameContentEvent() {
    // Possibly for serialization.
  }

  public RenameContentEvent(final cc.kune.core.shared.domain.utils.StateToken token,
      final java.lang.String oldName, final java.lang.String newName) {
    this.token = token;
    this.oldName = oldName;
    this.newName = newName;
  }

  @Override
  protected void dispatch(final RenameContentHandler handler) {
    handler.onRenameEvent(this);
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
    final RenameContentEvent other = (RenameContentEvent) obj;
    if (token == null) {
      if (other.token != null) {
        return false;
      }
    } else if (!token.equals(other.token)) {
      return false;
    }
    if (oldName == null) {
      if (other.oldName != null) {
        return false;
      }
    } else if (!oldName.equals(other.oldName)) {
      return false;
    }
    if (newName == null) {
      if (other.newName != null) {
        return false;
      }
    } else if (!newName.equals(other.newName)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<RenameContentHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getNewName() {
    return newName;
  }

  public java.lang.String getOldName() {
    return oldName;
  }

  public cc.kune.core.shared.domain.utils.StateToken getToken() {
    return token;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (token == null ? 1 : token.hashCode());
    hashCode = (hashCode * 37) + (oldName == null ? 1 : oldName.hashCode());
    hashCode = (hashCode * 37) + (newName == null ? 1 : newName.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "RenameEventEvent[" + token + "," + oldName + "," + newName + "]";
  }
}
