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
package cc.kune.chat.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ShowChatDialogEvent extends GwtEvent<ShowChatDialogEvent.ShowChatDialogHandler> {

  public interface HasShowChatDialogHandlers extends HasHandlers {
    HandlerRegistration addShowChatDialogHandler(ShowChatDialogHandler handler);
  }

  public interface ShowChatDialogHandler extends EventHandler {
    public void onShowChatDialog(ShowChatDialogEvent event);
  }

  private static final Type<ShowChatDialogHandler> TYPE = new Type<ShowChatDialogHandler>();

  public static void fire(final HasHandlers source, final boolean show) {
    source.fireEvent(new ShowChatDialogEvent(show));
  }

  public static Type<ShowChatDialogHandler> getType() {
    return TYPE;
  }

  boolean show;

  protected ShowChatDialogEvent() {
    // Possibly for serialization.
  }

  public ShowChatDialogEvent(final boolean show) {
    this.show = show;
  }

  @Override
  protected void dispatch(final ShowChatDialogHandler handler) {
    handler.onShowChatDialog(this);
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
    final ShowChatDialogEvent other = (ShowChatDialogEvent) obj;
    if (show != other.show) {
      return false;
    }
    return true;
  }

  @Override
  public Type<ShowChatDialogHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Boolean.valueOf(show).hashCode();
    return hashCode;
  }

  public boolean isShow() {
    return show;
  }

  @Override
  public String toString() {
    return "ShowChatDialogEvent[" + show + "]";
  }
}
