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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.SessionExpiredEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent;
import cc.kune.core.client.events.UserMustBeLoggedEvent.UserMustBeLoggedHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionExpirationManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SessionExpirationManager {

  /**
   * Instantiates a new session expiration manager.
   * 
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   */
  @Inject
  public SessionExpirationManager(final EventBus eventBus, final Session session,
      final I18nTranslationService i18n) {
    eventBus.addHandler(UserMustBeLoggedEvent.getType(), new UserMustBeLoggedHandler() {
      @Override
      public void onUserMustBeLogged(final UserMustBeLoggedEvent event) {
        if (session.isLogged()) {
          SessionExpiredEvent.fire(eventBus);
        } else {
          NotifyUser.important(i18n.t("Please sign in or register to collaborate"));
        }
      }
    });
    eventBus.addHandler(SessionExpiredEvent.getType(), new SessionExpiredEvent.SessionExpiredHandler() {
      @Override
      public void onSessionExpired(final SessionExpiredEvent event) {
        if (session.isLogged()) {
          session.signOut();
        }
        NotifyUser.info(i18n.t("Please sign in again"));
      }
    });
  }

}
