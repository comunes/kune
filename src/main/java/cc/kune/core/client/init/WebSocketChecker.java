/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.client.init;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.CoreConstants;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.i18n.I18n;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

public class WebSocketChecker {

  /**
   * This class check if the browser supports websockets and give some advices
   * to the user.
   * 
   * @param session
   *          the session
   * @param res
   *          the resources
   */
  @Inject
  public WebSocketChecker(final Session session, final CommonResources res) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        if (WindowUtils.dontHasWebSocket()) {
          final String mozLink = TextUtils.generateHtmlLink(CoreConstants.MOZILLA_FF_LINK,
              "Mozilla Firefox");
          NotifyUser.askConfirmation(
              res.important32(),
              I18n.t("Your browser is currently unsupported"),
              I18n.t(
                  "Please, use a free/libre modern and updated navigator like [%s] instead. Some functionality like concurrent edition will now work properly. Continue anyway?",
                  mozLink), new SimpleResponseCallback() {
                @Override
                public void onCancel() {
                  WindowUtils.changeHref(mozLink);
                }

                @Override
                public void onSuccess() {
                }
              });
        }
      }
    });
  }
}
