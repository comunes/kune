/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.shared.SessionConstants;
import cc.kune.core.shared.dto.UserSimpleDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailNotVerifiedReminder.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmailNotVerifiedReminder {

  /**
   * This class checks if the user has verified the email if it's not, give some
   * advice.
   *
   * @param session
   *          the session
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   */
  @Inject
  public EmailNotVerifiedReminder(final Session session, final EventBus eventBus,
      final I18nTranslationService i18n) {
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        final UserSimpleDTO currentUser = session.getCurrentUser();
        if (!currentUser.isEmailVerified()) {
          final long now = new Date().getTime();
          if ((currentUser.getCreatedOn() + SessionConstants._1_HOUR) < now) {
            ClickHandler clickHandler = new ClickHandler() {
              @Override
              public void onClick(ClickEvent event) {
                WindowUtils.changeHref("#" + SiteTokens.PREFS);
              }
            };
            NotifyUser.info(I18n.t(
                "Your email is not verified, [%s] functionality will work better if you [%s]",
                i18n.getSiteCommonName(), I18n.t("verify your email")), true, clickHandler);
          }
        }
      }
    });
  }

}
