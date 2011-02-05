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
package cc.kune.core.client.i18n;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class I18nReadyEvent extends GwtEvent<I18nReadyEvent.I18nReadyHandler> { 

  public interface HasI18nReadyHandlers extends HasHandlers {
    HandlerRegistration addI18nReadyHandler(I18nReadyHandler handler);
  }

  public interface I18nReadyHandler extends EventHandler {
    public void onI18nReady(I18nReadyEvent event);
  }

  private static final Type<I18nReadyHandler> TYPE = new Type<I18nReadyHandler>();

  public static void fire(HasEventBus source) {
    source.fireEvent(new I18nReadyEvent());
  }

  public static Type<I18nReadyHandler> getType() {
    return TYPE;
  }


  public I18nReadyEvent() {
  }

  @Override
  public Type<I18nReadyHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(I18nReadyHandler handler) {
    handler.onI18nReady(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
          I18nReadyEvent o = (I18nReadyEvent) other;
      return true
        ;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "I18nReadyEvent["
    + "]";
  }

}
