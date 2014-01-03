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
package cc.kune.core.client.state;

import cc.kune.core.client.events.AccessRightsChangedEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessRightsClientManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AccessRightsClientManager {

  /** The event bus. */
  private final EventBus eventBus;

  /** The previous rights. */
  private AccessRights previousRights;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new access rights client manager.
   * 
   * @param eventBus
   *          the event bus
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   */
  @Inject
  public AccessRightsClientManager(final EventBus eventBus, final StateManager stateManager,
      final Session session) {
    this.eventBus = eventBus;
    this.session = session;
    this.previousRights = null;
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final AccessRights rights = event.getState().getGroupRights();
        // NotifyUser.info("prev rights " + previousRights +
        // " new rights: " + rights);
        if (!rights.equals(previousRights)) {
          AccessRightsChangedEvent.fire(eventBus, previousRights, rights);
          previousRights = rights;
        }

      }
    });
  }

  /**
   * On rights changed.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  public HandlerRegistration onRightsChanged(final boolean fireNow,
      final AccessRightsChangedEvent.AccessRightsChangedHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(AccessRightsChangedEvent.getType(),
        handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onAccessRightsChanged(new AccessRightsChangedEvent(previousRights,
          currentState.getGroupRights()));
    }
    return handlerReg;
  }
}
