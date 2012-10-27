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
package cc.kune.gspace.client.style;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ClearBackgroundImageEvent extends
    GwtEvent<ClearBackgroundImageEvent.ClearBackgroundImageHandler> {

  public interface HasClearBackgroundImageHandlers extends HasHandlers {
    HandlerRegistration addClearBackImageHandler(ClearBackgroundImageHandler handler);
  }

  public interface ClearBackgroundImageHandler extends EventHandler {
    public void onClearBackImage(ClearBackgroundImageEvent event);
  }

  private static final Type<ClearBackgroundImageHandler> TYPE = new Type<ClearBackgroundImageHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new ClearBackgroundImageEvent());
  }

  public static Type<ClearBackgroundImageHandler> getType() {
    return TYPE;
  }

  public ClearBackgroundImageEvent() {
  }

  @Override
  public Type<ClearBackgroundImageHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ClearBackgroundImageHandler handler) {
    handler.onClearBackImage(this);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "ClearBackgroundImageEvent[]";
  }
}
