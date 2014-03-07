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
package cc.kune.common.client.notify;

import cc.kune.common.client.msgs.UserMessagesPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * The Class SimpleUserNotifierPopup.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SimpleUserNotifierPopup {

  /** The msgs. */
  private final SimpleUserMessage msgWidget;

  /**
   * Instantiates a new user notifier popup.
   * 
   * @param eventBus
   *          the event bus
   * @param msgs
   *          the msgs
   * @param msgWidget
   *          the msg widget
   */
  @Inject
  public SimpleUserNotifierPopup(final EventBus eventBus, final UserMessagesPresenter msgs,
      final SimpleUserMessage msgWidget) {
    this.msgWidget = msgWidget;
    eventBus.addHandler(UserNotifyEvent.getType(), new UserNotifyEvent.UserNotifyHandler() {
      @Override
      public void onUserNotify(final UserNotifyEvent event) {
        if (event.getLevel() != NotifyLevel.log) {
          SimpleUserNotifierPopup.this.onNotify(event);
        }
      }
    });
  }

  /**
   * Notify.
   * 
   * @param event
   *          the event
   */
  private void onNotify(final UserNotifyEvent event) {
    // TODO: Use event.getLevel() with style colors
    final String eventTitle = event.getTitle();
    final String title = eventTitle.length() > 0 ? eventTitle + ": " : "";
    msgWidget.show(title + event.getMessage());
  }

}
