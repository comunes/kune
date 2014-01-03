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
package cc.kune.chat.client;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.calclab.hablar.signals.client.notifications.HablarNotifier;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneChatNotifier.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneChatNotifier implements HablarNotifier {
  
  /** The down utils. */
  private final ClientFileDownloadUtils downUtils;
  
  /** The event bus. */
  private final EventBus eventBus;
  
  /** The i18n. */
  private final I18nTranslationService i18n;
  
  /** The reg exp. */
  private final RegExp regExp;

  /**
   * Instantiates a new kune chat notifier.
   *
   * @param i18n the i18n
   * @param downUtils the down utils
   * @param eventBus the event bus
   */
  public KuneChatNotifier(final I18nTranslationService i18n, final ClientFileDownloadUtils downUtils,
      final EventBus eventBus) {
    this.i18n = i18n;
    this.downUtils = downUtils;
    this.eventBus = eventBus;
    regExp = RegExp.compile("User (.*) says «(.*)»");
  }

  /* (non-Javadoc)
   * @see com.calclab.hablar.signals.client.notifications.HablarNotifier#getDisplayName()
   */
  @Override
  public String getDisplayName() {
    return "Bottom notifier";
  }

  /* (non-Javadoc)
   * @see com.calclab.hablar.signals.client.notifications.HablarNotifier#getId()
   */
  @Override
  public String getId() {
    return "kuneChatNotifier";
  }

  /* (non-Javadoc)
   * @see com.calclab.hablar.signals.client.notifications.HablarNotifier#show(java.lang.String, java.lang.String)
   */
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
