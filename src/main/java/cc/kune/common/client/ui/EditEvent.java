/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public class EditEvent extends GwtEvent<EditEvent.EditHandler> {

  public interface EditHandler extends EventHandler {

    void fire(EditEvent event);
  }

  public static final GwtEvent.Type<EditHandler> TYPE = new GwtEvent.Type<EditHandler>();

  public static Type<EditHandler> getType() {
    return TYPE;
  }

  private final String text;

  public EditEvent(final String text) {
    this.text = text;
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<EditHandler> getAssociatedType() {
    return TYPE;
  }

  public String getText() {
    return text;
  }

  @Override
  protected void dispatch(final EditHandler handler) {
    handler.fire(this);
  }
}
