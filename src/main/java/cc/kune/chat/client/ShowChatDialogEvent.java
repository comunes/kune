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
package cc.kune.chat.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowChatDialogEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShowChatDialogEvent extends GwtEvent<ShowChatDialogEvent.ShowChatDialogHandler> {

  /**
   * The Interface HasShowChatDialogHandlers.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasShowChatDialogHandlers extends HasHandlers {
    
    /**
     * Adds the show chat dialog handler.
     *
     * @param handler the handler
     * @return the handler registration
     */
    HandlerRegistration addShowChatDialogHandler(ShowChatDialogHandler handler);
  }

  /**
   * The Interface ShowChatDialogHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ShowChatDialogHandler extends EventHandler {
    
    /**
     * On show chat dialog.
     *
     * @param event the event
     */
    public void onShowChatDialog(ShowChatDialogEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ShowChatDialogHandler> TYPE = new Type<ShowChatDialogHandler>();

  /**
   * Fire.
   *
   * @param source the source
   * @param show the show
   */
  public static void fire(final HasHandlers source, final boolean show) {
    source.fireEvent(new ShowChatDialogEvent(show));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<ShowChatDialogHandler> getType() {
    return TYPE;
  }

  /** The show. */
  boolean show;

  /**
   * Instantiates a new show chat dialog event.
   */
  protected ShowChatDialogEvent() {
    // Possibly for serialization.
  }

  /**
   * Instantiates a new show chat dialog event.
   *
   * @param show the show
   */
  public ShowChatDialogEvent(final boolean show) {
    this.show = show;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final ShowChatDialogHandler handler) {
    handler.onShowChatDialog(this);
  }

  /* (non-Javadoc)
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
    final ShowChatDialogEvent other = (ShowChatDialogEvent) obj;
    if (show != other.show) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ShowChatDialogHandler> getAssociatedType() {
    return TYPE;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + Boolean.valueOf(show).hashCode();
    return hashCode;
  }

  /**
   * Checks if is show.
   *
   * @return true, if is show
   */
  public boolean isShow() {
    return show;
  }

  /* (non-Javadoc)
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ShowChatDialogEvent[" + show + "]";
  }
}
