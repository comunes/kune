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
package cc.kune.core.client.events;

import cc.kune.core.client.embed.EmbedConfJso;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * The Class AppStartEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbAppStartEvent extends GwtEvent<EmbAppStartEvent.EmbAppStartHandler> {

  /**
   * The Interface EmbAppStartHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface EmbAppStartHandler extends EventHandler {

    /**
     * On app start.
     * 
     * @param event
     *          the event
     */
    public void onAppStart(EmbAppStartEvent event);
  }

  /**
   * The Interface HasEmbAppStartHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasEmbAppStartHandlers extends HasHandlers {

    /**
     * Adds the app start handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addEmbAppStartHandler(EmbAppStartHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<EmbAppStartHandler> TYPE = new Type<EmbAppStartHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param conf
   *          the init data
   */
  public static void fire(final HasHandlers source, final EmbedConfJso conf) {
    source.fireEvent(new EmbAppStartEvent(conf));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<EmbAppStartHandler> getType() {
    return TYPE;
  }

  /** The init data. */
  private final EmbedConfJso conf;

  /**
   * Instantiates a new app start event.
   * 
   * @param conf
   *          the init data
   */
  public EmbAppStartEvent(final EmbedConfJso conf) {
    this.conf = conf;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final EmbAppStartHandler handler) {
    handler.onAppStart(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (other != null && other.getClass().equals(this.getClass())) {
      final EmbAppStartEvent o = (EmbAppStartEvent) other;
      return true && ((o.conf == null && this.conf == null) || (o.conf != null && o.conf.equals(this.conf)));
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<EmbAppStartHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the inits the data.
   * 
   * @return the inits the data
   */
  public EmbedConfJso getInitData() {
    return conf;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + getClass().hashCode();
    hashCode = (hashCode * 37) + (conf == null ? 1 : conf.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "EmbAppStartEvent[" + conf + "]";
  }

}
