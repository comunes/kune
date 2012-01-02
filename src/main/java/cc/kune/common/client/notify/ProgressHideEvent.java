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
package cc.kune.common.client.notify;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ProgressHideEvent extends GwtEvent<ProgressHideEvent.ProgressHideHandler> {

  public interface HasProgressHideHandlers extends HasHandlers {
    HandlerRegistration addProgressHideHandler(ProgressHideHandler handler);
  }

  public interface ProgressHideHandler extends EventHandler {
    public void onProgressHide(ProgressHideEvent event);
  }

  private static final Type<ProgressHideHandler> TYPE = new Type<ProgressHideHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new ProgressHideEvent());
  }

  public static Type<ProgressHideHandler> getType() {
    return TYPE;
  }

  public ProgressHideEvent() {
  }

  @Override
  protected void dispatch(final ProgressHideHandler handler) {
    handler.onProgressHide(this);
  }

  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      final ProgressHideEvent o = (ProgressHideEvent) other;
      return true;
    }
    return false;
  }

  @Override
  public Type<ProgressHideHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "ProgressHideEvent[" + "]";
  }

}
