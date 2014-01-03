/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under 
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
package cc.kune.core.client.embed;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class EmbedOpenEvent extends GwtEvent<EmbedOpenEvent.EmbedOpenHandler> {

  public interface EmbedOpenHandler extends EventHandler {
    public void onEmbedOpen(EmbedOpenEvent event);
  }

  public interface HasEmbedOpenHandlers extends HasHandlers {
    HandlerRegistration addEmbedOpenHandler(EmbedOpenHandler handler);
  }

  private static final Type<EmbedOpenHandler> TYPE = new Type<EmbedOpenHandler>();

  public static void fire(final HasHandlers source, final java.lang.String stateToken) {
    source.fireEvent(new EmbedOpenEvent(stateToken));
  }

  public static Type<EmbedOpenHandler> getType() {
    return TYPE;
  }

  java.lang.String stateToken;

  protected EmbedOpenEvent() {
    // Possibly for serialization.
  }

  public EmbedOpenEvent(final java.lang.String stateToken) {
    this.stateToken = stateToken;
  }

  @Override
  protected void dispatch(final EmbedOpenHandler handler) {
    handler.onEmbedOpen(this);
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
    final EmbedOpenEvent other = (EmbedOpenEvent) obj;
    if (stateToken == null) {
      if (other.stateToken != null) {
        return false;
      }
    } else if (!stateToken.equals(other.stateToken)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<EmbedOpenHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getStateToken() {
    return stateToken;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (stateToken == null ? 1 : stateToken.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "EmbedOpenEvent[" + stateToken + "]";
  }
}
