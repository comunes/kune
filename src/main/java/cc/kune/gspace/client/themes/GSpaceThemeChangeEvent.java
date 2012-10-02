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
package cc.kune.gspace.client.themes;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class GSpaceThemeChangeEvent extends GwtEvent<GSpaceThemeChangeEvent.GSpaceThemeChangeHandler> {

  public interface HasGsThemeChangeHandlers extends HasHandlers {
    HandlerRegistration addGsThemeChangeHandler(GSpaceThemeChangeHandler handler);
  }

  public interface GSpaceThemeChangeHandler extends EventHandler {
    public void onGsThemeChange(GSpaceThemeChangeEvent event);
  }

  private static final Type<GSpaceThemeChangeHandler> TYPE = new Type<GSpaceThemeChangeHandler>();

  public static void fire(HasHandlers source, cc.kune.core.shared.dto.GSpaceTheme oldTheme,
      cc.kune.core.shared.dto.GSpaceTheme newTheme) {
    source.fireEvent(new GSpaceThemeChangeEvent(oldTheme, newTheme));
  }

  public static Type<GSpaceThemeChangeHandler> getType() {
    return TYPE;
  }

  cc.kune.core.shared.dto.GSpaceTheme oldTheme;
  cc.kune.core.shared.dto.GSpaceTheme newTheme;

  public GSpaceThemeChangeEvent(cc.kune.core.shared.dto.GSpaceTheme oldTheme,
      cc.kune.core.shared.dto.GSpaceTheme newTheme) {
    this.oldTheme = oldTheme;
    this.newTheme = newTheme;
  }

  protected GSpaceThemeChangeEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<GSpaceThemeChangeHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.core.shared.dto.GSpaceTheme getOldTheme() {
    return oldTheme;
  }

  public cc.kune.core.shared.dto.GSpaceTheme getNewTheme() {
    return newTheme;
  }

  @Override
  protected void dispatch(GSpaceThemeChangeHandler handler) {
    handler.onGsThemeChange(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GSpaceThemeChangeEvent other = (GSpaceThemeChangeEvent) obj;
    if (oldTheme == null) {
      if (other.oldTheme != null)
        return false;
    } else if (!oldTheme.equals(other.oldTheme))
      return false;
    if (newTheme == null) {
      if (other.newTheme != null)
        return false;
    } else if (!newTheme.equals(other.newTheme))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (oldTheme == null ? 1 : oldTheme.hashCode());
    hashCode = (hashCode * 37) + (newTheme == null ? 1 : newTheme.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GsThemeChangeEvent[" + oldTheme + "," + newTheme + "]";
  }
}
