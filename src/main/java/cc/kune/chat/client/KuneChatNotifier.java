/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.chat.client;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.hablar.signals.client.notifications.HablarNotifier;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class KuneChatNotifier implements HablarNotifier {
  private final FileDownloadUtils downUtils;
  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final RegExp regExp;

  public KuneChatNotifier(final I18nTranslationService i18n, final FileDownloadUtils downUtils,
      final EventBus eventBus) {
    this.i18n = i18n;
    this.downUtils = downUtils;
    this.eventBus = eventBus;
    regExp = RegExp.compile("User (.*) says «(.*)»");
  }

  @Override
  public String getDisplayName() {
    return "Bottom notifier";
  }

  @Override
  public String getId() {
    return "kuneChatNotifier";
  }

  @Override
  public void show(final String userMessage, final String messageType) {
    // FIXME Dirty hack while emite/hablar lib don't provide user info
    if (regExp.test(userMessage)) {
      final MatchResult m = regExp.exec(userMessage);
      final String user = m.getGroup(1);
      NotifyUser.avatar(downUtils.getUserAvatar(user),
          i18n.t("User [%s] says «[%s]»", user, m.getGroup(2)), new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
              ShowChatDialogEvent.fire(eventBus, true);
            }
          });
    } else {
      NotifyUser.info(userMessage);
    }
  }

}
