/*******************************************************************************
 * Copyright (C) 2007, 2013 Licensed to the Comunes Association (CA) under
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
 *******************************************************************************/

package cc.kune.gadget.client.statecookie;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.thezukunft.wave.connector.State;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

public class CookieStateStore {
  public static final String COOKIE_NAME = "cookieName";

  private final String cookieName;
  private final StateLocalFactory factory;
  private final Wave wave;

  @Inject
  public CookieStateStore(final Wave wave, final EventBus eventBus,
      @Named(COOKIE_NAME) final String cookieName) {
    this.wave = wave;
    this.cookieName = cookieName;
    factory = GWT.create(StateLocalFactory.class);
    restoreStateFromCookie();
    eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
      @Override
      public void onUpdate(final StateUpdateEvent event) {
        // Storing state in a cookie
        final AutoBean<StateLocal> stateLocal = factory.getStateLocal();
        final Map<String, String> map = new HashMap<String, String>();
        final State state = wave.getState();
        final JsArrayString keys = state.getKeys();
        for (int i = 0; i < keys.length(); i++) {
          final String key = keys.get(i);
          final String value = state.get(key);
          map.put(key, value);
        }
        stateLocal.as().setMap(map);
        // Saving the cookie
        Cookies.setCookie(cookieName, AutoBeanCodex.encode(stateLocal).getPayload());
      }
    });
  }

  /**
   * Restore state from cookie (only for test purposes).
   */
  public void restoreStateFromCookie() {
    AutoBean<StateLocal> stateLocal;

    Map<String, String> map;
    final String stateCookie = Cookies.getCookie(cookieName);
    if (stateCookie != null) {
      // Restoring state from cookie (deserialization)
      stateLocal = AutoBeanCodex.decode(factory, StateLocal.class, stateCookie);
      map = stateLocal.as().getMap();
    } else {
      // Create a new state object (first run)
      stateLocal = factory.getStateLocal();
      map = new HashMap<String, String>();
    }

    for (final String key : map.keySet()) {
      final String value = map.get(key);
      // Setting the real state with cookie values
      wave.getState().submitValue(key, value);
    }
  }
}
