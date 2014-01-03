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
package cc.kune.client;

import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating OnAppStart objects.
 */
public class OnAppStartFactory {

  /**
   * Instantiates a new on app start factory.
   *
   * @param session the session
   * @param newGroup the new group
   * @param signIn the sign in
   * @param tokenListener the token listener
   */
  @Inject
  public OnAppStartFactory(final Session session, final Provider<NewGroup> newGroup,
      final Provider<SignIn> signIn, final SiteTokenListeners tokenListener) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        newGroup.get();
      }
    });
  }
}
