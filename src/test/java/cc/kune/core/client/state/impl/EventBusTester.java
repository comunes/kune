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
package cc.kune.core.client.state.impl;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class EventBusTester.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventBusTester extends SimpleEventBus {

  /** The all. */
  private final ArrayList<GwtEvent<?>> all;

  /** The last event. */
  private GwtEvent<?> lastEvent;

  /**
   * Instantiates a new event bus tester.
   */
  public EventBusTester() {
    all = new ArrayList<GwtEvent<?>>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.SimpleEventBus#fireEvent(com.google.gwt.event
   * .shared.GwtEvent)
   */
  @Override
  public void fireEvent(final GwtEvent<?> event) {
    all.add(event);
    this.lastEvent = event;
    super.fireEvent(event);
  }

  /**
   * Gets the last event.
   * 
   * @return the last event
   */
  public GwtEvent<?> getLastEvent() {
    return lastEvent;
  }

  /**
   * Gets the last event class.
   * 
   * @param <T>
   *          the generic type
   * @return the last event class
   */
  @SuppressWarnings("unchecked")
  public <T extends GwtEvent<?>> Class<T> getLastEventClass() {
    return (Class<T>) lastEvent.getClass();
  }

  /**
   * Received event of class.
   * 
   * @param eventClass
   *          the event class
   * @return true, if successful
   */
  public boolean receivedEventOfClass(final Class<? extends GwtEvent<?>> eventClass) {
    for (final GwtEvent<?> event : all) {
      if (eventClass.equals(event.getClass())) {
        return true;
      }
    }
    return false;
  }
}
