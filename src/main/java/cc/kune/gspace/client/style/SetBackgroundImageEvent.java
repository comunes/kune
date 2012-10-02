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
package cc.kune.gspace.client.style;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class SetBackgroundImageEvent extends GwtEvent<SetBackgroundImageEvent.SetBackgroundImageHandler> {

  public interface HasSetBackgroundImageHandlers extends HasHandlers {
    HandlerRegistration addSetBackImageHandler(SetBackgroundImageHandler handler);
  }

  public interface SetBackgroundImageHandler extends EventHandler {
    public void onSetBackImage(SetBackgroundImageEvent event);
  }

  private static final Type<SetBackgroundImageHandler> TYPE = new Type<SetBackgroundImageHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.domain.utils.StateToken token) {
    source.fireEvent(new SetBackgroundImageEvent(token));
  }

  public static Type<SetBackgroundImageHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.domain.utils.StateToken token;

  public SetBackgroundImageEvent(cc.kune.core.shared.domain.utils.StateToken token) {
    this.token = token;
  }

  protected SetBackgroundImageEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<SetBackgroundImageHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.domain.utils.StateToken getToken() {
    return token;
  }

  @Override
  protected void dispatch(SetBackgroundImageHandler handler) {
    handler.onSetBackImage(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SetBackgroundImageEvent other = (SetBackgroundImageEvent) obj;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (token == null ? 1 : token.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "SetBackImageEvent[" + token + "]";
  }
}
