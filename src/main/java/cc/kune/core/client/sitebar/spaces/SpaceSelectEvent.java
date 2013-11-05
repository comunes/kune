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
package cc.kune.core.client.sitebar.spaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SpaceSelectEvent extends GwtEvent<SpaceSelectEvent.SpaceSelectHandler> {

  public interface HasSpaceSelectHandlers extends HasHandlers {
    HandlerRegistration addSpaceSelectHandler(SpaceSelectHandler handler);
  }

  public interface SpaceSelectHandler extends EventHandler {
    public void onSpaceSelect(SpaceSelectEvent event);
  }

  private static final Type<SpaceSelectHandler> TYPE = new Type<SpaceSelectHandler>();

  public static void fire(final HasHandlers source, final cc.kune.core.client.sitebar.spaces.Space space) {
    source.fireEvent(new SpaceSelectEvent(space));
  }

  public static void fire(final HasHandlers source,
      final cc.kune.core.client.sitebar.spaces.Space space, final boolean restoreToken) {
    source.fireEvent(new SpaceSelectEvent(space, restoreToken));
  }

  public static Type<SpaceSelectHandler> getType() {
    return TYPE;
  }
  private boolean restoreToken;

  private cc.kune.core.client.sitebar.spaces.Space space;

  protected SpaceSelectEvent() {
    // Possibly for serialization.
  }

  public SpaceSelectEvent(final cc.kune.core.client.sitebar.spaces.Space space) {
    this(space, false);
  }

  public SpaceSelectEvent(final cc.kune.core.client.sitebar.spaces.Space space,
      final boolean restoreToken) {
    this.space = space;
    this.setRestoreToken(restoreToken);
  }

  @Override
  protected void dispatch(final SpaceSelectHandler handler) {
    handler.onSpaceSelect(this);
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
    final SpaceSelectEvent other = (SpaceSelectEvent) obj;
    if (space == null) {
      if (other.space != null) {
        return false;
      }
    } else if (!space.equals(other.space)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<SpaceSelectHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.client.sitebar.spaces.Space getSpace() {
    return space;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (space == null ? 1 : space.hashCode());
    return hashCode;
  }

  public void setRestoreToken(final boolean restoreToken) {
    this.restoreToken = restoreToken;
  }

  public boolean shouldRestoreToken() {
    return restoreToken;
  }

  @Override
  public String toString() {
    return "SpaceSelectEvent[" + space + "]";
  }
}
