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
package cc.kune.common.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class EditEvent.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EditEvent extends GwtEvent<EditEvent.EditHandler> {

  /**
   * The Interface EditHandler.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface EditHandler extends EventHandler {

    /**
     * Fire.
     *
     * @param event the event
     */
    void fire(EditEvent event);
  }

  /** The Constant TYPE. */
  public static final GwtEvent.Type<EditHandler> TYPE = new GwtEvent.Type<EditHandler>();

  /**
   * Gets the type.
   *
   * @return the type
   */
  public static Type<EditHandler> getType() {
    return TYPE;
  }

  /** The text. */
  private final String text;

  /**
   * Instantiates a new edits the event.
   *
   * @param text the text
   */
  public EditEvent(final String text) {
    this.text = text;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<EditHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(final EditHandler handler) {
    handler.fire(this);
  }
}
