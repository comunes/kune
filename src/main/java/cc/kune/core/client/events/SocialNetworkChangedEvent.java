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
package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SocialNetworkChangedEvent extends
    GwtEvent<SocialNetworkChangedEvent.SocialNetworkChangedHandler> {

  public interface HasSocialNetworkChangedHandlers extends HasHandlers {
    HandlerRegistration addSocialNetworkChangedHandler(SocialNetworkChangedHandler handler);
  }

  public interface SocialNetworkChangedHandler extends EventHandler {
    public void onSocialNetworkChanged(SocialNetworkChangedEvent event);
  }

  private static final Type<SocialNetworkChangedHandler> TYPE = new Type<SocialNetworkChangedHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.dto.StateAbstractDTO state) {
    source.fireEvent(new SocialNetworkChangedEvent(state));
  }

  public static Type<SocialNetworkChangedHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.dto.StateAbstractDTO state;

  public SocialNetworkChangedEvent(cc.kune.core.shared.dto.StateAbstractDTO state) {
    this.state = state;
  }

  protected SocialNetworkChangedEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<SocialNetworkChangedHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.StateAbstractDTO getState() {
    return state;
  }

  @Override
  protected void dispatch(SocialNetworkChangedHandler handler) {
    handler.onSocialNetworkChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SocialNetworkChangedEvent other = (SocialNetworkChangedEvent) obj;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (state == null ? 1 : state.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "SocialNetworkChangedEvent[" + state + "]";
  }
}
