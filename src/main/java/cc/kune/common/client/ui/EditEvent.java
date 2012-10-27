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
