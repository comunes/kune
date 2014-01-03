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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class WaveSessionAvailableEvent extends
    GwtEvent<WaveSessionAvailableEvent.WaveSessionAvailableHandler> {

  public interface HasWaveSessionAvailableHandlers extends HasHandlers {
    HandlerRegistration addWaveSessionAvailableHandler(WaveSessionAvailableHandler handler);
  }

  public interface WaveSessionAvailableHandler extends EventHandler {
    public void onWaveSessionAvailable(WaveSessionAvailableEvent event);
  }

  private static final Type<WaveSessionAvailableHandler> TYPE = new Type<WaveSessionAvailableHandler>();

  public static void fire(final HasHandlers source, final cc.kune.core.shared.dto.UserInfoDTO userInfo) {
    source.fireEvent(new WaveSessionAvailableEvent(userInfo));
  }

  public static Type<WaveSessionAvailableHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.dto.UserInfoDTO userInfo;

  protected WaveSessionAvailableEvent() {
    // Possibly for serialization.
  }

  public WaveSessionAvailableEvent(final cc.kune.core.shared.dto.UserInfoDTO userInfo) {
    this.userInfo = userInfo;
  }

  @Override
  protected void dispatch(final WaveSessionAvailableHandler handler) {
    handler.onWaveSessionAvailable(this);
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
    final WaveSessionAvailableEvent other = (WaveSessionAvailableEvent) obj;
    if (userInfo == null) {
      if (other.userInfo != null) {
        return false;
      }
    } else if (!userInfo.equals(other.userInfo)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<WaveSessionAvailableHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.UserInfoDTO getUserInfo() {
    return userInfo;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (userInfo == null ? 1 : userInfo.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "WaveSessionAvailableEvent[" + userInfo + "]";
  }
}
