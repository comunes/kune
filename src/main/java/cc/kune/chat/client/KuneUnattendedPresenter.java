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

import cc.kune.chat.client.ChatClientDefault.ChatClientAction;
import cc.kune.common.client.log.Log;
import cc.kune.core.client.events.SndClickEvent;

import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.signals.client.SignalPreferences;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedEvent;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedHandler;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;
import com.google.gwt.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * Handles the presentation of unattended chats.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneUnattendedPresenter {
  
  /** The active. */
  private boolean active;

  /**
   * Instantiates a new kune unattended presenter.
   *
   * @param eventBus the event bus
   * @param hablarEventBus the hablar event bus
   * @param preferences the preferences
   * @param unattendedManager the unattended manager
   * @param action the action
   */
  public KuneUnattendedPresenter(final EventBus eventBus, final HablarEventBus hablarEventBus,
      final SignalPreferences preferences, final UnattendedPagesManager unattendedManager,
      final ChatClientAction action) {
    active = false;
    hablarEventBus.addHandler(UnattendedChatsChangedEvent.TYPE, new UnattendedChatsChangedHandler() {
      @Override
      public void handleUnattendedChatChange(final UnattendedChatsChangedEvent event) {
        final int unattendedChatsCount = unattendedManager.getSize();
        if (unattendedChatsCount > 0 && active == false) {
          active = true;
          SndClickEvent.fire(eventBus);
          action.setBlink(true);
          Log.info("BLINK true");
        } else if (unattendedChatsCount == 0 && active == true) {
          action.setBlink(false);
          active = false;
          Log.info("BLINK false");
        }
      }
    });
  }
}
