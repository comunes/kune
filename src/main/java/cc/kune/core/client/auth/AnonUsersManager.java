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
package cc.kune.core.client.auth;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.UserNotifyEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteParameters;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AnonUsersManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AnonUsersManager {

  /** The Constant ANON_MESSAGE_CLOSE_ICON. */
  public static final String ANON_MESSAGE_CLOSE_ICON = "k-anon-um-close-btn";

  /** The notify msg. */
  protected UserNotifyEvent notifyMsg;

  /**
   * Instantiates a new anon users manager.
   * 
   * @param session
   *          the session
   * @param cookiesManager
   *          the cookies manager
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   */
  @Inject
  public AnonUsersManager(final Session session, final CookiesManager cookiesManager,
      final I18nTranslationService i18n, final EventBus eventBus) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        if (SiteParameters.checkUA()) {
          if (session.isNotLogged()) {
            final String anonCookie = cookiesManager.getAnonCookie();
            if (anonCookie == null) {
              // First access, set cookie to short period (1day), and show
              // message
              cookiesManager.setAnonCookie(false);
              final String siteCommonName = i18n.getSiteCommonName();
              notifyMsg = UserNotifyEvent.fire(
                  eventBus,
                  NotifyLevel.info,
                  "",
                  i18n.tWithNT(
                      "You did not sign-in, so you can just see some public contents in [%s], "
                          + "but not edit or collaborate with others. Please [%s] or [%s] in order to get full access to [%s] tools and contents",
                      "This will be something like 'Please register or sign in in other to get full access to this site tools', but instead of %s some links",
                      siteCommonName, UserFieldFactory.getRegisterLink(),
                      UserFieldFactory.getSignInLink(), siteCommonName), Boolean.TRUE);
              notifyMsg.setId(ANON_MESSAGE_CLOSE_ICON);
            } else {
              if (Boolean.valueOf(anonCookie)) {
                // Registered already: we set the cookie for some big period
                // again
                cookiesManager.setAnonCookie(true);
              } else {
                // Non registered yet: but we show the message already today
              }
            }
          }
        }
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        cookiesManager.setAnonCookie(true);
        if (notifyMsg != null) {
          // Issue #168, after register/sign-in the message not remains visible
          notifyMsg.getCloser().close();
        }
      }
    });
  }
}
