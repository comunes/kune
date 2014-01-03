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

public class OnWaveClientStartEvent extends GwtEvent<OnWaveClientStartEvent.OnWaveClientStartHandler> { 

  public interface HasOnWaveClientStartHandlers extends HasHandlers {
    HandlerRegistration addOnWaveClientStartHandler(OnWaveClientStartHandler handler);
  }

  public interface OnWaveClientStartHandler extends EventHandler {
    public void onOnWaveClientStart(OnWaveClientStartEvent event);
  }

  private static final Type<OnWaveClientStartHandler> TYPE = new Type<OnWaveClientStartHandler>();

  public static void fire(HasHandlers source, cc.kune.wave.client.kspecific.WaveClientView view) {
    source.fireEvent(new OnWaveClientStartEvent(view));
  }

  public static Type<OnWaveClientStartHandler> getType() {
    return TYPE;
  }

  cc.kune.wave.client.kspecific.WaveClientView view;

  public OnWaveClientStartEvent(cc.kune.wave.client.kspecific.WaveClientView view) {
    this.view = view;
  }

  protected OnWaveClientStartEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<OnWaveClientStartHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.wave.client.kspecific.WaveClientView getView() {
    return view;
  }

  @Override
  protected void dispatch(OnWaveClientStartHandler handler) {
    handler.onOnWaveClientStart(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    OnWaveClientStartEvent other = (OnWaveClientStartEvent) obj;
    if (view == null) {
      if (other.view != null)
        return false;
    } else if (!view.equals(other.view))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (view == null ? 1 : view.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "OnWaveClientStartEvent["
                 + view
    + "]";
  }
}
