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
package cc.kune.wave.client.kspecific;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class AfterOpenWaveEvent extends GwtEvent<AfterOpenWaveEvent.AfterOpenWaveHandler> { 

  public interface HasAfterOpenWaveHandlers extends HasHandlers {
    HandlerRegistration addAfterOpenWaveHandler(AfterOpenWaveHandler handler);
  }

  public interface AfterOpenWaveHandler extends EventHandler {
    public void onAfterOpenWave(AfterOpenWaveEvent event);
  }

  private static final Type<AfterOpenWaveHandler> TYPE = new Type<AfterOpenWaveHandler>();

  public static void fire(HasHandlers source, java.lang.String waveId) {
    source.fireEvent(new AfterOpenWaveEvent(waveId));
  }

  public static Type<AfterOpenWaveHandler> getType() {
    return TYPE;
  }

  java.lang.String waveId;

  public AfterOpenWaveEvent(java.lang.String waveId) {
    this.waveId = waveId;
  }

  protected AfterOpenWaveEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<AfterOpenWaveHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.String getWaveId() {
    return waveId;
  }

  @Override
  protected void dispatch(AfterOpenWaveHandler handler) {
    handler.onAfterOpenWave(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    AfterOpenWaveEvent other = (AfterOpenWaveEvent) obj;
    if (waveId == null) {
      if (other.waveId != null)
        return false;
    } else if (!waveId.equals(other.waveId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (waveId == null ? 1 : waveId.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "AfterOpenWaveEvent["
                 + waveId
    + "]";
  }
}
