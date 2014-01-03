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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class AppStartEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AppStartEvent extends GwtEvent<AppStartEvent.AppStartHandler> {

  /**
   * The Interface AppStartHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface AppStartHandler extends EventHandler {

    /**
     * On app start.
     * 
     * @param event
     *          the event
     */
    public void onAppStart(AppStartEvent event);
  }

  /**
   * The Interface HasAppStartHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasAppStartHandlers extends HasHandlers {

    /**
     * Adds the app start handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addAppStartHandler(AppStartHandler handler);
  }

  /** The Constant TYPE. */
  private static final Type<AppStartHandler> TYPE = new Type<AppStartHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param initData
   *          the init data
   */
  public static void fire(final HasHandlers source, final cc.kune.core.shared.dto.InitDataDTO initData) {
    source.fireEvent(new AppStartEvent(initData));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<AppStartHandler> getType() {
    return TYPE;
  }

  /** The init data. */
  private final cc.kune.core.shared.dto.InitDataDTO initData;

  /**
   * Instantiates a new app start event.
   * 
   * @param initData
   *          the init data
   */
  public AppStartEvent(final cc.kune.core.shared.dto.InitDataDTO initData) {
    this.initData = initData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final AppStartHandler handler) {
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
      final AppStartEvent o = (AppStartEvent) other;
      return true && ((o.initData == null && this.initData == null) || (o.initData != null && o.initData.equals(this.initData)));
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<AppStartHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the inits the data.
   * 
   * @return the inits the data
   */
  public cc.kune.core.shared.dto.InitDataDTO getInitData() {
    return initData;
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
    hashCode = (hashCode * 37) + (initData == null ? 1 : initData.hashCode());
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "AppStartEvent[" + initData + "]";
  }

}
