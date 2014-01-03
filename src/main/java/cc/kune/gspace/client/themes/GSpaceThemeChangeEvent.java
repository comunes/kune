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
package cc.kune.gspace.client.themes;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceThemeChangeEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceThemeChangeEvent extends GwtEvent<GSpaceThemeChangeEvent.GSpaceThemeChangeHandler> {

  /**
   * The Interface GSpaceThemeChangeHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface GSpaceThemeChangeHandler extends EventHandler {

    /**
     * On gs theme change.
     * 
     * @param event
     *          the event
     */
    public void onGsThemeChange(GSpaceThemeChangeEvent event);
  }

  /**
   * The Interface HasGsThemeChangeHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasGsThemeChangeHandlers extends HasHandlers {

    /**
     * Adds the gs theme change handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addGsThemeChangeHandler(GSpaceThemeChangeHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<GSpaceThemeChangeHandler> TYPE = new Type<GSpaceThemeChangeHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param oldTheme
   *          the old theme
   * @param newTheme
   *          the new theme
   */
  public static void fire(final HasHandlers source, final cc.kune.core.shared.dto.GSpaceTheme oldTheme,
      final cc.kune.core.shared.dto.GSpaceTheme newTheme) {
    source.fireEvent(new GSpaceThemeChangeEvent(oldTheme, newTheme));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<GSpaceThemeChangeHandler> getType() {
    return TYPE;
  }

  /** The new theme. */
  cc.kune.core.shared.dto.GSpaceTheme newTheme;

  /** The old theme. */
  cc.kune.core.shared.dto.GSpaceTheme oldTheme;

  /**
   * Instantiates a new g space theme change event.
   */
  protected GSpaceThemeChangeEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new g space theme change event.
   * 
   * @param oldTheme
   *          the old theme
   * @param newTheme
   *          the new theme
   */
  public GSpaceThemeChangeEvent(final cc.kune.core.shared.dto.GSpaceTheme oldTheme,
      final cc.kune.core.shared.dto.GSpaceTheme newTheme) {
    this.oldTheme = oldTheme;
    this.newTheme = newTheme;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final GSpaceThemeChangeHandler handler) {
    handler.onGsThemeChange(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    final GSpaceThemeChangeEvent other = (GSpaceThemeChangeEvent) obj;
    if (oldTheme == null) {
      if (other.oldTheme != null) {
        return false;
      }
    } else if (!oldTheme.equals(other.oldTheme)) {
      return false;
    }
    if (newTheme == null) {
      if (other.newTheme != null) {
        return false;
      }
    } else if (!newTheme.equals(other.newTheme)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<GSpaceThemeChangeHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the new theme.
   * 
   * @return the new theme
   */
  public cc.kune.core.shared.dto.GSpaceTheme getNewTheme() {
    return newTheme;
  }

  /**
   * Gets the old theme.
   * 
   * @return the old theme
   */
  public cc.kune.core.shared.dto.GSpaceTheme getOldTheme() {
    return oldTheme;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (oldTheme == null ? 1 : oldTheme.hashCode());
    hashCode = (hashCode * 37) + (newTheme == null ? 1 : newTheme.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "GsThemeChangeEvent[" + oldTheme + "," + newTheme + "]";
  }
}
